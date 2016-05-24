/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

public class SimpleGraph<V, E>
extends AbstractBaseGraph<V, E>
implements UndirectedGraph<V, E> {
    private static final long serialVersionUID = 3545796589454112304L;

    public SimpleGraph(EdgeFactory<V, E> ef) {
        super(ef, false, false);
    }

    public SimpleGraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }
}

