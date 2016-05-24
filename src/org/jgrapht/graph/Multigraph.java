/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

public class Multigraph<V, E>
extends AbstractBaseGraph<V, E>
implements UndirectedGraph<V, E> {
    private static final long serialVersionUID = 3257001055819871795L;

    public Multigraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }

    public Multigraph(EdgeFactory<V, E> ef) {
        super(ef, true, false);
    }
}

