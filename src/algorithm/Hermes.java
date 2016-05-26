/*
 * Decompiled with CFR 0_114.
 */
package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import fr.lirmm.marel.gsh2.algorithm.AbstractAlgorithm;
import fr.lirmm.marel.gsh2.core.IBinaryContext;
import fr.lirmm.marel.gsh2.core.IMySet;
import fr.lirmm.marel.gsh2.core.MyConcept;
import fr.lirmm.marel.gsh2.core.MyGSH;
import fr.lirmm.marel.gsh2.core.MySetWrapper;
import fr.lirmm.marel.gsh2.util.Chrono;

public class Hermes
extends AbstractAlgorithm {
    private IBinaryContext matrix;
    private MyGSH gsh = null;
    private Chrono chrono = null;

    public Hermes(IBinaryContext bc, Chrono chrono) {
        this.chrono = chrono;
        this.matrix = bc;
    }

    public Hermes(IBinaryContext bc) {
        this(bc, null);
    }

    protected ArrayList<RefSet> clarify(ArrayList<RefSet> setToClarify, ArrayList<RefSet> setToSynchronize) {
        Comparator<RefSet> comparator = new Comparator<RefSet>(){

            @Override
            public int compare(RefSet o1, RefSet o2) {
                int card2;
                int card1 = o1.values.cardinality();
                if (card1 < (card2 = o2.values.cardinality())) {
                    return 1;
                }
                if (card1 == card2) {
                    return 0;
                }
                return -1;
            }
        };
        Collections.sort(setToClarify, comparator);
        int i = setToClarify.size() - 1;
        while (i > 0) {
            RefSet setToCompare = setToClarify.get(i);
            int j = i - 1;
            while (j >= 0) {
                RefSet iSet = setToClarify.get(j);
                int comparison = comparator.compare(setToCompare, iSet);
                if (comparison != 0) break;
                if (setToCompare.values.equals(iSet.values)) {
                    iSet.addRef(setToCompare.refs);
                    setToClarify.remove(i);
                    break;
                }
                --j;
            }
            --i;
        }
        ArrayList<RefSet> attrSets = new ArrayList<RefSet>(setToSynchronize.size());
        int i2 = 0;
        while (i2 < setToSynchronize.size()) {
            attrSets.add(new RefSet(this, setToSynchronize.get((int)i2).refs));
            ++i2;
        }
        i2 = 0;
        while (i2 < setToClarify.size()) {
            IMySet ms = setToClarify.get((int)i2).values;
            Iterator<Integer> it = ms.iterator();
            while (it.hasNext()) {
                attrSets.get((int)it.next().intValue()).values.add(i2);
            }
            ++i2;
        }
        return attrSets;
    }

    protected ArrayList<RefSet> computeAttributeDomRelation(ArrayList<RefSet> attrSets) {
        ArrayList<RefSet> domRelation = new ArrayList<RefSet>();
        int i = 0;
        while (i < attrSets.size()) {
            RefSet attrSet = attrSets.get(i);
            RefSet newSet = new RefSet(this);
            newSet.addRef(attrSet.refs);
            int j = 0;
            while (j < attrSets.size()) {
                boolean b;
                RefSet attr2Set = attrSets.get(j);
                boolean bl = b = i == j || attrSet.isInclude(attr2Set);
                if (b) {
                    newSet.values.add(j);
                }
                ++j;
            }
            domRelation.add(newSet);
            ++i;
        }
        return domRelation;
    }

    private MyConcept getMyConcept(ConceptSet c) throws Exception {
        IMySet extent = c.extent;
        IMySet intent = c.intent;
        IMySet rextent = (IMySet)c.extent.clone();
        IMySet rintent = (IMySet)c.intent.clone();
        MyConcept concept = new MyConcept(extent, intent, rextent, rintent);
        concept.setStamp(new MyConceptInfos(this));
        return concept;
    }

    protected void computeHasseDiagram(MyGSH gsh, ArrayList<ConceptSet> conceptSets) throws Exception {
        Collections.sort(conceptSets, new Comparator<ConceptSet>(){

            @Override
            public int compare(ConceptSet o1, ConceptSet o2) {
                int card2;
                int card1 = o1.values.cardinality();
                if (card1 < (card2 = o2.values.cardinality())) {
                    return 1;
                }
                if (card1 == card2) {
                    return 0;
                }
                return -1;
            }
        });
        ArrayList<MyConcept> concepts = new ArrayList<MyConcept>();
        ArrayList<ConceptSet> conceptSetArray = new ArrayList<ConceptSet>();
        int i = 0;
        while (i < conceptSets.size()) {
            ConceptSet cSet = conceptSets.get(i);
            MyConcept S = this.getMyConcept(cSet);
            gsh.addConcept(S);
            concepts.add(S);
            conceptSetArray.add(cSet);
            int j = i - 1;
            while (j >= 0) {
                MyConcept T = (MyConcept)concepts.get(j);
                MyConceptInfos infos_T = (MyConceptInfos)T.getStamp();
                if (infos_T.isVisited()) {
                    infos_T.setVisited(false);
                } else if (this.isParentOf(S, cSet, T, (ConceptSet)conceptSetArray.get(j))) {
                    gsh.addPrecedenceConnection(T, S);
                    Iterator<Integer> k = T.getExtent().iterator();
                    while (k.hasNext()) {
                        gsh.addObjectToConcept(S, k.next());
                    }
                    T.getIntent().addAll(S.getReducedIntent());
                    this.completeDescendance(T, S.getReducedIntent());
                }
                --j;
            }
            ++i;
        }
    }

    private boolean isParentOf(MyConcept s, ConceptSet sCS, MyConcept t, ConceptSet tCS) {
        boolean t_hasObjects;
        boolean s_hasObjects = s.getReducedExtent().cardinality() > 0;
        boolean bl = t_hasObjects = t.getReducedExtent().cardinality() > 0;
        if (t_hasObjects && !s_hasObjects) {
            int t_obj = t.getReducedExtent().iterator().next();
            int s_attr = s.getReducedIntent().iterator().next();
            return this.matrix.get(t_obj, s_attr);
        }
        Iterator<Integer> it = sCS.values.iterator();
        while (it.hasNext()) {
            if (tCS.values.contains(it.next())) continue;
            return false;
        }
        return true;
    }

    private void completeDescendance(MyConcept concept, IMySet intent) {
        Iterator<MyConcept> i = concept.getLowerCover().iterator();
        while (i.hasNext()) {
            MyConcept child = i.next();
            MyConceptInfos child_infos = (MyConceptInfos)child.getStamp();
            if (child_infos.isVisited()) continue;
            child_infos.setVisited(true);
            child.getIntent().addAll(intent);
            this.completeDescendance(child, intent);
        }
    }

    public MyGSH computeGSH() throws Exception {
        int numObj;
        int numAttr;
        this.gsh = new MyGSH(this.matrix.getObjectCount(), MyGSH.EXTENT_LEVEL_INDEX);
        ArrayList<RefSet> attrSets = new ArrayList<RefSet>();
        ArrayList<RefSet> objSets = new ArrayList<RefSet>();
        if (this.chrono != null) {
            this.chrono.start("clarify");
        }
        if (this.matrix.getAttributeCount() > this.matrix.getObjectCount()) {
            numAttr = 0;
            while (numAttr < this.matrix.getAttributeCount()) {
                attrSets.add(new RefSet(this, numAttr, this.matrix.getExtent(numAttr)));
                ++numAttr;
            }
            numObj = 0;
            while (numObj < this.matrix.getObjectCount()) {
                objSets.add(new RefSet(this, numObj));
                ++numObj;
            }
            objSets = this.clarify(attrSets, objSets);
            attrSets = this.clarify(objSets, attrSets);
        } else {
            numObj = 0;
            while (numObj < this.matrix.getObjectCount()) {
                objSets.add(new RefSet(this, numObj, this.matrix.getIntent(numObj)));
                ++numObj;
            }
            numAttr = 0;
            while (numAttr < this.matrix.getAttributeCount()) {
                attrSets.add(new RefSet(this, numAttr));
                ++numAttr;
            }
            attrSets = this.clarify(objSets, attrSets);
            objSets = this.clarify(attrSets, objSets);
        }
        if (this.chrono != null) {
            this.chrono.stop("clarify");
            this.chrono.start("concept");
        }
        ArrayList<RefSet> domSets = this.computeAttributeDomRelation(attrSets);
        ArrayList<ConceptSet> concepts = new ArrayList<ConceptSet>();
        for (RefSet objSet : objSets) {
            concepts.add(new ConceptSet(null, objSet.refs, objSet.values));
        }
        for (RefSet domSet : domSets) {
            boolean done = false;
            for (ConceptSet concept : concepts) {
                if (!domSet.values.equals(concept.values)) continue;
                concept.intent.addAll(domSet.refs);
                done = true;
                break;
            }
            if (done) continue;
            concepts.add(new ConceptSet(domSet.refs, null, domSet.values));
        }
        if (this.chrono != null) {
            this.chrono.stop("concept");
            this.chrono.start("order");
        }
        this.computeHasseDiagram(this.gsh, concepts);
        if (this.chrono != null) {
            this.chrono.stop("order");
        }
        this.gsh.isReduicedlyLabelled(true);
        this.gsh.setNameId("Hermes");
        return this.gsh;
    }

    @Override
    public void exec() {
        try {
            this.result = this.computeGSH();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Hermes";
    }

    class ConceptSet {
        IMySet intent;
        IMySet extent;
        IMySet values;

        ConceptSet(IMySet intent, IMySet extent, IMySet values) {
            this.intent = intent == null ? new MySetWrapper(0) : intent;
            this.extent = extent == null ? new MySetWrapper(0) : extent;
            this.values = values;
        }
    }

    class MyConceptInfos {
        private boolean visited;
        final /* synthetic */ Hermes this$0;

        public MyConceptInfos(Hermes hermes) {
            this.this$0 = hermes;
            this.visited = false;
        }

        public MyConceptInfos(Hermes hermes, boolean visited) {
            this.this$0 = hermes;
            this.visited = visited;
        }

        public boolean isVisited() {
            return this.visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }
    }

    class RefSet {
        IMySet refs;
        IMySet values;
        final /* synthetic */ Hermes this$0;

        RefSet(Hermes hermes) {
            this.this$0 = hermes;
            this.refs = new MySetWrapper(0);
            this.values = new MySetWrapper(0);
        }

        public boolean isInclude(RefSet anotherRefSet) {
            return anotherRefSet.values.containsAll(this.values);
        }

        RefSet(Hermes hermes, int[] ref, int[] values) {
            int i;
            this.this$0 = hermes;
            this.refs = new MySetWrapper(ref.length);
            int[] arrn = ref;
            int n = arrn.length;
            int n2 = 0;
            while (n2 < n) {
                int i2 = arrn[n2];
                this.refs.add(i2);
                ++n2;
            }
            int max = 0;
            int[] arrn2 = values;
            int n3 = arrn2.length;
            n = 0;
            while (n < n3) {
                i = arrn2[n];
                if (i > max) {
                    max = i;
                }
                ++n;
            }
            this.values = new MySetWrapper(max + 1);
            arrn2 = values;
            n3 = arrn2.length;
            n = 0;
            while (n < n3) {
                i = arrn2[n];
                this.values.add(i);
                ++n;
            }
        }

        RefSet(Hermes hermes, int ref, IMySet values) {
            this.this$0 = hermes;
            this.refs = new MySetWrapper(1);
            this.refs.add(ref);
            try {
                this.values = (IMySet)values.clone();
            }
            catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        RefSet(Hermes hermes, int ref) {
            this.this$0 = hermes;
            this.refs = new MySetWrapper(1);
            this.refs.add(ref);
            this.values = new MySetWrapper(0);
        }

        RefSet(Hermes hermes, IMySet refs) {
            this.this$0 = hermes;
            this.values = new MySetWrapper(0);
            try {
                this.refs = (IMySet)refs.clone();
            }
            catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        void addRef(int ref) {
            this.refs.add(ref);
        }

        void addRef(IMySet refsToAdd) {
            this.refs.addAll(refsToAdd);
        }
    }

}

