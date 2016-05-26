/*
 * Decompiled with CFR 0_114.
 */
package core;

import core.MyConcept;
import core.MyConceptSet;
import core.MyGSH;
import java.util.Iterator;

public class MyBottomUpIterator {
    protected Iterator basicIterator = null;
    protected int indexBasicIterator = -1;
    protected int nbNodeToReachWithBasicIterator = 0;
    MyGSH theConceptalSructure = null;

    protected MyBottomUpIterator(MyGSH theConceptualStructure) {
        int i;
        this.theConceptalSructure = theConceptualStructure;
        if (theConceptualStructure.kindOfLevelIndex == MyGSH.EXTENT_LEVEL_INDEX) {
            this.basicIterator = null;
            this.indexBasicIterator = -1;
            this.nbNodeToReachWithBasicIterator = theConceptualStructure.size();
            if (this.nbNodeToReachWithBasicIterator > 0) {
                i = 0;
                while (this.basicIterator == null && i < theConceptualStructure.levelIndex.length) {
                    if (theConceptualStructure.levelIndex[i].size() != 0) {
                        this.basicIterator = theConceptualStructure.levelIndex[i].iterator();
                        this.indexBasicIterator = i;
                    }
                    ++i;
                }
            }
        }
        if (theConceptualStructure.kindOfLevelIndex == MyGSH.INTENT_LEVEL_INDEX) {
            this.basicIterator = null;
            this.indexBasicIterator = -1;
            this.nbNodeToReachWithBasicIterator = theConceptualStructure.size();
            if (this.nbNodeToReachWithBasicIterator > 0) {
                i = theConceptualStructure.levelIndex.length - 1;
                while (this.basicIterator == null && i >= 0) {
                    if (theConceptualStructure.levelIndex[i].size() != 0) {
                        this.basicIterator = theConceptualStructure.levelIndex[i].iterator();
                        this.indexBasicIterator = i;
                    }
                    --i;
                }
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
            int i;
            this.basicIterator = null;
            if (this.theConceptalSructure.kindOfLevelIndex == MyGSH.EXTENT_LEVEL_INDEX) {
                i = this.indexBasicIterator + 1;
                while (this.basicIterator == null && i < this.theConceptalSructure.levelIndex.length) {
                    if (this.theConceptalSructure.levelIndex[i].size() != 0) {
                        this.basicIterator = this.theConceptalSructure.levelIndex[i].iterator();
                        this.indexBasicIterator = i;
                    }
                    ++i;
                }
            }
            if (this.theConceptalSructure.kindOfLevelIndex == MyGSH.INTENT_LEVEL_INDEX) {
                i = this.indexBasicIterator - 1;
                while (this.basicIterator == null && i >= 0) {
                    if (this.theConceptalSructure.levelIndex[i].size() != 0) {
                        this.basicIterator = this.theConceptalSructure.levelIndex[i].iterator();
                        this.indexBasicIterator = i;
                    }
                    --i;
                }
            }
        }
        C = (MyConcept)this.basicIterator.next();
        --this.nbNodeToReachWithBasicIterator;
        return C;
    }
}

