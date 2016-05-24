/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.io.Serializable;
import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.AbstractGraph;

public class GraphDelegator<V, E>
extends AbstractGraph<V, E>
implements Graph<V, E>,
Serializable {
    private static final long serialVersionUID = 3257005445226181425L;
    private Graph<V, E> delegate;

    public GraphDelegator(Graph<V, E> g) {
        if (g == null) {
            throw new IllegalArgumentException("g must not be null.");
        }
        this.delegate = g;
    }

    @Override
    public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
        return this.delegate.getAllEdges(sourceVertex, targetVertex);
    }

    @Override
    public E getEdge(V sourceVertex, V targetVertex) {
        return this.delegate.getEdge(sourceVertex, targetVertex);
    }

    @Override
    public EdgeFactory<V, E> getEdgeFactory() {
        return this.delegate.getEdgeFactory();
    }

    @Override
    public E addEdge(V sourceVertex, V targetVertex) {
        return this.delegate.addEdge(sourceVertex, targetVertex);
    }

    @Override
    public boolean addEdge(V sourceVertex, V targetVertex, E e) {
        return this.delegate.addEdge(sourceVertex, targetVertex, e);
    }

    @Override
    public boolean addVertex(V v) {
        return this.delegate.addVertex(v);
    }

    @Override
    public boolean containsEdge(E e) {
        return this.delegate.containsEdge(e);
    }

    @Override
    public boolean containsVertex(V v) {
        return this.delegate.containsVertex(v);
    }

    public int degreeOf(V vertex) {
        return ((UndirectedGraph)this.delegate).degreeOf(vertex);
    }

    @Override
    public Set<E> edgeSet() {
        return this.delegate.edgeSet();
    }

    @Override
    public Set<E> edgesOf(V vertex) {
        return this.delegate.edgesOf(vertex);
    }

    public int inDegreeOf(V vertex) {
        return ((DirectedGraph)this.delegate).inDegreeOf(vertex);
    }

    public Set<E> incomingEdgesOf(V vertex) {
        return ((DirectedGraph)this.delegate).incomingEdgesOf(vertex);
    }

    public int outDegreeOf(V vertex) {
        return ((DirectedGraph)this.delegate).outDegreeOf(vertex);
    }

    public Set<E> outgoingEdgesOf(V vertex) {
        return ((DirectedGraph)this.delegate).outgoingEdgesOf(vertex);
    }

    @Override
    public boolean removeEdge(E e) {
        return this.delegate.removeEdge(e);
    }

    @Override
    public E removeEdge(V sourceVertex, V targetVertex) {
        return this.delegate.removeEdge(sourceVertex, targetVertex);
    }

    @Override
    public boolean removeVertex(V v) {
        return this.delegate.removeVertex(v);
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public Set<V> vertexSet() {
        return this.delegate.vertexSet();
    }

    @Override
    public V getEdgeSource(E e) {
        return this.delegate.getEdgeSource(e);
    }

    @Override
    public V getEdgeTarget(E e) {
        return this.delegate.getEdgeTarget(e);
    }

    @Override
    public double getEdgeWeight(E e) {
        return this.delegate.getEdgeWeight(e);
    }

    public void setEdgeWeight(E e, double weight) {
        ((WeightedGraph)this.delegate).setEdgeWeight(e, weight);
    }
}

