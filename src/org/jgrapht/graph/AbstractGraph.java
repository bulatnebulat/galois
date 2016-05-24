/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

public abstract class AbstractGraph<V, E>
implements Graph<V, E> {
    @Override
    public boolean containsEdge(V sourceVertex, V targetVertex) {
        if (this.getEdge(sourceVertex, targetVertex) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAllEdges(Collection<? extends E> edges) {
        boolean modified = false;
        for (E e : edges) {
            modified |= this.removeEdge(e);
        }
        return modified;
    }

    @Override
    public Set<E> removeAllEdges(V sourceVertex, V targetVertex) {
        Set removed = this.getAllEdges(sourceVertex, targetVertex);
        this.removeAllEdges(removed);
        return removed;
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> vertices) {
        boolean modified = false;
        for (V v : vertices) {
            modified |= this.removeVertex(v);
        }
        return modified;
    }

    public String toString() {
        return this.toStringFromSets(this.vertexSet(), this.edgeSet(), this instanceof DirectedGraph);
    }

    protected boolean assertVertexExist(V v) {
        if (this.containsVertex(v)) {
            return true;
        }
        if (v == null) {
            throw new NullPointerException();
        }
        throw new IllegalArgumentException("no such vertex in graph");
    }

    protected boolean removeAllEdges(E[] edges) {
        boolean modified = false;
        int i = 0;
        while (i < edges.length) {
            modified |= this.removeEdge(edges[i]);
            ++i;
        }
        return modified;
    }

    protected String toStringFromSets(Collection<? extends V> vertexSet, Collection<? extends E> edgeSet, boolean directed) {
        ArrayList<String> renderedEdges = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        for (E e : edgeSet) {
            if (e.getClass() != DefaultEdge.class && e.getClass() != DefaultWeightedEdge.class) {
                sb.append(e.toString());
                sb.append("=");
            }
            if (directed) {
                sb.append("(");
            } else {
                sb.append("{");
            }
            sb.append(this.getEdgeSource(e));
            sb.append(",");
            sb.append(this.getEdgeTarget(e));
            if (directed) {
                sb.append(")");
            } else {
                sb.append("}");
            }
            renderedEdges.add(sb.toString());
            sb.setLength(0);
        }
        return "(" + vertexSet + ", " + renderedEdges + ")";
    }
}

