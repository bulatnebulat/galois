/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.Pseudograph;

public class WeightedPseudograph<V, E>
extends Pseudograph<V, E>
implements WeightedGraph<V, E> {
    private static final long serialVersionUID = 3257290244524356152L;

    public WeightedPseudograph(EdgeFactory<V, E> ef) {
        super(ef);
    }

    public WeightedPseudograph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory(edgeClass));
    }
}

