/*
 * Decompiled with CFR 0_114.
 */
package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import algorithm.AbstractAlgorithm;
import core.IMySet;
import core.MyBasicIterator;
import core.MyConcept;
import core.MyConceptSet;
import core.MyGSH;
import core.MySetWrapper;

public class Aores
extends AbstractAlgorithm {
    private MyGSH gsh;
    private int A;
    private IMySet fA;
    private MyConceptSet superConceptsA;
    private IMySet propreA;
    private MyConcept CA;
    private boolean aDefini;
    private MyConceptSet emptyConcepts;
    private ArrayList<MyConcept> linext;

    public Aores(MyGSH gsh, int A, IMySet fA) {
        this.gsh = gsh;
        this.A = A;
        this.fA = fA;
        this.emptyConcepts = new MyConceptSet();
    }

    private void aoresCas1(MyConcept C) {
        this.gsh.addObjectToConcept(C, this.A);
        C.getReducedExtent().add(this.A);
    }

    private void aoresCas2(MyConcept C) {
        this.superConceptsA.add(C);
        this.gsh.addObjectToConcept(C, this.A);
        this.propreA.clearAll(C.getReducedIntent());
    }

    private void aoresCas3(MyConcept C, IMySet EP) throws Exception {
        if (!this.aDefini) {
            MySetWrapper extsCA = new MySetWrapper(0);
            extsCA.add(this.A);
            IMySet extCA = (IMySet)extsCA.clone();
            IMySet intCA = (IMySet)this.fA.clone();
            IMySet intsCA = (IMySet)this.propreA.clone();
            this.CA = new MyConcept(extCA, intCA, extsCA, intsCA);
            this.aDefini = true;
            if (!EP.isEmpty()) {
                IMySet intsC = C.getReducedIntent();
                intsC.clearAll(this.fA);
                if (intsC.isEmpty() && C.getReducedExtent().isEmpty()) {
                    this.emptyConcepts.add(C);
                }
            }
            MyConceptSet supIMCA = this.minSuperConcepts(this.superConceptsA);
            Iterator<MyConcept> i = supIMCA.iterator();
            while (i.hasNext()) {
                MyConcept C1 = i.next();
                this.gsh.addPrecedenceConnection(this.CA, C1);
                this.gsh.removePrecedenceConnection(C, C1);
            }
        }
        this.gsh.addPrecedenceConnection(C, this.CA);
        Iterator i = C.getExtent().iterator();
        while (i.hasNext()) {
            this.gsh.addObjectToConcept(this.CA, (Integer)i.next());
        }
        i = this.CA.getUpperCover().iterator();
        while (i.hasNext()) {
            this.gsh.removePrecedenceConnection(C, (MyConcept)i.next());
        }
        this.destroyDescendanceFromLinext(C);
    }

    private void aoresCas4(MyConcept C, IMySet EP, IMySet CP) throws Exception {
        IMySet intsC1 = EP;
        IMySet extC1 = (IMySet)C.getExtent().clone();
        extC1.add(this.A);
        MyConcept C1 = new MyConcept(extC1, CP, new MySetWrapper(0), intsC1);
        MyConceptSet superConceptsC = this.superConcepts(C);
        MyConceptSet superConceptsCinterA = this.minSuperConcepts(superConceptsC.getNewIntersectionWith(this.superConceptsA));
        Iterator<MyConcept> i = superConceptsCinterA.iterator();
        while (i.hasNext()) {
            MyConcept C2 = i.next();
            this.gsh.addPrecedenceConnection(C1, C2);
            this.gsh.removePrecedenceConnection(C, C2);
        }
        this.gsh.addPrecedenceConnection(C, C1);
        this.superConceptsA.add(C1);
        i = C1.getUpperCover().iterator();
        while (i.hasNext()) {
            this.gsh.removePrecedenceConnection(C, i.next());
        }
        IMySet intsC = C.getReducedIntent();
        intsC.clearAll(intsC1);
        if (intsC.isEmpty() && C.getReducedExtent().isEmpty()) {
            this.emptyConcepts.add(C);
        }
        this.propreA.clearAll(intsC1);
    }

    private MyConceptSet superConcepts(MyConcept concept) {
        MyConceptSet superConcepts = new MyConceptSet();
        superConcepts = this.superConcepts(concept, superConcepts);
        return superConcepts;
    }

    private MyConceptSet superConcepts(MyConcept concept, MyConceptSet superConcepts) {
        Iterator<MyConcept> i = concept.getUpperCover().iterator();
        while (i.hasNext()) {
            MyConcept parent = i.next();
            superConcepts.add(parent);
            superConcepts = this.superConcepts(parent, superConcepts);
        }
        return superConcepts;
    }

    private MyConceptSet minSuperConcepts(MyConceptSet superConcepts) {
        MyConceptSet minConcepts = new MyConceptSet();
        Iterator<MyConcept> i = superConcepts.iterator();
        while (i.hasNext()) {
            MyConcept concept = i.next();
            boolean allChildrenOutside = true;
            Iterator<MyConcept> j = concept.getLowerCover().iterator();
            while (allChildrenOutside && j.hasNext()) {
                MyConcept child = j.next();
                if (!superConcepts.contains(child)) continue;
                allChildrenOutside = false;
            }
            if (!allChildrenOutside) continue;
            minConcepts.add(concept);
        }
        return minConcepts;
    }

    private void destroyDescendanceFromLinext(MyConcept C) {
        Iterator<MyConcept> i = C.getLowerCover().iterator();
        while (i.hasNext()) {
            MyConcept child = i.next();
            this.linext.remove(child);
            this.destroyDescendanceFromLinext(child);
        }
    }

    private boolean isThereAPath(MyConcept parent, MyConcept descendant) {
        boolean link_found = false;
        Iterator<MyConcept> i = parent.getLowerCover().iterator();
        while (!link_found && i.hasNext()) {
            MyConcept child = i.next();
            boolean bl = link_found = child == descendant || this.isThereAPath(child, descendant);
        }
        return link_found;
    }

    private void destroyEmptyConcepts() {
        Iterator<MyConcept> i = this.emptyConcepts.iterator();
        while (i.hasNext()) {
            MyConcept emptyConcept = i.next();
            ArrayList<MyConcept> parents = new ArrayList<MyConcept>();
            parents.addAll(emptyConcept.getUpperCover().values());
            ArrayList<MyConcept> children = new ArrayList<MyConcept>();
            children.addAll(emptyConcept.getLowerCover().values());
            this.gsh.removeConcept(emptyConcept);
            int j = 0;
            while (j < parents.size()) {
                MyConcept parent = (MyConcept)parents.get(j);
                int k = 0;
                while (k < children.size()) {
                    MyConcept child = (MyConcept)children.get(k);
                    if (!this.isThereAPath(parent, child)) {
                        this.gsh.addPrecedenceConnection(child, parent);
                    }
                    ++k;
                }
                ++j;
            }
        }
    }

    private void aoresLimitCase() throws Exception {
        MyConcept top = this.gsh.getTopTentative();
        if (top != null && top.getIntent().isEmpty()) {
            top.getReducedExtent().add(this.A);
            this.gsh.addObjectToConcept(top, this.A);
        } else {
            MySetWrapper ext = new MySetWrapper(0);
            ext.add(this.A);
            IMySet exts = (IMySet)ext.clone();
            top = new MyConcept(ext, new MySetWrapper(0), exts, new MySetWrapper(0));
            MyConceptSet rootConcepts = new MyConceptSet();
            Iterator<MyConcept> i = this.gsh.getBasicIterator();
            while (i.hasNext()) {
                MyConcept concept = i.next();
                if (!concept.getUpperCover().isEmpty()) continue;
                rootConcepts.add(concept);
            }
            i = rootConcepts.iterator();
            while (i.hasNext()) {
                top.getExtent().addAll(((MyConcept)i.next()).getExtent());
            }
            this.gsh.addConcept(top);
            i = rootConcepts.iterator();
            while (i.hasNext()) {
                this.gsh.addPrecedenceConnection(i.next(), top);
            }
        }
    }

    public MyGSH updateGSH() throws Exception {
        if (this.fA.isEmpty()) {
            this.aoresLimitCase();
            return this.gsh;
        }
        this.superConceptsA = new MyConceptSet();
        this.propreA = (IMySet)this.fA.clone();
        this.aDefini = false;
        this.linext = this.computeLinext();
        int i = 0;
        while (i < this.linext.size()) {
            MyConcept C = this.linext.get(i);
            IMySet CP = C.getIntent().newIntersect(this.fA);
            if (!CP.isEmpty() || C.getIntent().cardinality() <= 0) {
                IMySet EP = C.getReducedIntent().newIntersect(this.fA);
                IMySet RC = C.getIntent().newDifference(CP);
                IMySet RA = this.fA.newDifference(CP);
                boolean RA_empty = RA.isEmpty();
                boolean RC_empty = RC.isEmpty();
                if (RA_empty && RC_empty) {
                    this.aoresCas1(C);
                    return this.gsh;
                }
                if (!RA_empty && RC_empty) {
                    this.aoresCas2(C);
                } else if (RA_empty && !RC_empty) {
                    this.aoresCas3(C, EP);
                } else if (!(RA_empty || RC_empty || EP.isEmpty())) {
                    this.aoresCas4(C, EP, CP);
                }
            }
            ++i;
        }
        if (!this.aDefini) {
            MySetWrapper extsCA = new MySetWrapper(0);
            extsCA.add(this.A);
            IMySet extCA = (IMySet)extsCA.clone();
            IMySet intCA = (IMySet)this.fA.clone();
            IMySet intsCA = (IMySet)this.propreA.clone();
            this.CA = new MyConcept(extCA, intCA, extsCA, intsCA);
            this.gsh.addConcept(this.CA);
            MyConceptSet supIMCA = this.minSuperConcepts(this.superConceptsA);
            Iterator<MyConcept> i2 = supIMCA.iterator();
            while (i2.hasNext()) {
                this.gsh.addPrecedenceConnection(this.CA, i2.next());
            }
        }
        this.destroyEmptyConcepts();
        return this.gsh;
    }

    @Override
    public void exec() {
        try {
            this.updateGSH();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.result = this.gsh;
    }

    public ArrayList<MyConcept> computeLinext() {
        ArrayList<MyConcept> linext = new ArrayList<MyConcept>();
        LinkedList<MyConcept> fifo = new LinkedList<MyConcept>();
        MyBasicIterator i = this.gsh.getBasicIterator();
        while (i.hasNext()) {
            MyConcept c = i.next();
            int degree = c.getUpperCover().size();
            ConceptInfos c_infos = new ConceptInfos(this, degree);
            c.setStamp(c_infos);
            if (degree != 0) continue;
            fifo.add(c);
        }
        while (!fifo.isEmpty()) {
            MyConcept c = (MyConcept)fifo.poll();
            linext.add(c);
            Iterator<MyConcept> i2 = c.getLowerCover().iterator();
            while (i2.hasNext()) {
                MyConcept child = i2.next();
                ConceptInfos child_infos = (ConceptInfos)child.getStamp();
                child_infos.decDegree();
                if (child_infos.getDegree() != 0) continue;
                fifo.add(child);
            }
        }
        return linext;
    }

    @Override
    public String getDescription() {
        return "Aores";
    }

    public static String conceptsToString(MyConceptSet cs) {
        String concepts = "";
        Iterator<MyConcept> i = cs.iterator();
        while (i.hasNext()) {
            MyConcept c = i.next();
            concepts = String.valueOf(concepts) + Aores.conceptToString(c) + " ";
        }
        return concepts;
    }

    public static String conceptToString(MyConcept C) {
        return "[{" + C.getExtent() + "," + C.getIntent() + "} {" + C.getReducedExtent() + "," + C.getReducedIntent() + "}]";
    }

    class ConceptInfos {
        private int degree;
        final /* synthetic */ Aores this$0;

        public ConceptInfos(Aores aores) {
            this.this$0 = aores;
            this.degree = 0;
        }

        public ConceptInfos(Aores aores, int degree) {
            this.this$0 = aores;
            this.degree = degree;
        }

        public int getDegree() {
            return this.degree;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }

        public void decDegree() {
            --this.degree;
        }
    }

}

