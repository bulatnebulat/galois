/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

public class SimpleDirectedGraph<V, E>
extends AbstractBaseGraph<V, E>
implements DirectedGraph<V, E> {
    private static final long serialVersionUID = 4049358608472879671L;

    public SimpleDirectedGraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }

    public SimpleDirectedGraph(EdgeFactory<V, E> ef) {
        super(ef, false, false);
    }
}

