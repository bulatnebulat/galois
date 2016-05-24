/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DirectedSubgraph;

public class DirectedWeightedSubgraph<V, E>
extends DirectedSubgraph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 3905799799168250680L;

    public DirectedWeightedSubgraph(WeightedGraph<V, E> base, Set<V> vertexSubset, Set<E> edgeSubset) {
        super((DirectedGraph)((Object)base), vertexSubset, edgeSubset);
    }
}

