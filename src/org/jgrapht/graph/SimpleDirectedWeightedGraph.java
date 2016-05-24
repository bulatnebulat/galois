/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.SimpleDirectedGraph;

public class SimpleDirectedWeightedGraph<V, E>
extends SimpleDirectedGraph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 3904960841681220919L;

    public SimpleDirectedWeightedGraph(EdgeFactory<V, E> ef) {
        super(ef);
    }

    public SimpleDirectedWeightedGraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }
}

