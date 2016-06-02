package io;

import fr.lirmm.marel.gsh2.core.IBinaryContext;
import fr.lirmm.marel.gsh2.core.MyBinaryContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class MyIBMReader {
    BufferedReader buff = null;
    protected IBinaryContext result;

    public MyIBMReader(BufferedReader _buff) {
        this.buff = _buff;
    }

    public void read() throws IOException {
        this.result = this.readBinaryContext();
    }

    public IBinaryContext readBinaryContext() throws IOException {
        try {
            String line;
            ArrayList objects = new ArrayList();
            int nbAttributes = 0;
            block2 : while ((line = this.buff.readLine()) != null) {
                line = line.trim().replace(' ', '\t');
                ArrayList<Integer> object = null;
                StringTokenizer st = new StringTokenizer(line, "\t");
                boolean first = true;
                while (st.hasMoreElements()) {
                    String token = st.nextToken();
                    if (token.length() == 0) continue;
                    if (first) {
                        if (token.equals("%")) continue block2;
                        first = false;
                        int nbAttr = Integer.decode(token);
                        object = new ArrayList<Integer>(nbAttr);
                        objects.add(object);
                        continue;
                    }
                    int attr = Integer.decode(token);
                    if (attr > nbAttributes) {
                        nbAttributes = attr;
                    }
                    object.add(attr);
                }
            }
            MyBinaryContext binRel = new MyBinaryContext(objects.size(), nbAttributes, "IBM binary context");
            int numobj = 0;
            while (numobj < objects.size()) {
                ArrayList object = (ArrayList)objects.get(numobj);
                Iterator attr = object.iterator();
                while (attr.hasNext()) {
                    int numAttr = (Integer)attr.next();
                    binRel.set(numobj, numAttr - 1, true);
                }
                ++numobj;
            }
            return binRel;
        }
        catch (Exception e) {
            throw new IOException("Attempt to read IBM format failed");
        }
    }

    public String getDescription() {
        return "IBM Reader";
    }

    public IBinaryContext getBinaryContext() {
        return this.result;
    }
}

