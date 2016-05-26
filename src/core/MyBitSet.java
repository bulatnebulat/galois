/*
 * Decompiled with CFR 0_114.
 */
package core;

import core.IMySet;
import java.util.BitSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class MyBitSet
implements IMySet {
    private final BitSet bitSet;

    public MyBitSet(int initialCapacity) {
        this.bitSet = new BitSet(initialCapacity);
    }

    public MyBitSet(BitSet bitSet) {
        this.bitSet = bitSet;
    }

    @Override
    public void fill(int size) {
        this.bitSet.set(0, size);
    }

    @Override
    public void clear(int size) {
        this.bitSet.clear(0, size);
    }

    @Override
    public void clearAll(IMySet anotherSet) {
        if (anotherSet.getImplementationType() == this.getImplementationType()) {
            MyBitSet anotherMyBitSet = (MyBitSet)anotherSet.getImplementation();
            this.bitSet.andNot(anotherMyBitSet.bitSet);
        } else {
            Iterator<Integer> it = anotherSet.iterator();
            while (it.hasNext()) {
                this.bitSet.set((int)it.next(), false);
            }
        }
    }

    @Override
    public void add(int num) {
        this.bitSet.set(num);
    }

    @Override
    public boolean contains(int num) {
        return this.bitSet.get(num);
    }

    @Override
    public int cardinality() {
        return this.bitSet.cardinality();
    }

    @Override
    public boolean containsAll(IMySet anotherSet) {
        Iterator<Integer> it = anotherSet.iterator();
        while (it.hasNext()) {
            if (this.bitSet.get(it.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public void addAll(IMySet anotherSet) {
        if (anotherSet.getImplementationType() == this.getImplementationType()) {
            MyBitSet anotherMyBitSet = (MyBitSet)anotherSet.getImplementation();
            this.bitSet.or(anotherMyBitSet.bitSet);
        } else {
            Iterator<Integer> it = anotherSet.iterator();
            while (it.hasNext()) {
                this.bitSet.set(it.next());
            }
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>(){
            int index;

            @Override
            public boolean hasNext() {
                if (this.index == -1) {
                    return false;
                }
                if (this.index == -2) {
                    if (MyBitSet.this.bitSet.nextSetBit(0) >= 0) {
                        return true;
                    }
                    return false;
                }
                if (MyBitSet.this.bitSet.nextSetBit(this.index + 1) >= 0) {
                    return true;
                }
                return false;
            }

            @Override
            public Integer next() {
                if (this.index == -1) {
                    throw new NoSuchElementException();
                }
                this.index = this.index == -2 ? MyBitSet.this.bitSet.nextSetBit(0) : MyBitSet.this.bitSet.nextSetBit(this.index + 1);
                if (this.index == -1) {
                    throw new NoSuchElementException();
                }
                return this.index;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return this.bitSet.isEmpty();
    }

    @Override
    public boolean equals(Object aSet) {
        if (aSet instanceof IMySet) {
            if (((IMySet)aSet).getImplementationType() == this.getImplementationType()) {
                MyBitSet aMyBitSet = (MyBitSet)((IMySet)aSet).getImplementation();
                return this.bitSet.equals(aMyBitSet.bitSet);
            }
            if (this.cardinality() == ((IMySet)aSet).cardinality() && this.containsAll((IMySet)aSet)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        String s = null;
        Iterator<Integer> it = this.iterator();
        while (it.hasNext()) {
            s = s == null ? "[" + it.next() : String.valueOf(s) + "," + it.next();
        }
        s = String.valueOf(s) + "]";
        return s;
    }

    @Override
    public int capacity() {
        return this.bitSet.size();
    }

    @Override
    public IMySet.Impl getImplementationType() {
        return IMySet.Impl.BITSET;
    }

    public BitSet cloneBitSet() {
        return (BitSet)this.bitSet.clone();
    }

    public BitSet newIntersectBitSet(IMySet anotherSet) {
        if (anotherSet.getImplementationType() == this.getImplementationType()) {
            BitSet intersectBS = (BitSet)this.bitSet.clone();
            MyBitSet anotherMyBitSet = (MyBitSet)anotherSet.getImplementation();
            intersectBS.and(anotherMyBitSet.bitSet);
            return intersectBS;
        }
        BitSet newBitSet = new BitSet();
        Iterator<Integer> it = anotherSet.iterator();
        while (it.hasNext()) {
            int num = it.next();
            if (!this.bitSet.get(num)) continue;
            newBitSet.set(num);
        }
        return newBitSet;
    }

    @Override
    public IMySet newIntersect(IMySet anotherSet) {
        return null;
    }

    @Override
    public IMySet newDifference(IMySet anotherSet) {
        return null;
    }

    public BitSet newDifferenceBitSet(IMySet anotherSet) {
        if (anotherSet.getImplementationType() == this.getImplementationType()) {
            BitSet diffBS = (BitSet)this.bitSet.clone();
            MyBitSet anotherMyBitSet = (MyBitSet)anotherSet.getImplementation();
            diffBS.andNot(anotherMyBitSet.bitSet);
            return diffBS;
        }
        BitSet newBitSet = (BitSet)this.bitSet.clone();
        Iterator<Integer> it = anotherSet.iterator();
        while (it.hasNext()) {
            newBitSet.set((int)it.next(), false);
        }
        return newBitSet;
    }

    @Override
    public IMySet getImplementation() {
        return this;
    }

    @Override
    public void remove(int num) {
        this.bitSet.set(num, false);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}

