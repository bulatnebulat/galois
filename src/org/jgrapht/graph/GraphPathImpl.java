/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

public class GraphPathImpl<V, E>
implements GraphPath<V, E> {
    private Graph<V, E> graph;
    private List<E> edgeList;
    private V startVertex;
    private V endVertex;
    private double weight;

    public GraphPathImpl(Graph<V, E> graph, V startVertex, V endVertex, List<E> edgeList, double weight) {
        this.graph = graph;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.edgeList = edgeList;
        this.weight = weight;
    }

    @Override
    public Graph<V, E> getGraph() {
        return this.graph;
    }

    @Override
    public V getStartVertex() {
        return this.startVertex;
    }

    @Override
    public V getEndVertex() {
        return this.endVertex;
    }

    @Override
    public List<E> getEdgeList() {
        return this.edgeList;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    public String toString() {
        return this.edgeList.toString();
    }
}

