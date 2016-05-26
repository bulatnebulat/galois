/*
 * Decompiled with CFR 0_114.
 */
package core;

import core.MyConcept;
import core.MyConceptSet;
import core.MyGSH;
import java.util.Iterator;

public class MyBasicIterator {
    protected Iterator<MyConcept> basicIterator = null;
    protected int indexBasicIterator = -1;
    protected int nbNodeToReachWithBasicIterator = 0;
    MyGSH theConceptualOrder = null;

    protected MyBasicIterator(MyGSH theConceptualOrder) {
        this.theConceptualOrder = theConceptualOrder;
        this.basicIterator = null;
        this.indexBasicIterator = -1;
        this.nbNodeToReachWithBasicIterator = theConceptualOrder.size();
        if (this.nbNodeToReachWithBasicIterator > 0) {
            int i = 0;
            while (this.basicIterator == null && i < theConceptualOrder.levelIndex.length) {
                if (theConceptualOrder.levelIndex[i].size() != 0) {
                    this.basicIterator = theConceptualOrder.levelIndex[i].iterator();
                    this.indexBasicIterator = i;
                }
                ++i;
            }
        }
    }

    public boolean hasNext() {
        if (this.nbNodeToReachWithBasicIterator == 0) {
            return false;
        }
        return true;
    }

    public MyConcept next() {
        if (this.nbNodeToReachWithBasicIterator == 0) {
            return null;
        }
        MyConcept C = null;
        if (!this.basicIterator.hasNext()) {
            this.basicIterator = null;
            int i = this.indexBasicIterator + 1;
            while (this.basicIterator == null && i < this.theConceptualOrder.levelIndex.length) {
                if (this.theConceptualOrder.levelIndex[i].size() != 0) {
                    this.basicIterator = this.theConceptualOrder.levelIndex[i].iterator();
                    this.indexBasicIterator = i;
                }
                ++i;
            }
        }
        C = this.basicIterator.next();
        --this.nbNodeToReachWithBasicIterator;
        return C;
    }
}

