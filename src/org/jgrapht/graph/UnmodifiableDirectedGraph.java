/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.UnmodifiableGraph;

public class UnmodifiableDirectedGraph<V, E>
extends UnmodifiableGraph<V, E>
implements DirectedGraph<V, E> {
    private static final long serialVersionUID = 3978701783725913906L;

    public UnmodifiableDirectedGraph(DirectedGraph<V, E> g) {
        super(g);
    }
}

