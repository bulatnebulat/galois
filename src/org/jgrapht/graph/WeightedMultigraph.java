/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.Multigraph;

public class WeightedMultigraph<V, E>
extends Multigraph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 3544671793370640696L;

    public WeightedMultigraph(EdgeFactory<V, E> ef) {
        super(ef);
    }

    public WeightedMultigraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }
}

