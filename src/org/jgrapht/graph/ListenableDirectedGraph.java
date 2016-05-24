/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;

public class ListenableDirectedGraph<V, E>
extends DefaultListenableGraph<V, E>
implements DirectedGraph<V, E> {
    private static final long serialVersionUID = 3257571698126368824L;

    public ListenableDirectedGraph(Class<? extends E> edgeClass) {
        this(new DefaultDirectedGraph(edgeClass));
    }

    public ListenableDirectedGraph(DirectedGraph<V, E> base) {
        super(base);
    }
}

