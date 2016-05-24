/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.MaskFunctor;
import org.jgrapht.util.PrefetchIterator;
import org.jgrapht.util.TypeUtil;

class MaskEdgeSet<V, E>
extends AbstractSet<E> {
    private Set<E> edgeSet;
    private Graph<V, E> graph;
    private MaskFunctor<V, E> mask;
    private transient TypeUtil<E> edgeTypeDecl = null;
    private int size;

    public MaskEdgeSet(Graph<V, E> graph, Set<E> edgeSet, MaskFunctor<V, E> mask) {
        this.graph = graph;
        this.edgeSet = edgeSet;
        this.mask = mask;
        this.size = -1;
    }

    @Override
    public boolean contains(Object o) {
        if (this.edgeSet.contains(o) && !this.mask.isEdgeMasked(TypeUtil.uncheckedCast(o, this.edgeTypeDecl))) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new PrefetchIterator(new MaskEdgeSetNextElementFunctor());
    }

    @Override
    public int size() {
        if (this.size == -1) {
            this.size = 0;
            Iterator<E> iter = this.iterator();
            while (iter.hasNext()) {
                iter.next();
                ++this.size;
            }
        }
        return this.size;
    }

    private class MaskEdgeSetNextElementFunctor
    implements PrefetchIterator.NextElementFunctor<E> {
        private Iterator<E> iter;

        public MaskEdgeSetNextElementFunctor() {
            this.iter = MaskEdgeSet.this.edgeSet.iterator();
        }

        @Override
        public E nextElement() throws NoSuchElementException {
            E edge = this.iter.next();
            while (this.isMasked(edge)) {
                edge = this.iter.next();
            }
            return edge;
        }

        private boolean isMasked(E edge) {
            if (!(MaskEdgeSet.this.mask.isEdgeMasked(edge) || MaskEdgeSet.this.mask.isVertexMasked(MaskEdgeSet.this.graph.getEdgeSource(edge)) || MaskEdgeSet.this.mask.isVertexMasked(MaskEdgeSet.this.graph.getEdgeTarget(edge)))) {
                return false;
            }
            return true;
        }
    }

}

