package core;

import core.IMySet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public abstract class MyHashSet
implements IMySet {
    private final HashSet<Integer> hashSet;

    public MyHashSet(int initialCapacity) {
        this.hashSet = new HashSet(initialCapacity);
    }

    public MyHashSet(HashSet<Integer> h) {
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
            MyHashSet anotherMyHashSet = (MyHashSet)anotherSet.getImplementation();
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
            MyHashSet anotherMyHashSet = (MyHashSet)anotherSet.getImplementation();
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
            MyHashSet anotherMyHashSet = (MyHashSet)anotherSet.getImplementation();
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
        return this.hashSet.iterator();
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
        return IMySet.Impl.HASHSET;
    }

    @Override
    public boolean equals(Object aSet) {
        if (aSet instanceof IMySet) {
            if (((IMySet)aSet).getImplementationType() == this.getImplementationType()) {
                MyHashSet aMyHashSet = (MyHashSet)((IMySet)aSet).getImplementation();
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

    public HashSet<Integer> newIntersectHashSet(IMySet anotherSet) {
        HashSet<Integer> newIntersect = new HashSet<Integer>();
        for (Integer num : this.hashSet) {
            if (!anotherSet.contains(num)) continue;
            newIntersect.add(num);
        }
        return newIntersect;
    }

    public HashSet<Integer> newDifferenceHashSet(IMySet anotherSet) {
        HashSet<Integer> newDiff = new HashSet<Integer>(this.hashSet);
        for (Integer num : this.hashSet) {
            if (!anotherSet.contains(num)) continue;
            newDiff.remove(num);
        }
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

    public HashSet<Integer> cloneHashSet() {
        return new HashSet<Integer>(this.hashSet);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}

