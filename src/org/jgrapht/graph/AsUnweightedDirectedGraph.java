/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.io.Serializable;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.GraphDelegator;

public class AsUnweightedDirectedGraph<V, E>
extends GraphDelegator<V, E>
implements Serializable,
DirectedGraph<V, E> {
    private static final long serialVersionUID = -4320818446777715312L;

    public AsUnweightedDirectedGraph(DirectedGraph<V, E> g) {
        super(g);
    }

    @Override
    public double getEdgeWeight(E e) {
        return 1.0;
    }
}

