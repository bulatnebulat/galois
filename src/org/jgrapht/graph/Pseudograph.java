/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

public class Pseudograph<V, E>
extends AbstractBaseGraph<V, E>
implements UndirectedGraph<V, E> {
    private static final long serialVersionUID = 3833183614484755253L;

    public Pseudograph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }

    public Pseudograph(EdgeFactory<V, E> ef) {
        super(ef, true, true);
    }
}

