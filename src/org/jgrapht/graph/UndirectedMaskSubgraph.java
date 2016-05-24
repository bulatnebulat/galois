/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.MaskFunctor;
import org.jgrapht.graph.MaskSubgraph;

public class UndirectedMaskSubgraph<V, E>
extends MaskSubgraph<V, E>
implements UndirectedGraph<V, E> {
    public UndirectedMaskSubgraph(UndirectedGraph<V, E> base, MaskFunctor<V, E> mask) {
        super(base, mask);
    }
}

