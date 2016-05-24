/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;

public class DefaultDirectedWeightedGraph<V, E>
extends DefaultDirectedGraph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 3761405317841171513L;

    public DefaultDirectedWeightedGraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }

    public DefaultDirectedWeightedGraph(EdgeFactory<V, E> ef) {
        super(ef);
    }
}

