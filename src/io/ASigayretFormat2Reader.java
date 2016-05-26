/*
 * Decompiled with CFR 0_114.
 */
package io;

import core.IBinaryContext;
import core.MyBinaryContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class ASigayretFormat2Reader {
    BufferedReader buff = null;

    public ASigayretFormat2Reader(BufferedReader _buff) {
        this.buff = _buff;
    }

    public IBinaryContext readBinaryContext() throws IOException {
        String line;
        MyBinaryContext binRel = null;
        int nbObj = 0;
        int nbAtt = 0;
        while (!(line = this.buff.readLine().trim()).startsWith("<RELATION")) {
        }
        int index1 = line.indexOf("nobj=");
        int index2 = (index1 += 6) + 1;
        while (Character.isDigit(line.charAt(index2))) {
            ++index2;
        }
        nbObj = Integer.parseInt(line.substring(index1, index2));
        index1 = line.indexOf("nprop=");
        index2 = (index1 += 7) + 1;
        while (Character.isDigit(line.charAt(index2))) {
            ++index2;
        }
        nbAtt = Integer.parseInt(line.substring(index1, index2));
        binRel = new MyBinaryContext(nbObj, nbAtt, "BC from Sigayret");
        while (!(line = this.buff.readLine()).startsWith("<ROWS>")) {
            System.out.println(line);
        }
        while (!(line = this.buff.readLine()).startsWith("</ROWS>")) {
            String[] cols = line.split(" ");
            System.out.println("col1=" + cols[0] + " col2=" + cols[1]);
        }
        this.buff.close();
        return binRel;
    }

    public String getDescription() {
        return "Sigayret format Reader";
    }
}

