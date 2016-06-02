package core;

import java.util.HashSet;
import java.util.Iterator;

public class MyGSH {
    public static int INTENT_LEVEL_INDEX = 0;
    public static int EXTENT_LEVEL_INDEX = 1;
    MyConceptSet setOfMaximumNodes = null;
    MyConceptSet setOfMinimumNodes = null;
    protected MyConceptSet setOfNodes = null;
    protected int numberOfNode = 0;
    protected int numberOfPrecedenceLinks = 0;
    protected String nameId = "Default_Name";
    protected int absoluteAddedNodes = 0;
    protected boolean isReducedlyLabelled = false;
    MyConceptSet[] levelIndex;
    int kindOfLevelIndex = INTENT_LEVEL_INDEX;

    public MyGSH(int maxSizeLevelIndex) {
        if (maxSizeLevelIndex < 0) {
            return;
        }
        this.levelIndex = new MyConceptSet[maxSizeLevelIndex + 1];
        int i = 0;
        while (i < this.levelIndex.length) {
            this.levelIndex[i] = new MyConceptSet();
            ++i;
        }
        this.setOfMaximumNodes = new MyConceptSet();
        this.setOfMinimumNodes = new MyConceptSet();
    }

    public MyGSH(int maxSizeLevelIndex, int kindOfLevelIndex) {
        if (maxSizeLevelIndex < 0) {
            return;
        }
        if (kindOfLevelIndex != INTENT_LEVEL_INDEX && kindOfLevelIndex != EXTENT_LEVEL_INDEX) {
            return;
        }
        this.levelIndex = new MyConceptSet[maxSizeLevelIndex + 1];
        int i = 0;
        while (i < this.levelIndex.length) {
            this.levelIndex[i] = new MyConceptSet();
            ++i;
        }
        this.kindOfLevelIndex = kindOfLevelIndex;
        this.setOfMaximumNodes = new MyConceptSet();
        this.setOfMinimumNodes = new MyConceptSet();
    }

    public void addConcept(MyConcept c) {
        if (c.getConceptualOwner() != null) {
            return;
        }
        if (c.getUpperCover().size() != 0) {
            return;
        }
        if (c.getLowerCover().size() != 0) {
            return;
        }
        c.setConceptualOwner(this);
        ++this.absoluteAddedNodes;
        c.setRankNumber(this.absoluteAddedNodes);
        if (this.kindOfLevelIndex == INTENT_LEVEL_INDEX) {
            if (c.getIntent().cardinality() >= this.levelIndex.length) {
                this.incrementLevelIndex(c.getIntent().cardinality() - this.levelIndex.length + 1);
            }
            this.levelIndex[c.getIntent().cardinality()].add(c);
        }
        if (this.kindOfLevelIndex == EXTENT_LEVEL_INDEX) {
            if (c.getExtent().cardinality() >= this.levelIndex.length) {
                this.incrementLevelIndex(c.getExtent().cardinality() - this.levelIndex.length + 1);
            }
            this.levelIndex[c.getExtent().cardinality()].add(c);
        }
        ++this.numberOfNode;
        this.setOfMaximumNodes.add(c);
        this.setOfMinimumNodes.add(c);
    }

    public void addElement(MyConcept elmt) {
        if (elmt.getConceptualOwner() != null) {
            return;
        }
        if (elmt.getUpperCover().size() != 0) {
            return;
        }
        if (elmt.getLowerCover().size() != 0) {
            return;
        }
        elmt.setConceptualOwner(this);
        ++this.absoluteAddedNodes;
        elmt.setRankNumber(this.absoluteAddedNodes);
        this.setOfNodes.add(elmt);
        ++this.numberOfNode;
        this.setOfMaximumNodes.add(elmt);
        this.setOfMinimumNodes.add(elmt);
    }

    public void addPrecedenceConnection(MyConcept lower, MyConcept greater) {
        if (lower.getConceptualOwner() == null) {
            this.addConcept(lower);
        }
        if (greater.getConceptualOwner() == null) {
            this.addConcept(greater);
        }
        if (lower.getConceptualOwner() != this || greater.getConceptualOwner() != this) {
            return;
        }
        if (lower.getConceptualOwner() == null) {
            this.addElement(lower);
        }
        if (greater.getConceptualOwner() == null) {
            this.addElement(greater);
        }
        if (lower.getUpperCover().contains(greater) || greater.getLowerCover().contains(lower)) {
            return;
        }
        if (lower.getConceptualOwner() != this || greater.getConceptualOwner() != this) {
            return;
        }
        if (lower.getUpperCover().size() == 0) {
            this.setOfMaximumNodes.remove(lower);
        }
        lower.getUpperCover().add(greater);
        if (greater.getLowerCover().size() == 0) {
            this.setOfMinimumNodes.remove(greater);
        }
        greater.getLowerCover().add(lower);
        ++this.numberOfPrecedenceLinks;
    }

    public void removeConcept(MyConcept c) {
        if (c.getConceptualOwner() == null) {
            return;
        }
        if (c.getConceptualOwner() != this) {
            return;
        }
        MyConceptSet allSup = new MyConceptSet();
        allSup.addAll(c.getUpperCover());
        MyConceptSet allInf = new MyConceptSet();
        allInf.addAll(c.getLowerCover());
        Iterator<MyConcept> it_sup = allSup.iterator();
        while (it_sup.hasNext()) {
            MyConcept nodeSup = it_sup.next();
            this.removePrecedenceConnection(c, nodeSup);
        }
        Iterator<MyConcept> it_inf = allInf.iterator();
        while (it_inf.hasNext()) {
            MyConcept nodeInf = it_inf.next();
            this.removePrecedenceConnection(nodeInf, c);
        }
        if (this.kindOfLevelIndex == INTENT_LEVEL_INDEX) {
            this.levelIndex[c.getIntent().cardinality()].remove(c);
        }
        if (this.kindOfLevelIndex == EXTENT_LEVEL_INDEX) {
            this.levelIndex[c.getExtent().cardinality()].remove(c);
        }
        --this.numberOfNode;
        this.setOfMaximumNodes.remove(c);
        this.setOfMinimumNodes.remove(c);
        c.setConceptualOwner(null);
    }

    public void removePrecedenceConnection(MyConcept lower, MyConcept greater) {
        if (lower.getConceptualOwner() == null) {
            return;
        }
        if (greater.getConceptualOwner() == null) {
            return;
        }
        if (lower.getConceptualOwner() != this || greater.getConceptualOwner() != this) {
            return;
        }
        if (lower.getConceptualOwner() == null) {
            return;
        }
        if (greater.getConceptualOwner() == null) {
            return;
        }
        if (lower.getConceptualOwner() != this || greater.getConceptualOwner() != this) {
            return;
        }
        if (lower.getUpperCover().contains(greater) && greater.getLowerCover().contains(lower)) {
            lower.getUpperCover().remove(greater);
            greater.getLowerCover().remove(lower);
            if (greater.getLowerCover().size() == 0) {
                this.setOfMinimumNodes.add(greater);
            }
            if (lower.getUpperCover().size() == 0) {
                this.setOfMaximumNodes.add(lower);
            }
            --this.numberOfPrecedenceLinks;
        }
    }

    public boolean isConceptualyOrdered(MyConcept first, MyConcept second) {
        if (first.getConceptualOwner() == null) {
            return false;
        }
        if (second.getConceptualOwner() == null) {
            return false;
        }
        if (first.getConceptualOwner() != this || second.getConceptualOwner() != this) {
            return false;
        }
        if (!(second.getIntent().containsAll(first.getIntent()) && first.getExtent().containsAll(second.getExtent()) || second.getExtent().containsAll(first.getExtent()) && first.getIntent().containsAll(second.getIntent()))) {
            return false;
        }
        return true;
    }

    public boolean isConceptualyGreater(MyConcept first, MyConcept second) {
        if (first.getConceptualOwner() == null) {
            return false;
        }
        if (second.getConceptualOwner() == null) {
            return false;
        }
        if (first.getConceptualOwner() != this || second.getConceptualOwner() != this) {
            return false;
        }
        if (first.getExtent().containsAll(second.getExtent()) && second.getIntent().containsAll(first.getIntent())) {
            return true;
        }
        return false;
    }

    public boolean isConceptualyLower(MyConcept first, MyConcept second) {
        if (first.getConceptualOwner() == null) {
            return false;
        }
        if (second.getConceptualOwner() == null) {
            return false;
        }
        if (first.getConceptualOwner() != this || second.getConceptualOwner() != this) {
            return false;
        }
        return this.isConceptualyGreater(second, first);
    }

    public void addAttributeToConcept(MyConcept c, int a) {
        if (this.kindOfLevelIndex == INTENT_LEVEL_INDEX) {
            this.levelIndex[c.getIntent().cardinality()].remove(c);
            c.getIntent().add(a);
            if (c.getIntent().cardinality() == this.levelIndex.length) {
                this.incrementLevelIndex(1);
            }
            this.levelIndex[c.getIntent().cardinality()].add(c);
        } else {
            c.getIntent().add(a);
        }
    }

    public void addObjectToConcept(MyConcept c, int o) {
        if (this.kindOfLevelIndex == EXTENT_LEVEL_INDEX) {
            this.levelIndex[c.getExtent().cardinality()].remove(c);
            c.getExtent().add(o);
            if (c.getExtent().cardinality() == this.levelIndex.length) {
                this.incrementLevelIndex(1);
            }
            this.levelIndex[c.getExtent().cardinality()].add(c);
        } else {
            c.getExtent().add(o);
        }
    }

    public void incrementLevelIndex(int nbIncrement) {
        MyConceptSet[] newLevelIndex = new MyConceptSet[this.levelIndex.length + nbIncrement];
        int i = 0;
        while (i < newLevelIndex.length) {
            newLevelIndex[i] = i < this.levelIndex.length ? this.levelIndex[i] : new MyConceptSet();
            ++i;
        }
        this.levelIndex = newLevelIndex;
    }

    public IMySet getIntent(int o) {
        return this.getObjectConcept(o).getIntent();
    }

    public IMySet getExtent(int a) {
        return this.getAttributeConcept(a).getExtent();
    }

    public MyConcept getObjectConcept(int o) {
        MyBottomUpIterator it = this.getBottomUpIterator();
        while (it.hasNext()) {
            MyConcept cCur = it.next();
            if (!cCur.getExtent().contains(o)) continue;
            return cCur;
        }
        return null;
    }

    public MyConcept getAttributeConcept(int a) {
        MyTopDownIterator it = this.getTopDownIterator();
        while (it.hasNext()) {
            MyConcept cCur = it.next();
            if (!cCur.getIntent().contains(a)) continue;
            return cCur;
        }
        return null;
    }

    public MyConcept find(MyConcept c) {
        if (this.kindOfLevelIndex == INTENT_LEVEL_INDEX) {
            Iterator<MyConcept> it = this.levelIndex[c.getIntent().cardinality()].iterator();
            while (it.hasNext()) {
                MyConcept cC = it.next();
                if (!cC.getIntent().equals(c.getIntent())) continue;
                return cC;
            }
            return null;
        }
        Iterator<MyConcept> it = this.levelIndex[c.getExtent().cardinality()].iterator();
        while (it.hasNext()) {
            MyConcept cC = it.next();
            if (!cC.getIntent().equals(c.getIntent())) continue;
            return cC;
        }
        return null;
    }

    public boolean isReduicedlyLabelled() {
        return this.isReducedlyLabelled;
    }

    public void isReduicedlyLabelled(boolean newVal) {
        this.isReducedlyLabelled = newVal;
    }

    public void updateReducedLabel() {
        HashSet<Integer> itemset = new HashSet<Integer>();
        HashSet<Integer> tidSet = new HashSet<Integer>();
        MyTopDownIterator it = this.getTopDownIterator();
        while (it.hasNext()) {
            MyConcept cCur = it.next();
            cCur.getReducedExtent().clearAll(cCur.getReducedExtent());
            cCur.getReducedIntent().clearAll(cCur.getReducedIntent());
            Iterator<Integer> itExt = cCur.getExtent().iterator();
            while (itExt.hasNext()) {
                int fo = itExt.next();
                if (tidSet.contains(fo)) continue;
                boolean addFo = true;
                Iterator<MyConcept> itChild = cCur.getLowerCover().iterator();
                while (itChild.hasNext() && addFo) {
                    MyConcept childCur = itChild.next();
                    boolean bl = addFo = !childCur.getExtent().contains(fo);
                }
                if (!addFo) continue;
                tidSet.add(fo);
                cCur.getReducedExtent().add(fo);
            }
            Iterator<Integer> itInt = cCur.getIntent().iterator();
            while (itInt.hasNext()) {
                int fa = itInt.next();
                if (itemset.contains(fa)) continue;
                itemset.add(fa);
                cCur.getReducedIntent().add(fa);
            }
        }
        this.isReducedlyLabelled = true;
    }

    public MyBasicIterator getBasicIterator() {
        return new MyBasicIterator(this);
    }

    public MyTopDownIterator getTopDownIterator() {
        return new MyTopDownIterator(this);
    }

    public MyBottomUpIterator getBottomUpIterator() {
        return new MyBottomUpIterator(this);
    }

    public MyConceptSet getUpperBounds(MyConceptSet elmts) {
        return null;
    }

    public MyConceptSet getLowerBounds(MyConceptSet elmts) {
        return null;
    }

    public boolean isEmpty() {
        if (this.numberOfNode == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return this.numberOfNode;
    }

    public String getNameId() {
        return this.nameId;
    }

    public String toString() {
        return this.nameId;
    }

    public void setNameId(String theNameId) {
        this.nameId = theNameId;
    }

    public MyConceptSet getMaximals() {
        return this.setOfMaximumNodes;
    }

    public MyConceptSet getMinimals() {
        return this.setOfMinimumNodes;
    }

    public int numberOfPrecedenceLinks() {
        return this.numberOfPrecedenceLinks;
    }

    public int getAbsoluteNumberOfAddedNodes() {
        return this.absoluteAddedNodes;
    }

    public MyConcept getTopTentative() {
        MyConcept top = null;
        if (this.setOfMaximumNodes.size() == 1) {
            top = this.setOfMaximumNodes.iterator().next();
        }
        return top;
    }

    public MyConcept getBottomTentative() {
        MyConcept bottom = null;
        if (this.setOfMinimumNodes.size() == 1) {
            bottom = this.setOfMinimumNodes.iterator().next();
        }
        return bottom;
    }
}

