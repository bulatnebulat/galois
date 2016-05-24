/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.io.Serializable;
import org.jgrapht.Graph;
import org.jgrapht.graph.GraphDelegator;

public class AsUnweightedGraph<V, E>
extends GraphDelegator<V, E>
implements Serializable {
    private static final long serialVersionUID = 7175505077601824663L;

    public AsUnweightedGraph(Graph<V, E> g) {
        super(g);
    }

    @Override
    public double getEdgeWeight(E e) {
        return 1.0;
    }
}

