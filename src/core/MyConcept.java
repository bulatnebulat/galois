package core;

import core.IMySet;
import core.MyConceptSet;
import core.MyGSH;
import core.MySetWrapper;

public class MyConcept
implements Cloneable {
    private MyGSH conceptualOwner = null;
    protected MyConceptSet upperCover = new MyConceptSet();
    protected MyConceptSet lowerCover = new MyConceptSet();
    protected IMySet extent;
    protected IMySet reducedExtent;
    protected IMySet intent;
    protected IMySet reducedIntent;
    private Object stamp;
    private int rankNumber = 0;
    private static long nextId = 0;
    private long id = nextId++;

    public MyConcept(IMySet extent, IMySet intent) {
        this.extent = extent;
        this.reducedExtent = new MySetWrapper(0);
        this.intent = intent;
        this.reducedIntent = new MySetWrapper(0);
    }

    public MyConcept(IMySet extent, IMySet intent, IMySet reducedExtent, IMySet reducedIntent) {
        this.extent = extent;
        this.reducedExtent = reducedExtent;
        this.intent = intent;
        this.reducedIntent = reducedIntent;
    }

    public int getId() {
        return (int)this.id;
    }

    public IMySet getIntent() {
        return this.intent;
    }

    public IMySet getExtent() {
        return this.extent;
    }

    public IMySet getReducedExtent() {
        return this.reducedExtent;
    }

    public IMySet getReducedIntent() {
        return this.reducedIntent;
    }

    public Object getStamp() {
        return this.stamp;
    }

    public void setStamp(Object stamp) {
        this.stamp = stamp;
    }

    public MyConceptSet getUpperCover() {
        return this.upperCover;
    }

    public MyConceptSet getLowerCover() {
        return this.lowerCover;
    }

    public Object clone() throws CloneNotSupportedException {
        return new MyConcept((IMySet)this.extent.clone(), (IMySet)this.intent.clone(), (IMySet)this.reducedExtent.clone(), (IMySet)this.reducedIntent.clone());
    }

    public MyGSH getConceptualOwner() {
        return this.conceptualOwner;
    }

    public void setConceptualOwner(MyGSH conceptualOwner) {
        this.conceptualOwner = conceptualOwner;
    }

    public int getRankNumber() {
        return this.rankNumber;
    }

    public void setRankNumber(int rankNumber) {
        this.rankNumber = rankNumber;
    }

    public String toString() {
        if (this.conceptualOwner == null) {
            return "<NoOwner, Concept id=" + this.id + ">";
        }
        return "<" + this.id + ">";
    }

    public boolean isFusion() {
        if (this.getReducedExtent().cardinality() > 1) {
            return true;
        }
        return false;
    }

    public boolean isNewConcept() {
        if (this.getReducedExtent().cardinality() == 0) {
            return true;
        }
        return false;
    }

    public boolean isDummy() {
        if (this.getExtent().cardinality() == 0 || this.getIntent().cardinality() == 0) {
            return true;
        }
        return false;
    }
}

