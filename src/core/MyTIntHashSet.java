package core;

import core.IMySet;
import gnu.trove.TIntCollection;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import java.util.Iterator;

public abstract class MyTIntHashSet
implements IMySet {
    private final TIntSet hashSet;

    public MyTIntHashSet(int initialCapacity) {
        this.hashSet = new TIntHashSet(initialCapacity);
    }

    public MyTIntHashSet(TIntHashSet h) {
        this.hashSet = h;
    }

    @Override
    public void fill(int size) {
        int i = 0;
        while (i < size) {
            this.hashSet.add(i);
            ++i;
        }
    }

    @Override
    public void clear(int size) {
        int i = 0;
        while (i < size) {
            this.hashSet.remove(i);
            ++i;
        }
    }

    @Override
    public void clearAll(IMySet anotherSet) {
        if (anotherSet.getImplementationType() == this.getImplementationType()) {
            MyTIntHashSet anotherMyHashSet = (MyTIntHashSet)anotherSet.getImplementation();
            this.hashSet.removeAll(anotherMyHashSet.hashSet);
        } else {
            Iterator<Integer> it = anotherSet.iterator();
            while (it.hasNext()) {
                this.hashSet.remove(it.next());
            }
        }
    }

    @Override
    public void add(int num) {
        this.hashSet.add(num);
    }

    @Override
    public boolean contains(int num) {
        return this.hashSet.contains(num);
    }

    @Override
    public int cardinality() {
        return this.hashSet.size();
    }

    @Override
    public boolean containsAll(IMySet anotherSet) {
        if (anotherSet.getImplementationType() == this.getImplementationType()) {
            MyTIntHashSet anotherMyHashSet = (MyTIntHashSet)anotherSet.getImplementation();
            return this.hashSet.containsAll(anotherMyHashSet.hashSet);
        }
        Iterator<Integer> it = anotherSet.iterator();
        while (it.hasNext()) {
            if (this.hashSet.contains(it.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public void addAll(IMySet anotherSet) {
        if (anotherSet.getImplementationType() == this.getImplementationType()) {
            MyTIntHashSet anotherMyHashSet = (MyTIntHashSet)anotherSet.getImplementation();
            this.hashSet.addAll(anotherMyHashSet.hashSet);
        } else {
            Iterator<Integer> it = anotherSet.iterator();
            while (it.hasNext()) {
                this.hashSet.add(it.next());
            }
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>(){
            TIntIterator it;

            @Override
            public boolean hasNext() {
                return this.it.hasNext();
            }

            @Override
            public Integer next() {
                return this.it.next();
            }

            @Override
            public void remove() {
                this.it.remove();
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return this.hashSet.isEmpty();
    }

    public boolean isStrictlyInclude(IMySet anotherSet) {
        if (anotherSet.cardinality() < this.cardinality() && this.containsAll(anotherSet)) {
            return true;
        }
        return false;
    }

    @Override
    public int capacity() {
        return this.hashSet.size();
    }

    @Override
    public IMySet.Impl getImplementationType() {
        return IMySet.Impl.TROVE_HASHSET;
    }

    @Override
    public boolean equals(Object aSet) {
        if (aSet instanceof IMySet) {
            if (((IMySet)aSet).getImplementationType() == this.getImplementationType()) {
                MyTIntHashSet aMyHashSet = (MyTIntHashSet)((IMySet)aSet).getImplementation();
                return this.hashSet.equals(aMyHashSet.hashSet);
            }
            if (this.cardinality() == ((IMySet)aSet).cardinality() && this.containsAll((IMySet)aSet)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public IMySet newIntersect(IMySet anotherSet) {
        return null;
    }

    @Override
    public IMySet newDifference(IMySet anotherSet) {
        return null;
    }

    public TIntHashSet newIntersectTIntHashSet(final IMySet anotherSet) {
        final TIntHashSet newIntersect = new TIntHashSet();
        this.hashSet.forEach(new TIntProcedure(){

            @Override
            public boolean execute(int value) {
                if (anotherSet.contains(value)) {
                    newIntersect.add(value);
                }
                return true;
            }
        });
        return newIntersect;
    }

    public TIntHashSet newDifferenceTIntHashSet(final IMySet anotherSet) {
        final TIntHashSet newDiff = new TIntHashSet(this.hashSet);
        this.hashSet.forEach(new TIntProcedure(){

            @Override
            public boolean execute(int value) {
                if (anotherSet.contains(value)) {
                    newDiff.remove(value);
                }
                return true;
            }
        });
        return newDiff;
    }

    @Override
    public IMySet getImplementation() {
        return this;
    }

    @Override
    public void remove(int num) {
        this.hashSet.remove(num);
    }

    public TIntHashSet cloneTIntHashSet() {
        return new TIntHashSet(this.hashSet);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}

