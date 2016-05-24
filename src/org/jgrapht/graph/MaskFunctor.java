/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

public interface MaskFunctor<V, E> {
    public boolean isEdgeMasked(E var1);

    public boolean isVertexMasked(V var1);
}

