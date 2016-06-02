package core;

import core.IBinaryContext;
import core.IMySet;
import core.MySetWrapper;
import java.util.ArrayList;

public class MyBinaryContext
implements IBinaryContext {
    protected String name;
    protected ArrayList<String> objectNames = new ArrayList();
    protected ArrayList<String> attrNames = new ArrayList();
    protected ArrayList<IMySet> rows = new ArrayList();
    protected ArrayList<IMySet> columns = new ArrayList();

    public MyBinaryContext(int nbObjects, int nbAttributes, String name) {
        this.rows = new ArrayList(nbAttributes);
        this.columns = new ArrayList(nbObjects);
        int i = 0;
        while (i < nbAttributes) {
            this.columns.add(new MySetWrapper(nbObjects));
            ++i;
        }
        i = 0;
        while (i < nbObjects) {
            this.rows.add(new MySetWrapper(nbAttributes));
            ++i;
        }
    }

    public MyBinaryContext(ArrayList<IMySet> rows, ArrayList<IMySet> columns, String name) {
        this.rows = rows;
        this.columns = columns;
        this.name = name;
    }

    @Override
    public void changeImplementation(IMySet.Impl impl) {
        for (IMySet row : this.rows) {
            row.changeImplementation(impl);
        }
        for (IMySet col : this.columns) {
            col.changeImplementation(impl);
        }
    }

    @Override
    public int addAttributeName(String name) {
        this.attrNames.add(name);
        return this.attrNames.size() - 1;
    }

    @Override
    public String getAttributeName(int i) {
        try {
            return this.attrNames.get(i);
        }
        catch (Exception e) {
            return "Attribute " + i;
        }
    }

    @Override
    public String getObjectName(int i) {
        try {
            return this.objectNames.get(i);
        }
        catch (Exception e) {
            return "Object " + i;
        }
    }

    @Override
    public int addObjectName(String name) {
        this.objectNames.add(name);
        return this.objectNames.size() - 1;
    }

    @Override
    public int getAttributeCount() {
        return this.columns.size();
    }

    @Override
    public int getObjectCount() {
        return this.rows.size();
    }

    @Override
    public boolean get(int numObject, int numAttribute) {
        return this.rows.get(numObject).contains(numAttribute);
    }

    @Override
    public void set(int numObject, int numAttribute, boolean value) {
        if (value) {
            this.rows.get(numObject).add(numAttribute);
            this.columns.get(numAttribute).add(numObject);
        } else {
            this.rows.get(numObject).remove(numAttribute);
            this.columns.get(numAttribute).remove(numObject);
        }
    }

    @Override
    public IMySet getExtent(int numAttribute) {
        return this.columns.get(numAttribute);
    }

    @Override
    public IMySet getIntent(int numObject) {
        return this.rows.get(numObject);
    }

    @Override
    public double getObjectsDensity() {
        double val = 0.0;
        int sum = 0;
        int i = 0;
        while (i < this.getAttributeCount()) {
            sum += this.getExtent(i).cardinality();
            ++i;
        }
        val = (double)sum / (double)this.getAttributeCount();
        sum = 0;
        return val;
    }

    @Override
    public double getAtributesDensity() {
        double val = 0.0;
        int sum = 0;
        int i = 0;
        while (i < this.getObjectCount()) {
            sum += this.getIntent(i).cardinality();
            ++i;
        }
        val = (double)sum / (double)this.getObjectCount();
        return val;
    }

    @Override
    public double getDensity() {
        double val = 0.0;
        int sum = 0;
        int i = 0;
        while (i < this.getObjectCount()) {
            sum += this.getIntent(i).cardinality();
            ++i;
        }
        val = (double)sum / (double)(this.getObjectCount() * this.getAttributeCount());
        return val;
    }

    @Override
    public double getDataComplexity() {
        double val1 = this.getObjectsDensity();
        double val2 = this.getAtributesDensity();
        return Math.sqrt(val1 * val1 + val2 * val2);
    }

    @Override
    public int getAttributeNumber() {
        return this.getAttributeCount();
    }

    @Override
    public int getObjectNumber() {
        return this.getObjectCount();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IBinaryContext transpose() {
        MyBinaryContext transpose = new MyBinaryContext(this.columns, this.rows, "transpose of " + this.name);
        return transpose;
    }
}

