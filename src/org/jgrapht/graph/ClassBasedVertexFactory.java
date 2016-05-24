/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.VertexFactory;

public class ClassBasedVertexFactory<V>
implements VertexFactory<V> {
    private final Class<? extends V> vertexClass;

    public ClassBasedVertexFactory(Class<? extends V> vertexClass) {
        this.vertexClass = vertexClass;
    }

    @Override
    public V createVertex() {
        try {
            return this.vertexClass.newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException("Vertex factory failed", e);
        }
    }
}

