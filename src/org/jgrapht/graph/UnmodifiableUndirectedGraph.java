/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.UnmodifiableGraph;

public class UnmodifiableUndirectedGraph<V, E>
extends UnmodifiableGraph<V, E>
implements UndirectedGraph<V, E> {
    private static final long serialVersionUID = 3258134639355704624L;

    public UnmodifiableUndirectedGraph(UndirectedGraph<V, E> g) {
        super(g);
    }
}

