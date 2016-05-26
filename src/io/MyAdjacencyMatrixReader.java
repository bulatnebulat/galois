/*
 * Decompiled with CFR 0_114.
 */
package io;

import fr.lirmm.marel.gsh2.core.IBinaryContext;
import fr.lirmm.marel.gsh2.core.IMySet;
import fr.lirmm.marel.gsh2.core.MyBinaryContext;
import fr.lirmm.marel.gsh2.core.MySetWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class MyAdjacencyMatrixReader {
    BufferedReader buff = null;
    protected IBinaryContext result;

    public MyAdjacencyMatrixReader(BufferedReader _buff) {
        this.buff = _buff;
    }

    public void read() throws IOException {
        this.result = this.readBinaryContext();
    }

    protected IBinaryContext readBinaryContext() throws IOException {
        try {
            String line;
            ArrayList<IMySet> rows = new ArrayList<IMySet>();
            int max = 0;
            int nbAttr = 0;
            int count = 0;
            while ((line = this.buff.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line = line.trim().replace(' ', '\t'), "\t");
                String token1 = st.nextToken();
                if (token1.equals("%")) continue;
                String token2 = st.nextToken();
                int id1 = Integer.parseInt(token1);
                int id2 = Integer.parseInt(token2);
                if (rows.size() <= id1) {
                    int i = rows.size();
                    while (i <= id1) {
                        rows.add(i, new MySetWrapper(0));
                        ++i;
                    }
                }
                if (nbAttr <= id2) {
                    nbAttr = id2 + 1;
                }
                if (max < rows.size()) {
                    max = rows.size();
                }
                IMySet bs1 = rows.get(id1);
                bs1.add(id2);
                ++count;
            }
            MyBinaryContext binRel = new MyBinaryContext(rows, this.transpose(rows, nbAttr), "");
            return binRel;
        }
        catch (Exception e) {
            throw new IOException("Attempt to read adjacency matrix failed");
        }
    }

    public String getDescription() {
        return "Adjacency Matrix Reader";
    }

    public IBinaryContext getBinaryContext() {
        return this.result;
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

