/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedMultigraph;

public class DirectedWeightedMultigraph<V, E>
extends DirectedMultigraph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 4049071636005206066L;

    public DirectedWeightedMultigraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }

    public DirectedWeightedMultigraph(EdgeFactory<V, E> ef) {
        super(ef);
    }
}

