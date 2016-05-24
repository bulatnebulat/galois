/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.SimpleGraph;

public class SimpleWeightedGraph<V, E>
extends SimpleGraph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 3906088949100655922L;

    public SimpleWeightedGraph(EdgeFactory<V, E> ef) {
        super(ef);
    }

    public SimpleWeightedGraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }
}

