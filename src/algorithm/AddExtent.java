package algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import core.IMySet;
import core.MyBinaryContext;
import core.MyConcept;
import core.MyGSH;
import core.MySetWrapper;
import util.Chrono;


public class AddExtent
extends AbstractAlgorithm {
    private MyBinaryContext matrix;
    private MyGSH lattice;
    private Chrono chrono = null;

    public AddExtent(MyBinaryContext matrix, Chrono chrono) {
        this.matrix = matrix;
        this.chrono = chrono;
    }

    public AddExtent(MyBinaryContext matrix) {
        this(matrix, null);
    }

    protected Set<MyConcept> getAllChildren(MyConcept c) {
        Iterator<MyConcept> it = c.getLowerCover().iterator();
        HashSet<MyConcept> set = new HashSet<MyConcept>();
        while (it.hasNext()) {
            MyConcept child = it.next();
            set.add(child);
            set.addAll(this.getAllChildren(child));
        }
        return set;
    }

    @Override
    public void exec() {
        try {
            this.lattice = new MyGSH(this.matrix.getObjectNumber(), MyGSH.EXTENT_LEVEL_INDEX);
            if (this.chrono != null) {
                this.chrono.start("concept/order");
            }
            MySetWrapper allObjects = new MySetWrapper(this.matrix.getObjectCount());
            allObjects.fill(this.matrix.getObjectCount());
            MyConcept top = new MyConcept(allObjects, new MySetWrapper(0));
            this.lattice.addConcept(top);
            int numAttr = 0;
            while (numAttr < this.matrix.getAttributeCount()) {
                MyConcept concept = this.addExtent(this.matrix.getExtent(numAttr), top);
                concept.getIntent().add(numAttr);
                for (MyConcept child : this.getAllChildren(concept)) {
                    child.getIntent().add(numAttr);
                }
                ++numAttr;
            }
            this.result = this.lattice;
            if (this.chrono != null) {
                this.chrono.stop("concept/order");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected MyConcept addExtent(IMySet extent, MyConcept generator) throws Exception {
        if (extent.equals((generator = this.getMaximalConcept(extent, generator)).getExtent())) {
            return generator;
        }
        ArrayList<MyConcept> newChildren = new ArrayList<MyConcept>();
        Iterator<MyConcept> it = generator.getLowerCover().iterator();
        while (it.hasNext()) {
            MyConcept candidate = it.next();
            if (!candidate.getExtent().containsAll(extent)) {
                IMySet intersection = extent.newIntersect(candidate.getExtent());
                candidate = this.addExtent(intersection, candidate);
            }
            boolean addChild = true;
            ArrayList conceptsToDelete = new ArrayList();
            for (MyConcept child : newChildren) {
                if (child.getExtent().containsAll(candidate.getExtent())) {
                    addChild = false;
                    break;
                }
                if (!candidate.getExtent().containsAll(child.getExtent())) continue;
                conceptsToDelete.add(child);
            }
            Iterator iterator = conceptsToDelete.iterator();
            while (iterator.hasNext()) {
                MyConcept concept = (MyConcept)iterator.next();
                newChildren.remove(concept);
            }
            if (!addChild) continue;
            newChildren.add(candidate);
        }
        MyConcept newConcept = new MyConcept((IMySet)extent.clone(), (IMySet)generator.getIntent().clone());
        this.lattice.addConcept(newConcept);
        for (MyConcept child : newChildren) {
            this.lattice.removePrecedenceConnection(child, generator);
            this.lattice.addPrecedenceConnection(child, newConcept);
        }
        this.lattice.addPrecedenceConnection(newConcept, generator);
        return newConcept;
    }

    protected MyConcept getMaximalConcept(IMySet extent, MyConcept generator) {
        boolean isMaximal = true;
        block0 : while (isMaximal) {
            isMaximal = false;
            Iterator<MyConcept> it = generator.getLowerCover().iterator();
            while (it.hasNext()) {
                MyConcept child = it.next();
                if (!child.getExtent().containsAll(extent)) continue;
                generator = child;
                isMaximal = true;
                continue block0;
            }
        }
        return generator;
    }

    @Override
    public String getDescription() {
        return "AddExtent";
    }
}

