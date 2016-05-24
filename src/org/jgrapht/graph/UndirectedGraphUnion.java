/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.util.Set;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.GraphUnion;
import org.jgrapht.util.WeightCombiner;

public class UndirectedGraphUnion<V, E>
extends GraphUnion<V, E, UndirectedGraph<V, E>>
implements UndirectedGraph<V, E> {
    private static final long serialVersionUID = -740199233080172450L;

    UndirectedGraphUnion(UndirectedGraph<V, E> g1, UndirectedGraphUnion<V, E> g2, WeightCombiner operator) {
        super(g1, g2, operator);
    }

    UndirectedGraphUnion(UndirectedGraph<V, E> g1, UndirectedGraphUnion<V, E> g2) {
        super(g1, g2);
    }

    @Override
    public int degreeOf(V vertex) {
        Set res = this.edgesOf(vertex);
        return res.size();
    }
}

