/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.io.Serializable;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.GraphDelegator;

public class AsWeightedGraph<V, E>
extends GraphDelegator<V, E>
implements Serializable,
WeightedGraph<V, E> {
    private static final long serialVersionUID = -716810639338971372L;
    protected final Map<E, Double> weightMap;
    private final boolean isWeightedGraph;

    public AsWeightedGraph(Graph<V, E> g, Map<E, Double> weightMap) {
        super(g);
        assert (weightMap != null);
        this.weightMap = weightMap;
        this.isWeightedGraph = g instanceof WeightedGraph;
    }

    @Override
    public void setEdgeWeight(E e, double weight) {
        if (this.isWeightedGraph) {
            super.setEdgeWeight(e, weight);
        }
        this.weightMap.put(e, weight);
    }

    @Override
    public double getEdgeWeight(E e) {
        double weight = this.weightMap.containsKey(e) ? this.weightMap.get(e).doubleValue() : super.getEdgeWeight(e);
        return weight;
    }
}

