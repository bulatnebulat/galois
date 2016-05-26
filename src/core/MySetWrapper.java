/*
 * Decompiled with CFR 0_114.
 */
package core;

import core.IMySet;
import core.MyBitSet;
import core.MyHashSet;
import core.MyTIntHashSet;
import gnu.trove.set.hash.TIntHashSet;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;

public class MySetWrapper
implements IMySet {
    protected IMySet impl;
    protected static IMySet.Impl defaultImpl = IMySet.Impl.BITSET;
    private static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl;

    public MySetWrapper(int initialCapacity) {
        this(initialCapacity, defaultImpl);
    }

    public MySetWrapper(int initialCapacity, IMySet.Impl implementation_type) {
        this.impl = this.create(initialCapacity, implementation_type);
    }

    public MySetWrapper(BitSet bs, IMySet.Impl implementation_type) {
        this.impl = new MyBitSet(bs){

            @Override
            public void changeImplementation(IMySet.Impl implementation_type) {
                MySetWrapper.this.changeImpl(implementation_type);
            }
        };
        this.changeImplementation(implementation_type);
    }

    public MySetWrapper(HashSet<Integer> hs, IMySet.Impl implementation_type) {
        this.impl = new MyHashSet(hs){

            @Override
            public void changeImplementation(IMySet.Impl implementation_type) {
                MySetWrapper.this.changeImpl(implementation_type);
            }
        };
        this.changeImplementation(implementation_type);
    }

    public MySetWrapper(TIntHashSet hs, IMySet.Impl implementation_type) {
        this.impl = new MyTIntHashSet(hs){

            @Override
            public void changeImplementation(IMySet.Impl implementation_type) {
                MySetWrapper.this.changeImpl(implementation_type);
            }
        };
        this.changeImplementation(implementation_type);
    }

    public static void setDefaultImplementation(IMySet.Impl impl) {
        defaultImpl = impl;
    }

    public static IMySet.Impl getDefaultImplementation() {
        return defaultImpl;
    }

    protected IMySet create(int initialCapacity, IMySet.Impl implementation_type) {
        switch (MySetWrapper.$SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl()[implementation_type.ordinal()]) {
            case 1: {
                return new MyBitSet(initialCapacity){

                    @Override
                    public void changeImplementation(IMySet.Impl implementation_type) {
                        MySetWrapper.this.changeImpl(implementation_type);
                    }
                };
            }
            case 3: {
                return new MyTIntHashSet(0){

                    @Override
                    public void changeImplementation(IMySet.Impl implementation_type) {
                        MySetWrapper.this.changeImpl(implementation_type);
                    }
                };
            }
            case 2: {
                return new MyHashSet(0){

                    @Override
                    public void changeImplementation(IMySet.Impl implementation_type) {
                        MySetWrapper.this.changeImpl(implementation_type);
                    }
                };
            }
        }
        return null;
    }

    protected void changeImpl(IMySet.Impl implementation_type) {
        if (this.getImplementationType() != implementation_type) {
            IMySet newImpl = this.create(this.impl.capacity(), implementation_type);
            Iterator<Integer> it = this.impl.iterator();
            while (it.hasNext()) {
                newImpl.add(it.next());
            }
            this.impl = newImpl;
        }
    }

    @Override
    public void changeImplementation(IMySet.Impl implementation_type) {
        this.changeImpl(implementation_type);
    }

    @Override
    public void fill(int size) {
        this.impl.fill(size);
    }

    @Override
    public void clear(int size) {
        this.impl.clear(size);
    }

    @Override
    public void clearAll(IMySet anotherSet) {
        this.impl.clearAll(anotherSet);
    }

    @Override
    public void add(int num) {
        this.impl.add(num);
    }

    @Override
    public boolean contains(int num) {
        return this.impl.contains(num);
    }

    @Override
    public int cardinality() {
        return this.impl.cardinality();
    }

    @Override
    public boolean containsAll(IMySet anotherSet) {
        return this.impl.containsAll(anotherSet);
    }

    @Override
    public void addAll(IMySet anotherSet) {
        this.impl.addAll(anotherSet);
    }

    @Override
    public Iterator<Integer> iterator() {
        return this.impl.iterator();
    }

    @Override
    public boolean isEmpty() {
        return this.impl.isEmpty();
    }

    @Override
    public int capacity() {
        return this.impl.capacity();
    }

    @Override
    public IMySet.Impl getImplementationType() {
        return this.impl.getImplementationType();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        switch (MySetWrapper.$SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl()[this.impl.getImplementationType().ordinal()]) {
            case 1: {
                return new MySetWrapper(((MyBitSet)this.impl).cloneBitSet(), IMySet.Impl.BITSET);
            }
            case 3: {
                return new MySetWrapper(((MyTIntHashSet)this.impl).cloneTIntHashSet(), IMySet.Impl.TROVE_HASHSET);
            }
            case 2: {
                return new MySetWrapper(((MyHashSet)this.impl).cloneHashSet(), IMySet.Impl.HASHSET);
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return this.impl.equals(o);
    }

    @Override
    public IMySet newIntersect(IMySet anotherSet) {
        switch (MySetWrapper.$SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl()[this.impl.getImplementationType().ordinal()]) {
            case 1: {
                return new MySetWrapper(((MyBitSet)this.impl).newIntersectBitSet(anotherSet), IMySet.Impl.BITSET);
            }
            case 3: {
                return new MySetWrapper(((MyTIntHashSet)this.impl).newIntersectTIntHashSet(anotherSet), IMySet.Impl.TROVE_HASHSET);
            }
            case 2: {
                return new MySetWrapper(((MyHashSet)this.impl).newIntersectHashSet(anotherSet), IMySet.Impl.HASHSET);
            }
        }
        return null;
    }

    @Override
    public IMySet newDifference(IMySet anotherSet) {
        switch (MySetWrapper.$SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl()[this.impl.getImplementationType().ordinal()]) {
            case 1: {
                return new MySetWrapper(((MyBitSet)this.impl).newDifferenceBitSet(anotherSet), IMySet.Impl.BITSET);
            }
            case 3: {
                return new MySetWrapper(((MyTIntHashSet)this.impl).newDifferenceTIntHashSet(anotherSet), IMySet.Impl.HASHSET);
            }
            case 2: {
                return new MySetWrapper(((MyHashSet)this.impl).newDifferenceHashSet(anotherSet), IMySet.Impl.HASHSET);
            }
        }
        return null;
    }

    @Override
    public IMySet getImplementation() {
        return this.impl;
    }

    @Override
    public void remove(int num) {
        this.impl.remove(num);
    }

    static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[IMySet.Impl.values().length];
        try {
            arrn[IMySet.Impl.BITSET.ordinal()] = 1;
        }
        catch (NoSuchFieldError v1) {}
        try {
            arrn[IMySet.Impl.HASHSET.ordinal()] = 2;
        }
        catch (NoSuchFieldError v2) {}
        try {
            arrn[IMySet.Impl.TROVE_HASHSET.ordinal()] = 3;
        }
        catch (NoSuchFieldError v3) {}
        $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl = arrn;
        return $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl;
    }

}

