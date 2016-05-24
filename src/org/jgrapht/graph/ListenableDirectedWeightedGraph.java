/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.ListenableDirectedGraph;

public class ListenableDirectedWeightedGraph<V, E>
extends ListenableDirectedGraph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 3977582476627621938L;

    public ListenableDirectedWeightedGraph(Class<? extends E> edgeClass) {
        this(new DefaultDirectedWeightedGraph(edgeClass));
    }

    public ListenableDirectedWeightedGraph(WeightedGraph<V, E> base) {
        super((DirectedGraph)((Object)base));
    }
}

