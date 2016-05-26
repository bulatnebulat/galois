/*
 * Decompiled with CFR 0_114.
 */
package core;

import core.IMySet;
import core.MyConcept;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyConceptSet {
    HashSet<MyConcept> concepts = new HashSet();

    public int size() {
        return this.concepts.size();
    }

    public void add(MyConcept cpt) {
        this.concepts.add(cpt);
    }

    public void addAll(MyConceptSet unCptSet) {
        this.concepts.addAll(unCptSet.concepts);
    }

    public boolean remove(MyConcept cpt) {
        return this.concepts.remove(cpt);
    }

    public void removeAll(MyConceptSet unCptSet) {
        this.concepts.removeAll(unCptSet.concepts);
    }

    public boolean contains(MyConcept cpt) {
        return this.concepts.contains(cpt);
    }

    public boolean containsAll(MyConceptSet set) {
        return this.concepts.containsAll(set.concepts);
    }

    public Iterator<MyConcept> iterator() {
        return this.concepts.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        MyConceptSet mcs = new MyConceptSet();
        Iterator<MyConcept> it = this.iterator();
        while (it.hasNext()) {
            MyConcept cpt = it.next();
            mcs.add((MyConcept)cpt.clone());
        }
        return mcs;
    }

    public MyConceptSet getNewUnionWith(MyConceptSet unCptSet) throws CloneNotSupportedException {
        MyConceptSet unionSet = (MyConceptSet)this.clone();
        unionSet.concepts.addAll(unCptSet.concepts);
        return unionSet;
    }

    public MyConceptSet getNewIntersectionWith(MyConceptSet unCptSet) {
        MyConceptSet intersectionSet = new MyConceptSet();
        Iterator<MyConcept> i = this.iterator();
        while (i.hasNext()) {
            MyConcept mc = i.next();
            if (!unCptSet.contains(mc)) continue;
            intersectionSet.add(mc);
        }
        return intersectionSet;
    }

    public MyConceptSet getNewDifferenceWith(MyConceptSet unCptSet) throws CloneNotSupportedException {
        MyConceptSet diffSet = (MyConceptSet)this.clone();
        Iterator<MyConcept> i = unCptSet.iterator();
        while (i.hasNext()) {
            diffSet.remove(i.next());
        }
        return diffSet;
    }

    public boolean equals(MyConceptSet unCptSet) {
        if (unCptSet.size() == this.size() && unCptSet.containsAll(unCptSet)) {
            return true;
        }
        return false;
    }

    public MyConcept[] getIncreasingIntentSorted() {
        MyConcept[] ret = new MyConcept[this.size()];
        int i = 0;
        Iterator<MyConcept> it = this.iterator();
        while (it.hasNext()) {
            ret[i++] = it.next();
        }
        i = 0;
        while (i < ret.length) {
            int j = i;
            while (j < ret.length) {
                if (ret[i].getIntent().cardinality() > ret[j].getIntent().cardinality()) {
                    MyConcept tmp = ret[i];
                    ret[i] = ret[j];
                    ret[j] = tmp;
                }
                ++j;
            }
            ++i;
        }
        return ret;
    }

    public MyConcept[] getIncreasingExtentSorted() {
        MyConcept[] ret = new MyConcept[this.size()];
        int i = 0;
        Iterator<MyConcept> it = this.iterator();
        while (it.hasNext()) {
            ret[i++] = it.next();
        }
        i = 0;
        while (i < ret.length) {
            int j = i;
            while (j < ret.length) {
                if (ret[i].getExtent().cardinality() > ret[j].getExtent().cardinality()) {
                    MyConcept tmp = ret[i];
                    ret[i] = ret[j];
                    ret[j] = tmp;
                }
                ++j;
            }
            ++i;
        }
        return ret;
    }

    public MyConcept[] getDecreasingIntentSorted() {
        MyConcept[] ret = new MyConcept[this.size()];
        int i = 0;
        Iterator<MyConcept> it = this.iterator();
        while (it.hasNext()) {
            ret[i++] = it.next();
        }
        i = 0;
        while (i < ret.length) {
            int j = i;
            while (j < ret.length) {
                if (ret[i].getIntent().cardinality() < ret[j].getIntent().cardinality()) {
                    MyConcept tmp = ret[i];
                    ret[i] = ret[j];
                    ret[j] = tmp;
                }
                ++j;
            }
            ++i;
        }
        return ret;
    }

    public MyConcept[] getDecreasingExtentSorted() {
        MyConcept[] tab = new MyConcept[this.size()];
        int i = 0;
        Iterator<MyConcept> it = this.iterator();
        while (it.hasNext()) {
            tab[i++] = it.next();
        }
        this.sortConcepTatbOnDecExtent(tab, 0, tab.length - 1);
        return tab;
    }

    protected void sortConcepTatbOnDecExtent(MyConcept[] tab, int start, int end) {
        if (start == end) {
            return;
        }
        if (end == start + 1) {
            if (tab[start].getExtent().cardinality() < tab[end].getExtent().cardinality()) {
                MyConcept tmp = tab[start];
                tab[start] = tab[end];
                tab[end] = tmp;
            }
            return;
        }
        int midle = start + (end - start) / 2;
        this.sortConcepTatbOnDecExtent(tab, start, midle);
        this.sortConcepTatbOnDecExtent(tab, midle + 1, end);
        int i = start;
        int j = midle + 1;
        int k = 0;
        MyConcept[] sortedTab = new MyConcept[end - start + 1];
        while (i <= midle || j <= end) {
            sortedTab[k++] = i <= midle && j > end ? tab[i++] : (i > midle && j <= end ? tab[j++] : (tab[i].getExtent().cardinality() > tab[j].getExtent().cardinality() ? tab[i++] : tab[j++]));
        }
        while (--k >= 0) {
            tab[start + k] = sortedTab[k];
        }
    }

    public boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        }
        return false;
    }

    public Set<MyConcept> values() {
        return this.concepts;
    }
}

