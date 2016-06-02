package io;

import core.IBinaryContext;
import core.MyBinaryContext;
import java.io.BufferedReader;
import java.io.IOException;

public class ASigayretFormat1Reader {
    BufferedReader buff = null;

    public ASigayretFormat1Reader(BufferedReader _buff) {
        this.buff = _buff;
    }

    public IBinaryContext readBinaryContext() throws IOException {
        String line;
        int r;
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
        while (!this.buff.readLine().trim().equalsIgnoreCase("<MATRIX>")) {
        }
        int i = 0;
        int j = 0;
        while ((r = this.buff.read()) != -1 && i < nbObj) {
            if ((char)r == '0') {
                binRel.set(i, j, false);
                if (++j == nbAtt) {
                    ++i;
                    j = 0;
                }
            }
            if ((char)r != '1') continue;
            binRel.set(i, j, true);
            if (++j != nbAtt) continue;
            ++i;
            j = 0;
        }
        this.buff.close();
        return binRel;
    }

    public String getDescription() {
        return "Sigayret format Reader";
    }
}

