/*
 * Decompiled with CFR 0_114.
 */
package io;

import core.IBinaryContext;
import core.IMySet;
import core.MyBinaryContext;
import core.MySetWrapper;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;

public class MyCSVReader {
    BufferedReader buff = null;

    public MyCSVReader(BufferedReader _buff) {
        this.buff = _buff;
    }

    public IBinaryContext readBinaryContext() throws Exception {
        MyBinaryContext binRel = null;
        int nbAttributes = 0;
        ArrayList<IMySet> rows = new ArrayList<IMySet>();
        String dataRow = this.buff.readLine();
        while (dataRow != null) {
            String[] dataArray = dataRow.split(",");
            if (dataArray.length == 0 || dataArray[0].equals("%")) continue;
            if (dataArray.length > 1) {
                if (dataArray.length > nbAttributes) {
                    nbAttributes = dataArray.length;
                }
                MySetWrapper bs = new MySetWrapper(nbAttributes);
                int numCol = 0;
                String[] arrstring = dataArray;
                int n = arrstring.length;
                int n2 = 0;
                while (n2 < n) {
                    String item = arrstring[n2];
                    if (!(item.trim().isEmpty() || item.trim().equals("0") || item.trim().equalsIgnoreCase("O"))) {
                        if (item.trim().equals("1") || item.trim().equalsIgnoreCase("X")) {
                            bs.add(numCol);
                        } else {
                            throw new Exception("Not valid format for CSV Binary Context");
                        }
                    }
                    ++numCol;
                    ++n2;
                }
                rows.add(bs);
            }
            dataRow = this.buff.readLine();
        }
        binRel = new MyBinaryContext(rows, this.transpose(rows, nbAttributes), "");
        this.buff.close();
        return binRel;
    }

    protected ArrayList<IMySet> transpose(ArrayList<IMySet> rows, int numColumns) {
        ArrayList<IMySet> columns = new ArrayList<IMySet>();
        int i = 0;
        while (i < numColumns) {
            columns.add(new MySetWrapper(rows.size()));
            ++i;
        }
        i = 0;
        while (i < rows.size()) {
            Iterator<Integer> it = rows.get(i).iterator();
            while (it.hasNext()) {
                columns.get(it.next()).add(i);
            }
            ++i;
        }
        return columns;
    }
}
