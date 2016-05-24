/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.GraphUnion;
import org.jgrapht.util.WeightCombiner;

public class DirectedGraphUnion<V, E>
extends GraphUnion<V, E, DirectedGraph<V, E>>
implements DirectedGraph<V, E> {
    private static final long serialVersionUID = -740199233080172450L;

    DirectedGraphUnion(DirectedGraph<V, E> g1, DirectedGraph<V, E> g2, WeightCombiner operator) {
        super(g1, g2, operator);
    }

    DirectedGraphUnion(DirectedGraph<V, E> g1, DirectedGraph<V, E> g2) {
        super(g1, g2);
    }

    @Override
    public int inDegreeOf(V vertex) {
        Set<E> res = this.incomingEdgesOf(vertex);
        return res.size();
    }

    @Override
    public Set<E> incomingEdgesOf(V vertex) {
        HashSet res = new HashSet();
        if (((DirectedGraph)this.getG1()).containsVertex(vertex)) {
            res.addAll(((DirectedGraph)this.getG1()).incomingEdgesOf(vertex));
        }
        if (((DirectedGraph)this.getG2()).containsVertex(vertex)) {
            res.addAll(((DirectedGraph)this.getG2()).incomingEdgesOf(vertex));
        }
        return Collections.unmodifiableSet(res);
    }

    @Override
    public int outDegreeOf(V vertex) {
        Set<E> res = this.outgoingEdgesOf(vertex);
        return res.size();
    }

    @Override
    public Set<E> outgoingEdgesOf(V vertex) {
        HashSet res = new HashSet();
        if (((DirectedGraph)this.getG1()).containsVertex(vertex)) {
            res.addAll(((DirectedGraph)this.getG1()).outgoingEdgesOf(vertex));
        }
        if (((DirectedGraph)this.getG2()).containsVertex(vertex)) {
            res.addAll(((DirectedGraph)this.getG2()).outgoingEdgesOf(vertex));
        }
        return Collections.unmodifiableSet(res);
    }
}

