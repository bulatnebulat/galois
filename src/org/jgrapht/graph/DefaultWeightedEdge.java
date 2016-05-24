/*
 * Decompiled with CFR 0_114.
 */
package org.jgrapht.graph;

import org.jgrapht.graph.DefaultEdge;

public class DefaultWeightedEdge
extends DefaultEdge {
    private static final long serialVersionUID = 229708706467350994L;
    double weight = 1.0;

    protected double getWeight() {
        return this.weight;
    }
}

