/*
 * Decompiled with CFR 0_114.
 */
package io;

import core.IBinaryContext;
import core.MyBinaryContext;
import java.io.BufferedReader;
import java.io.IOException;

public class MySLFReader {
    BufferedReader buff = null;
    protected IBinaryContext result;
    protected boolean econome;

    public MySLFReader(BufferedReader _buff, boolean econome) {
        this.buff = _buff;
        this.econome = econome;
    }

    public MySLFReader(BufferedReader _buff) {
        this(_buff, false);
    }

    public void read() throws IOException {
        this.result = this.readBinaryContext();
    }

    public IBinaryContext readBinaryContext() throws IOException {
        int r;
        MyBinaryContext binRel = null;
        int nbObj = 0;
        int nbAtt = 0;
        String rel = "";
        if (!this.buff.readLine().trim().equalsIgnoreCase("[Lattice]")) {
            throw new IOException("Attempt to read SLF format failed.");
        }
        nbObj = Integer.parseInt(this.buff.readLine().trim());
        nbAtt = Integer.parseInt(this.buff.readLine().trim());
        binRel = new MyBinaryContext(nbObj, nbAtt, "BC from SLF");
        if (!this.buff.readLine().trim().equalsIgnoreCase("[Objects]")) {
            throw new IOException("Attempt to read SLF format failed.");
        }
        String nomObj = null;
        boolean names_truncated = false;
        int i = 0;
        while (i < nbObj) {
            nomObj = this.buff.readLine().trim();
            if (nomObj.trim().equalsIgnoreCase("[Attributes]")) {
                names_truncated = true;
                break;
            }
            if (!this.econome) {
                binRel.addObjectName(nomObj);
            }
            ++i;
        }
        if (!names_truncated && !this.buff.readLine().trim().equalsIgnoreCase("[Attributes]")) {
            throw new IOException("Attempt to read SLF format failed.");
        }
        names_truncated = false;
        String nomAtt = null;
        int i2 = 0;
        while (i2 < nbAtt) {
            nomAtt = this.buff.readLine().trim();
            if (nomAtt.trim().equalsIgnoreCase("[relation]")) {
                names_truncated = true;
                break;
            }
            if (!this.econome) {
                binRel.addAttributeName(nomAtt);
            }
            ++i2;
        }
        if (!names_truncated && !this.buff.readLine().trim().equalsIgnoreCase("[relation]")) {
            throw new IOException("Attempt to read SLF format failed.");
        }
        i2 = 0;
        int j = 0;
        while ((r = this.buff.read()) != -1 && i2 < nbObj) {
            if ((char)r == '0') {
                binRel.set(i2, j, false);
                if (++j == nbAtt) {
                    ++i2;
                    j = 0;
                }
            }
            if ((char)r != '1') continue;
            binRel.set(i2, j, true);
            if (++j != nbAtt) continue;
            ++i2;
            j = 0;
        }
        this.buff.close();
        return binRel;
    }

    public String getDescription() {
        return "SLF Reader";
    }

    public IBinaryContext getBinaryContext() {
        return this.result;
    }
}

