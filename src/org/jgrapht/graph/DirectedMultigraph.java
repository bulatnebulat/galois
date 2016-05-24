/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

public class DirectedMultigraph<V, E>
extends AbstractBaseGraph<V, E>
implements DirectedGraph<V, E> {
    private static final long serialVersionUID = 3258408413590599219L;

    public DirectedMultigraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }

    public DirectedMultigraph(EdgeFactory<V, E> ef) {
        super(ef, true, true);
    }
}

