/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.MaskFunctor;
import org.jgrapht.graph.MaskSubgraph;

public class DirectedMaskSubgraph<V, E>
extends MaskSubgraph<V, E>
implements DirectedGraph<V, E> {
    public DirectedMaskSubgraph(DirectedGraph<V, E> base, MaskFunctor<V, E> mask) {
        super(base, mask);
    }
}

