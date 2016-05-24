/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.graph.MaskEdgeSet;
import org.jgrapht.graph.MaskFunctor;
import org.jgrapht.graph.MaskVertexSet;

public class MaskSubgraph<V, E>
extends AbstractGraph<V, E> {
    private static final String UNMODIFIABLE = "this graph is unmodifiable";
    private Graph<V, E> base;
    private Set<E> edges;
    private MaskFunctor<V, E> mask;
    private Set<V> vertices;

    public MaskSubgraph(Graph<V, E> base, MaskFunctor<V, E> mask) {
        this.base = base;
        this.mask = mask;
        this.vertices = new MaskVertexSet<V, E>(base.vertexSet(), mask);
        this.edges = new MaskEdgeSet<V, E>(base, base.edgeSet(), mask);
    }

    @Override
    public E addEdge(V sourceVertex, V targetVertex) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public boolean addEdge(V sourceVertex, V targetVertex, E edge) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public boolean addVertex(V v) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public boolean containsEdge(E e) {
        return this.edgeSet().contains(e);
    }

    @Override
    public boolean containsVertex(V v) {
        if (!this.mask.isVertexMasked(v) && this.base.containsVertex(v)) {
            return true;
        }
        return false;
    }

    public int degreeOf(V vertex) {
        return this.edgesOf(vertex).size();
    }

    @Override
    public Set<E> edgeSet() {
        return this.edges;
    }

    @Override
    public Set<E> edgesOf(V vertex) {
        this.assertVertexExist(vertex);
        return new MaskEdgeSet<V, E>(this.base, this.base.edgesOf(vertex), this.mask);
    }

    @Override
    public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
        Set<E> edges = null;
        if (this.containsVertex(sourceVertex) && this.containsVertex(targetVertex)) {
            return new MaskEdgeSet<V, E>(this.base, this.base.getAllEdges(sourceVertex, targetVertex), this.mask);
        }
        return edges;
    }

    @Override
    public E getEdge(V sourceVertex, V targetVertex) {
        Set<E> edges = this.getAllEdges(sourceVertex, targetVertex);
        if (edges == null || edges.isEmpty()) {
            return null;
        }
        return edges.iterator().next();
    }

    @Override
    public EdgeFactory<V, E> getEdgeFactory() {
        return this.base.getEdgeFactory();
    }

    @Override
    public V getEdgeSource(E edge) {
        assert (this.edgeSet().contains(edge));
        return this.base.getEdgeSource(edge);
    }

    @Override
    public V getEdgeTarget(E edge) {
        assert (this.edgeSet().contains(edge));
        return this.base.getEdgeTarget(edge);
    }

    @Override
    public double getEdgeWeight(E edge) {
        assert (this.edgeSet().contains(edge));
        return this.base.getEdgeWeight(edge);
    }

    public Set<E> incomingEdgesOf(V vertex) {
        this.assertVertexExist(vertex);
        return new MaskEdgeSet<V, E>(this.base, ((DirectedGraph)this.base).incomingEdgesOf(vertex), this.mask);
    }

    public int inDegreeOf(V vertex) {
        return this.incomingEdgesOf(vertex).size();
    }

    public int outDegreeOf(V vertex) {
        return this.outgoingEdgesOf(vertex).size();
    }

    public Set<E> outgoingEdgesOf(V vertex) {
        this.assertVertexExist(vertex);
        return new MaskEdgeSet<V, E>(this.base, ((DirectedGraph)this.base).outgoingEdgesOf(vertex), this.mask);
    }

    @Override
    public boolean removeAllEdges(Collection<? extends E> edges) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public Set<E> removeAllEdges(V sourceVertex, V targetVertex) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> vertices) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public boolean removeEdge(E e) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public E removeEdge(V sourceVertex, V targetVertex) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public boolean removeVertex(V v) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public Set<V> vertexSet() {
        return this.vertices;
    }
}

