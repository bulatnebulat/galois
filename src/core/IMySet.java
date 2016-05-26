/*
 * Decompiled with CFR 0_114.
 */
package core;

import java.util.Iterator;

public interface IMySet
extends Cloneable {
    public Impl getImplementationType();

    public IMySet getImplementation();

    public void changeImplementation(Impl var1);

    public void fill(int var1);

    public void clear(int var1);

    public void clearAll(IMySet var1);

    public void add(int var1);

    public boolean contains(int var1);

    public int capacity();

    public int cardinality();

    public boolean containsAll(IMySet var1);

    public void addAll(IMySet var1);

    public Iterator<Integer> iterator();

    public boolean isEmpty();

    public boolean equals(Object var1);

    public String toString();

    public Object clone() throws CloneNotSupportedException;

    public IMySet newIntersect(IMySet var1);

    public IMySet newDifference(IMySet var1);

    public void remove(int var1);

    public static enum Impl {
        BITSET,
        HASHSET,
        TROVE_HASHSET;
        

  
    }

}

