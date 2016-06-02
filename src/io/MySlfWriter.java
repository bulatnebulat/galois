package io;

import core.IBinaryContext;
import java.io.BufferedWriter;
import java.io.IOException;

public class MySlfWriter {
    IBinaryContext binRel = null;
    protected BufferedWriter _buff = null;
    protected boolean econome;

    public MySlfWriter(BufferedWriter buff, IBinaryContext _binRel, boolean econome) {
        this._buff = buff;
        this.binRel = _binRel;
        this.econome = econome;
    }

    public MySlfWriter(BufferedWriter buff, IBinaryContext _binRel) {
        this(buff, _binRel, false);
    }

    public void write() throws IOException {
        this.writeBinaryContext();
    }

    public void writeBinaryContext() throws IOException {
        int i;
        this._buff.write("[Lattice]\n");
        this._buff.write("" + this.binRel.getObjectNumber() + "\n");
        this._buff.write("" + this.binRel.getAttributeNumber() + "\n");
        this._buff.write("[Objects]\n");
        if (!this.econome) {
            i = 0;
            while (i < this.binRel.getObjectNumber()) {
                this._buff.write(String.valueOf(this.binRel.getObjectName(i)) + "\n");
                ++i;
            }
        }
        this._buff.write("[Attributes]\n");
        if (!this.econome) {
            i = 0;
            while (i < this.binRel.getAttributeNumber()) {
                this._buff.write(String.valueOf(this.binRel.getAttributeName(i)) + "\n");
                ++i;
            }
        }
        this._buff.write("[relation]\n");
        i = 0;
        while (i < this.binRel.getObjectNumber()) {
            int j = 0;
            while (j < this.binRel.getAttributeNumber()) {
                this._buff.write(this.binRel.get(i, j) ? 49 : 48);
                this._buff.write(32);
                ++j;
            }
            this._buff.write(10);
            ++i;
        }
        this._buff.flush();
        this._buff.close();
    }

    public String getDescription() {
        return "SLF Writer";
    }
}

