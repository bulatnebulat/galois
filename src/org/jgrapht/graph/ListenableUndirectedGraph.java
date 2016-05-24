/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.SimpleGraph;

public class ListenableUndirectedGraph<V, E>
extends DefaultListenableGraph<V, E>
implements UndirectedGraph<V, E> {
    private static final long serialVersionUID = 3256999969193145905L;

    public ListenableUndirectedGraph(Class<? extends E> edgeClass) {
        this(new SimpleGraph(edgeClass));
    }

    public ListenableUndirectedGraph(UndirectedGraph<V, E> base) {
        super(base);
    }
}

