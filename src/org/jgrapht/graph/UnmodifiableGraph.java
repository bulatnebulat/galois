/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.GraphDelegator;

public class UnmodifiableGraph<V, E>
extends GraphDelegator<V, E>
implements Serializable {
    private static final long serialVersionUID = 3544957670722713913L;
    private static final String UNMODIFIABLE = "this graph is unmodifiable";

    public UnmodifiableGraph(Graph<V, E> g) {
        super(g);
    }

    @Override
    public E addEdge(V sourceVertex, V targetVertex) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public boolean addEdge(V sourceVertex, V targetVertex, E e) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
    }

    @Override
    public boolean addVertex(V v) {
        throw new UnsupportedOperationException("this graph is unmodifiable");
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
}

