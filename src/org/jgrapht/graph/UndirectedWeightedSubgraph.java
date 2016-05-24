/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.util.Set;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.UndirectedSubgraph;

public class UndirectedWeightedSubgraph<V, E>
extends UndirectedSubgraph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 3689346615735236409L;

    public UndirectedWeightedSubgraph(WeightedGraph<V, E> base, Set<V> vertexSubset, Set<E> edgeSubset) {
        super((UndirectedGraph)((Object)base), vertexSubset, edgeSubset);
    }
}

