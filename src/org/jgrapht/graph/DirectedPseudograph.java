/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

public class DirectedPseudograph<V, E>
extends AbstractBaseGraph<V, E>
implements DirectedGraph<V, E> {
    private static final long serialVersionUID = -8300409752893486415L;

    public DirectedPseudograph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }

    public DirectedPseudograph(EdgeFactory<V, E> ef) {
        super(ef, true, true);
    }
}

