package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import fr.lirmm.marel.gsh2.core.IBinaryContext;
import fr.lirmm.marel.gsh2.core.IMySet;
import fr.lirmm.marel.gsh2.core.MyConcept;
import fr.lirmm.marel.gsh2.core.MyConceptSet;
import fr.lirmm.marel.gsh2.core.MyGSH;
import fr.lirmm.marel.gsh2.core.MySetWrapper;
import fr.lirmm.marel.gsh2.util.Chrono;


public class Ceres
extends AbstractAlgorithm {
    private IBinaryContext binCtx = null;
    MyGSH theGSH = null;
    private Chrono chrono = null;
    Random ran = new Random(System.currentTimeMillis());
    int classifyIdentifier;

    public Ceres(IBinaryContext binCtx, Chrono chrono) {
        this.binCtx = binCtx;
        this.chrono = chrono;
    }

    public Ceres(IBinaryContext binCtx) {
        this(binCtx, null);
    }

    @Override
    public void exec() {
        if (this.binCtx.getAttributeNumber() == 0 || this.binCtx.getObjectNumber() == 0) {
            return;
        }
        this.theGSH = new MyGSH(this.binCtx.getObjectNumber(), MyGSH.EXTENT_LEVEL_INDEX);
        if (this.chrono != null) {
            this.chrono.start("concept/order");
        }
        MySetWrapper ext = new MySetWrapper(this.binCtx.getObjectCount());
        ext.fill(this.binCtx.getObjectCount());
        MySetWrapper reducedExtent = new MySetWrapper(0);
        int i = 0;
        while (i < this.binCtx.getObjectCount()) {
            if (this.binCtx.getIntent(i).cardinality() == 0) {
                reducedExtent.add(i);
            }
            ++i;
        }
        MyConcept topInit = new MyConcept(ext, new MySetWrapper(0));
        if (reducedExtent.cardinality() > 0) {
            topInit.getReducedExtent().addAll(reducedExtent);
        }
        this.theGSH.addConcept(topInit);
        MyConceptSet aCset = new MyConceptSet();
        MyConcept aCpt = null;
        int i2 = 0;
        while (i2 < this.binCtx.getAttributeNumber()) {
            MySetWrapper preCptInt = new MySetWrapper(0);
            preCptInt.add(i2);
            IMySet preCptExt = this.binCtx.getExtent(i2);
            aCpt = new MyConcept(preCptExt, preCptInt);
            aCpt.getReducedIntent().add(i2);
            aCset.add(aCpt);
            ++i2;
        }
        MyConcept[] preCptTab = aCset.getDecreasingExtentSorted();
        boolean[] preCptDone = new boolean[preCptTab.length];
        int i3 = 0;
        while (i3 < preCptDone.length) {
            preCptDone[i3] = false;
            ++i3;
        }
        int startIndex = 0;
        int endIndex = 1;
        MySetWrapper allCoveredIntent = new MySetWrapper(0);
        while (startIndex < preCptTab.length) {
            int sizeToDo = preCptTab[startIndex].getExtent().cardinality();
            while (endIndex < preCptTab.length && preCptTab[endIndex].getExtent().cardinality() == sizeToDo) {
                int i4 = startIndex;
                while (i4 < endIndex) {
                    if (!preCptDone[i4] && preCptTab[i4].getExtent().equals(preCptTab[endIndex].getExtent())) {
                        preCptTab[i4].getIntent().addAll(preCptTab[endIndex].getIntent());
                        preCptTab[i4].getReducedIntent().addAll(preCptTab[endIndex].getIntent());
                        preCptDone[endIndex] = true;
                    }
                    ++i4;
                }
                ++endIndex;
            }
            boolean doWOLP = false;
            int i5 = startIndex;
            while (i5 < endIndex) {
                if (!preCptDone[i5]) {
                    if (sizeToDo < this.binCtx.getObjectNumber()) {
                        this.Classify(preCptTab[i5], allCoveredIntent, true);
                        doWOLP = true;
                    } else {
                        this.theGSH.getTopTentative().getIntent().addAll(preCptTab[i5].getIntent());
                        this.theGSH.getTopTentative().getReducedIntent().addAll(preCptTab[i5].getReducedIntent());
                        preCptTab[i5] = this.theGSH.getTopTentative();
                        doWOLP = false;
                    }
                    allCoveredIntent.addAll(preCptTab[i5].getReducedIntent());
                    Iterator<Integer> it = preCptTab[i5].getExtent().iterator();
                    while (it.hasNext()) {
                        int o = it.next();
                        if (!this.binCtx.getIntent(o).equals(preCptTab[i5].getIntent())) continue;
                        preCptTab[i5].getReducedExtent().add(o);
                    }
                    if (doWOLP) {
                        this.WorkOnLeftPart(preCptTab[i5], allCoveredIntent);
                    }
                    preCptDone[i5] = true;
                }
                ++i5;
            }
            startIndex = endIndex++;
        }
        MyConcept top = this.theGSH.getTopTentative();
        if (top.getReducedExtent().cardinality() == 0 && top.getReducedIntent().cardinality() == 0) {
            this.theGSH.removeConcept(top);
        }
        if (this.chrono != null) {
            this.chrono.stop("concept/order");
        }
        this.theGSH.isReduicedlyLabelled(true);
        double complexity = this.binCtx.getDataComplexity();
        this.theGSH.setNameId("Ceres");
        this.result = this.theGSH;
    }

    private void Classify(MyConcept cptToAdd, IMySet allCoveredIntent, boolean isAttributeCpt) {
        Iterator<MyConcept> it;
        this.classifyIdentifier = this.ran.nextInt();
        LinkedList<MyConcept> fifoQueue = new LinkedList<MyConcept>();
        MyConceptSet potentialUpperCover = new MyConceptSet();
        fifoQueue.add(this.theGSH.getTopTentative());
        MyConcept nextCpt = null;
        while (fifoQueue.size() != 0) {
            nextCpt = (MyConcept)fifoQueue.remove(0);
            potentialUpperCover.add(nextCpt);
            potentialUpperCover.removeAll(nextCpt.getUpperCover());
            if (isAttributeCpt) {
                cptToAdd.getIntent().addAll(nextCpt.getReducedIntent());
            }
            it = nextCpt.getLowerCover().iterator();
            while (it.hasNext()) {
                MyConcept P = it.next();
                this.changeMarkValue(P);
                if (!this.isReady(P) || !P.getExtent().containsAll(cptToAdd.getExtent())) continue;
                fifoQueue.add(P);
            }
        }
        this.theGSH.addConcept(cptToAdd);
        it = potentialUpperCover.iterator();
        while (it.hasNext()) {
            this.theGSH.addPrecedenceConnection(cptToAdd, it.next());
        }
    }

    private void WorkOnLeftPart(MyConcept addedCpt, IMySet allCoveredIntent) {
        MySetWrapper setOfPotentialObjectCptGenerator = new MySetWrapper(0);
        Iterator<Integer> it = addedCpt.getExtent().iterator();
        while (it.hasNext()) {
            int anObject = it.next();
            if (addedCpt.getReducedExtent().contains(anObject)) continue;
            setOfPotentialObjectCptGenerator.add(anObject);
        }
        ArrayList<Integer> lesObjsTrie = new ArrayList<Integer>();
        Iterator<Integer> it2 = setOfPotentialObjectCptGenerator.iterator();
        while (it2.hasNext()) {
            int e = it2.next();
            int tailleFe = this.binCtx.getIntent(e).cardinality();
            boolean trouve = false;
            int i = 0;
            while (i < lesObjsTrie.size() && !trouve) {
                int o = (Integer)lesObjsTrie.get(i);
                int tailleFo = this.binCtx.getIntent(o).cardinality();
                if (tailleFe <= tailleFo) {
                    lesObjsTrie.add(i, e);
                    trouve = true;
                }
                ++i;
            }
            if (trouve) continue;
            lesObjsTrie.add(e);
        }
        int i = 0;
        while (i < lesObjsTrie.size()) {
            int potentialObjectCptGenerator = (Integer)lesObjsTrie.get(i);
            IMySet theAssocitedIntent = this.binCtx.getIntent(potentialObjectCptGenerator);
            if (allCoveredIntent.containsAll(theAssocitedIntent)) {
                MySetWrapper LP = new MySetWrapper(0);
                LP.add(potentialObjectCptGenerator);
                MyConcept theNexCpt = new MyConcept(LP, theAssocitedIntent);
                theNexCpt.getReducedExtent().add(potentialObjectCptGenerator);
                int j = i + 1;
                while (j < lesObjsTrie.size()) {
                    int complementaryObjectGenerator = (Integer)lesObjsTrie.get(j);
                    if (this.binCtx.getIntent(complementaryObjectGenerator).cardinality() == theAssocitedIntent.cardinality()) {
                        if (this.binCtx.getIntent(complementaryObjectGenerator).equals(theAssocitedIntent)) {
                            theNexCpt.getExtent().add(complementaryObjectGenerator);
                            theNexCpt.getReducedExtent().add(complementaryObjectGenerator);
                            lesObjsTrie.remove((Object)complementaryObjectGenerator);
                            --j;
                        }
                    } else if (this.binCtx.getIntent(complementaryObjectGenerator).containsAll(theAssocitedIntent)) {
                        theNexCpt.getExtent().add(complementaryObjectGenerator);
                    }
                    ++j;
                }
                this.Classify(theNexCpt, allCoveredIntent, false);
            }
            ++i;
        }
    }

    private void initMark(MyConcept aCpt) {
        if (aCpt.getStamp() == null) {
            aCpt.setStamp(new int[2]);
        }
        ((int[])aCpt.getStamp())[0] = this.classifyIdentifier;
        ((int[])aCpt.getStamp())[1] = aCpt.getUpperCover().size();
    }

    private boolean isReady(MyConcept aCpt) {
        if (aCpt.getStamp() == null || ((int[])aCpt.getStamp())[0] != this.classifyIdentifier) {
            this.initMark(aCpt);
        }
        if (((int[])aCpt.getStamp())[1] == 0) {
            return true;
        }
        return false;
    }

    private void changeMarkValue(MyConcept aCpt) {
        if (aCpt.getStamp() == null || ((int[])aCpt.getStamp())[0] != this.classifyIdentifier) {
            this.initMark(aCpt);
        }
        ((int[])aCpt.getStamp())[1] = ((int[])aCpt.getStamp())[1] - 1;
    }

    @Override
    public String getDescription() {
        return "Ceres";
    }
}

