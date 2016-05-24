/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.jgrapht.graph.MaskFunctor;
import org.jgrapht.util.PrefetchIterator;
import org.jgrapht.util.TypeUtil;

class MaskVertexSet<V, E>
extends AbstractSet<V> {
    private MaskFunctor<V, E> mask;
    private int size;
    private Set<V> vertexSet;
    private transient TypeUtil<V> vertexTypeDecl = null;

    public MaskVertexSet(Set<V> vertexSet, MaskFunctor<V, E> mask) {
        this.vertexSet = vertexSet;
        this.mask = mask;
        this.size = -1;
    }

    @Override
    public boolean contains(Object o) {
        if (!this.mask.isVertexMasked(TypeUtil.uncheckedCast(o, this.vertexTypeDecl)) && this.vertexSet.contains(o)) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<V> iterator() {
        return new PrefetchIterator(new MaskVertexSetNextElementFunctor());
    }

    @Override
    public int size() {
        if (this.size == -1) {
            this.size = 0;
            Iterator<V> iter = this.iterator();
            while (iter.hasNext()) {
                iter.next();
                ++this.size;
            }
        }
        return this.size;
    }

    private class MaskVertexSetNextElementFunctor
    implements PrefetchIterator.NextElementFunctor<V> {
        private Iterator<V> iter;

        public MaskVertexSetNextElementFunctor() {
            this.iter = MaskVertexSet.this.vertexSet.iterator();
        }

        @Override
        public V nextElement() throws NoSuchElementException {
            V element = this.iter.next();
            while (MaskVertexSet.this.mask.isVertexMasked(element)) {
                element = this.iter.next();
            }
            return element;
        }
    }

}

