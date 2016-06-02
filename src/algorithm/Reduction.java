
package algorithm;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import algorithm.AbstractAlgorithm;
import core.IBinaryContext;
import core.IMySet;
import core.MyBinaryContext;
import core.MySetWrapper;
import util.Chrono;
import util.Utils;

public class Reduction
extends AbstractAlgorithm {
    private IBinaryContext matrix = null;
    private Chrono chrono = null;

    public Reduction(IBinaryContext binCtx, Chrono chrono) {
        this.matrix = binCtx;
        this.chrono = chrono;
    }

    public Reduction(IBinaryContext binCtx) {
        this(binCtx, null);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Syntax error give fileName");
            return;
        }
        int numarg = 0;
        while (numarg < args.length) {
            File f = new File(args[numarg]);
            IBinaryContext bc = Utils.readSLF(f, true, true);
            String sDensity = new DecimalFormat("#.#####").format(bc.getDensity());
            System.out.println("***Reduction de " + f.getName() + " (densit\u00e9=" + sDensity + ")******");
            System.out.print(String.valueOf(bc.getObjectCount()) + "x" + bc.getAttributeCount() + "->");
            Reduction reduc = new Reduction(bc);
            reduc.exec();
            IBinaryContext bc2 = (IBinaryContext)reduc.getResult();
            System.out.println(String.valueOf(bc2.getObjectCount()) + "x" + bc2.getAttributeCount());
            double taux = 100.0 * (double)bc2.getObjectCount() * (double)bc2.getAttributeCount() / (double)(bc.getObjectCount() * bc.getAttributeCount());
            String sTaux = new DecimalFormat("#.#####").format(taux);
            System.out.println("Taille r\u00e9duite \u00e0 " + sTaux + "% de la taille initiale");
            ++numarg;
        }
    }

    @Override
    public void exec() {
        IMySet first;
        int i;
        IMySet current;
        int numObj;
        int numAttr;
        Object inter;
        ArrayList<RefSet> attrSets = new ArrayList<RefSet>();
        ArrayList<RefSet> objSets = new ArrayList<RefSet>();
        if (this.chrono != null) {
            this.chrono.start("clarify");
        }
        if (this.matrix.getAttributeCount() > this.matrix.getObjectCount()) {
            numAttr = 0;
            while (numAttr < this.matrix.getAttributeCount()) {
                attrSets.add(new RefSet(this, numAttr, this.matrix.getExtent(numAttr)));
                ++numAttr;
            }
            numObj = 0;
            while (numObj < this.matrix.getObjectCount()) {
                objSets.add(new RefSet(this, numObj));
                ++numObj;
            }
            objSets = this.clarify(attrSets, objSets);
            attrSets = this.clarify(objSets, attrSets);
        } else {
            numObj = 0;
            while (numObj < this.matrix.getObjectCount()) {
                objSets.add(new RefSet(this, numObj, this.matrix.getIntent(numObj)));
                ++numObj;
            }
            numAttr = 0;
            while (numAttr < this.matrix.getAttributeCount()) {
                attrSets.add(new RefSet(this, numAttr));
                ++numAttr;
            }
            attrSets = this.clarify(objSets, attrSets);
            objSets = this.clarify(attrSets, objSets);
        }
        TreeSet<Integer> to_remove = new TreeSet<Integer>();
        int numLine = 0;
        while (numLine < objSets.size() - 1) {
            current = objSets.get((int)numLine).values;
            first = objSets.get((int)(numLine + 1)).values;
            inter = first.newDifference(current);
            i = numLine + 2;
            while (i < objSets.size()) {
                if (objSets.get((int)i).values.containsAll(current)) {
                    inter = inter.newDifference(objSets.get((int)i).values);
                }
                ++i;
            }
            if (inter.cardinality() == 0) {
                to_remove.add(numLine);
            }
            ++numLine;
        }
        Iterator it = to_remove.descendingIterator();
        while (it.hasNext()) {
            objSets.remove((Integer)it.next());
        }
        int i2 = 0;
        while (i2 < attrSets.size()) {
            RefSet refSet = attrSets.get(i2);
            refSet.values = new MySetWrapper(objSets.size());
            int numObj2 = 0;
            while (numObj2 < objSets.size()) {
                if (objSets.get((int)numObj2).values.contains(i2)) {
                    refSet.values.add(numObj2);
                }
                ++numObj2;
            }
            ++i2;
        }
        to_remove = new TreeSet();
        int numCol = 0;
        while (numCol < attrSets.size() - 1) {
            current = attrSets.get((int)numCol).values;
            first = attrSets.get((int)(numCol + 1)).values;
            inter = first.newDifference(current);
            i = numCol + 2;
            while (i < attrSets.size()) {
                if (attrSets.get((int)i).values.containsAll(current)) {
                    inter = inter.newDifference(attrSets.get((int)i).values);
                }
                ++i;
            }
            if (inter.cardinality() == 0) {
                to_remove.add(numCol);
            }
            ++numCol;
        }
        Iterator it2 = to_remove.descendingIterator();
        while (it2.hasNext()) {
            attrSets.remove((Integer)it2.next());
        }
        ArrayList<IMySet> rows = new ArrayList<IMySet>();
        ArrayList<IMySet> cols = new ArrayList<IMySet>();
        for (RefSet rs2 : objSets) {
            rows.add(rs2.values);
        }
        for (RefSet rs2 : attrSets) {
            cols.add(rs2.values);
        }
        this.result = new MyBinaryContext(rows, cols, "result");
    }

    protected ArrayList<RefSet> clarify(ArrayList<RefSet> setToClarify, ArrayList<RefSet> setToSynchronize) {
        Comparator<RefSet> comparator = new Comparator<RefSet>(){

            @Override
            public int compare(RefSet o1, RefSet o2) {
                int card2;
                int card1 = o1.values.cardinality();
                if (card1 < (card2 = o2.values.cardinality())) {
                    return 1;
                }
                if (card1 == card2) {
                    return 0;
                }
                return -1;
            }
        };
        Collections.sort(setToClarify, comparator);
        int i = setToClarify.size() - 1;
        while (i > 0) {
            RefSet setToCompare = setToClarify.get(i);
            int j = i - 1;
            while (j >= 0) {
                RefSet iSet = setToClarify.get(j);
                int comparison = comparator.compare(setToCompare, iSet);
                if (comparison != 0) break;
                if (setToCompare.values.equals(iSet.values)) {
                    iSet.addRef(setToCompare.refs);
                    setToClarify.remove(i);
                    break;
                }
                --j;
            }
            --i;
        }
        ArrayList<RefSet> attrSets = new ArrayList<RefSet>(setToSynchronize.size());
        int i2 = 0;
        while (i2 < setToSynchronize.size()) {
            attrSets.add(new RefSet(this, setToSynchronize.get((int)i2).refs));
            ++i2;
        }
        i2 = 0;
        while (i2 < setToClarify.size()) {
            IMySet ms = setToClarify.get((int)i2).values;
            Iterator<Integer> it = ms.iterator();
            while (it.hasNext()) {
                attrSets.get((int)it.next().intValue()).values.add(i2);
            }
            ++i2;
        }
        return attrSets;
    }

    @Override
    public String getDescription() {
        return "Reduction";
    }

    class RefSet {
        IMySet refs;
        IMySet values;
        final /* synthetic */ Reduction this$0;

        RefSet(Reduction reduction) {
            this.this$0 = reduction;
            this.refs = new MySetWrapper(0);
            this.values = new MySetWrapper(0);
        }

        public boolean isInclude(RefSet anotherRefSet) {
            return anotherRefSet.values.containsAll(this.values);
        }

        RefSet(Reduction reduction, int[] ref, int[] values) {
            int i;
            this.this$0 = reduction;
            this.refs = new MySetWrapper(ref.length);
            int[] arrn = ref;
            int n = arrn.length;
            int n2 = 0;
            while (n2 < n) {
                int i2 = arrn[n2];
                this.refs.add(i2);
                ++n2;
            }
            int max = 0;
            int[] arrn2 = values;
            int n3 = arrn2.length;
            n = 0;
            while (n < n3) {
                i = arrn2[n];
                if (i > max) {
                    max = i;
                }
                ++n;
            }
            this.values = new MySetWrapper(max + 1);
            arrn2 = values;
            n3 = arrn2.length;
            n = 0;
            while (n < n3) {
                i = arrn2[n];
                this.values.add(i);
                ++n;
            }
        }

        RefSet(Reduction reduction, int ref, IMySet values) {
            this.this$0 = reduction;
            this.refs = new MySetWrapper(1);
            this.refs.add(ref);
            try {
                this.values = (IMySet)values.clone();
            }
            catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        RefSet(Reduction reduction, int ref) {
            this.this$0 = reduction;
            this.refs = new MySetWrapper(1);
            this.refs.add(ref);
            this.values = new MySetWrapper(0);
        }

        RefSet(Reduction reduction, IMySet refs) {
            this.this$0 = reduction;
            this.values = new MySetWrapper(0);
            try {
                this.refs = (IMySet)refs.clone();
            }
            catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        void addRef(int ref) {
            this.refs.add(ref);
        }

        void addRef(IMySet refsToAdd) {
            this.refs.addAll(refsToAdd);
        }
    }

}

