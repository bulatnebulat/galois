/*
 * Decompiled with CFR 0_114.
 */
package core;

import core.IMySet;

public interface IBinaryContext {
    public int addAttributeName(String var1);

    public String getAttributeName(int var1);

    public String getObjectName(int var1);

    public int addObjectName(String var1);

    public int getAttributeCount();

    public int getObjectCount();

    public boolean get(int var1, int var2);

    public void set(int var1, int var2, boolean var3);

    public IMySet getExtent(int var1);

    public IMySet getIntent(int var1);

    public double getObjectsDensity();

    public double getAtributesDensity();

    public double getDensity();

    public double getDataComplexity();

    public int getAttributeNumber();

    public int getObjectNumber();

    public String getName();

    public void changeImplementation(IMySet.Impl var1);

    public IBinaryContext transpose();
}

