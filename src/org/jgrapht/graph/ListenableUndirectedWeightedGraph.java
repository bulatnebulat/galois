/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class ListenableUndirectedWeightedGraph<V, E>
extends ListenableUndirectedGraph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 3690762799613949747L;

    public ListenableUndirectedWeightedGraph(Class<? extends E> edgeClass) {
        this(new SimpleWeightedGraph(edgeClass));
    }

    public ListenableUndirectedWeightedGraph(WeightedGraph<V, E> base) {
        super((UndirectedGraph)((Object)base));
    }
}

