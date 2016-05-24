/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.util.WeightCombiner;

public class GraphUnion<V, E, G extends Graph<V, E>>
extends AbstractGraph<V, E>
implements Serializable {
    private static final long serialVersionUID = -740199233080172450L;
    private static final String READ_ONLY = "union of graphs is read-only";
    private G g1;
    private G g2;
    private WeightCombiner operator;

    public GraphUnion(G g1, G g2, WeightCombiner operator) {
        if (g1 == null) {
            throw new NullPointerException("g1 is null");
        }
        if (g2 == null) {
            throw new NullPointerException("g2 is null");
        }
        if (g1 == g2) {
            throw new IllegalArgumentException("g1 is equal to g2");
        }
        this.g1 = g1;
        this.g2 = g2;
        this.operator = operator;
    }

    public GraphUnion(G g1, G g2) {
        this(g1, g2, WeightCombiner.SUM);
    }

    @Override
    public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
        HashSet res = new HashSet();
        if (this.g1.containsVertex(sourceVertex) && this.g1.containsVertex(targetVertex)) {
            res.addAll(this.g1.getAllEdges(sourceVertex, targetVertex));
        }
        if (this.g2.containsVertex(sourceVertex) && this.g2.containsVertex(targetVertex)) {
            res.addAll(this.g2.getAllEdges(sourceVertex, targetVertex));
        }
        return Collections.unmodifiableSet(res);
    }

    @Override
    public E getEdge(V sourceVertex, V targetVertex) {
        E res = null;
        if (this.g1.containsVertex(sourceVertex) && this.g1.containsVertex(targetVertex)) {
            res = this.g1.getEdge(sourceVertex, targetVertex);
        }
        if (res == null && this.g2.containsVertex(sourceVertex) && this.g2.containsVertex(targetVertex)) {
            res = this.g2.getEdge(sourceVertex, targetVertex);
        }
        return res;
    }

    @Override
    public EdgeFactory<V, E> getEdgeFactory() {
        throw new UnsupportedOperationException("union of graphs is read-only");
    }

    @Override
    public E addEdge(V sourceVertex, V targetVertex) {
        throw new UnsupportedOperationException("union of graphs is read-only");
    }

    @Override
    public boolean addEdge(V sourceVertex, V targetVertex, E e) {
        throw new UnsupportedOperationException("union of graphs is read-only");
    }

    @Override
    public boolean addVertex(V v) {
        throw new UnsupportedOperationException("union of graphs is read-only");
    }

    @Override
    public boolean containsEdge(E e) {
        if (!this.g1.containsEdge(e) && !this.g2.containsEdge(e)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean containsVertex(V v) {
        if (!this.g1.containsVertex(v) && !this.g2.containsVertex(v)) {
            return false;
        }
        return true;
    }

    @Override
    public Set<E> edgeSet() {
        HashSet res = new HashSet();
        res.addAll(this.g1.edgeSet());
        res.addAll(this.g2.edgeSet());
        return Collections.unmodifiableSet(res);
    }

    @Override
    public Set<E> edgesOf(V vertex) {
        HashSet res = new HashSet();
        if (this.g1.containsVertex(vertex)) {
            res.addAll(this.g1.edgesOf(vertex));
        }
        if (this.g2.containsVertex(vertex)) {
            res.addAll(this.g2.edgesOf(vertex));
        }
        return Collections.unmodifiableSet(res);
    }

    @Override
    public E removeEdge(V sourceVertex, V targetVertex) {
        throw new UnsupportedOperationException("union of graphs is read-only");
    }

    @Override
    public boolean removeEdge(E e) {
        throw new UnsupportedOperationException("union of graphs is read-only");
    }

    @Override
    public boolean removeVertex(V v) {
        throw new UnsupportedOperationException("union of graphs is read-only");
    }

    @Override
    public Set<V> vertexSet() {
        HashSet res = new HashSet();
        res.addAll(this.g1.vertexSet());
        res.addAll(this.g2.vertexSet());
        return Collections.unmodifiableSet(res);
    }

    @Override
    public V getEdgeSource(E e) {
        if (this.g1.containsEdge(e)) {
            return this.g1.getEdgeSource(e);
        }
        if (this.g2.containsEdge(e)) {
            return this.g2.getEdgeSource(e);
        }
        return null;
    }

    @Override
    public V getEdgeTarget(E e) {
        if (this.g1.containsEdge(e)) {
            return this.g1.getEdgeTarget(e);
        }
        if (this.g2.containsEdge(e)) {
            return this.g2.getEdgeTarget(e);
        }
        return null;
    }

    @Override
    public double getEdgeWeight(E e) {
        if (this.g1.containsEdge(e) && this.g2.containsEdge(e)) {
            return this.operator.combine(this.g1.getEdgeWeight(e), this.g2.getEdgeWeight(e));
        }
        if (this.g1.containsEdge(e)) {
            return this.g1.getEdgeWeight(e);
        }
        if (this.g2.containsEdge(e)) {
            return this.g2.getEdgeWeight(e);
        }
        throw new IllegalArgumentException("no such edge in the union");
    }

    public G getG1() {
        return this.g1;
    }

    public G getG2() {
        return this.g2;
    }
}

