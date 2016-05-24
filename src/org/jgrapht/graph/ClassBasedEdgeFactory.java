/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import java.io.Serializable;
import org.jgrapht.EdgeFactory;

public class ClassBasedEdgeFactory<V, E>
implements EdgeFactory<V, E>,
Serializable {
    private static final long serialVersionUID = 3618135658586388792L;
    private final Class<? extends E> edgeClass;

    public ClassBasedEdgeFactory(Class<? extends E> edgeClass) {
        this.edgeClass = edgeClass;
    }

    @Override
    public E createEdge(V source, V target) {
        try {
            return this.edgeClass.newInstance();
        }
        catch (Exception ex) {
            throw new RuntimeException("Edge factory failed", ex);
        }
    }
}

