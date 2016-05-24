/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

public class DefaultDirectedGraph<V, E>
extends AbstractBaseGraph<V, E>
implements DirectedGraph<V, E> {
    private static final long serialVersionUID = 3544953246956466230L;

    public DefaultDirectedGraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }

    public DefaultDirectedGraph(EdgeFactory<V, E> ef) {
        super(ef, false, true);
    }
}

