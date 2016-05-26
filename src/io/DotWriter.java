/*
 * Decompiled with CFR 0_114.
 */
package io;

import core.IBinaryContext;
import core.IMySet;
import core.MyBasicIterator;
import core.MyConcept;
import core.MyConceptSet;
import core.MyGSH;
import java.io.BufferedWriter;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;

public class DotWriter {
    private static final String NEW_CONCEPT_COLOR = "lightblue";
    private static final String FUSION_CONCEPT_COLOR = "orange";
    private MyGSH lattice;
    private IBinaryContext mbc;
    protected BufferedWriter _buff = null;
    private boolean displaySize;
    private boolean useColor;
    private DisplayFormat df;
    private static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat;

    public DotWriter(BufferedWriter buff, MyGSH lattice, IBinaryContext mbc, DisplayFormat df, boolean displaySize) {
        this._buff = buff;
        this.lattice = lattice;
        this.mbc = mbc;
        this.df = df;
        this.displaySize = displaySize;
        this.useColor = true;
    }

    public DotWriter(BufferedWriter _buff, MyGSH lattice, IBinaryContext mbc) {
        this(_buff, lattice, mbc, DisplayFormat.REDUCED, false);
    }

    protected String buildDot() {
        StringBuffer sb = new StringBuffer();
        this.appendHeader(sb);
        this.buildDotConcepts(sb);
        this.appendFooter(sb);
        return sb.toString();
    }

    protected void buildDotConcepts(StringBuffer sb) {
        MyConcept c;
        MyBasicIterator it = this.lattice.getBasicIterator();
        while (it.hasNext()) {
            c = it.next();
            sb.append(String.valueOf(c.hashCode()) + " ");
            sb.append("[shape=record,style=filled");
            if (this.useColor) {
                if (c.isNewConcept()) {
                    sb.append(",fillcolor=lightblue");
                } else if (c.isFusion()) {
                    sb.append(",fillcolor=orange");
                }
            }
            sb.append(",label=\"{");
            sb.append(c);
            if (this.displaySize) {
                sb.append(" (");
                sb.append("I: " + c.getIntent().cardinality());
                sb.append(", ");
                sb.append("E: " + c.getExtent().cardinality());
                sb.append(")");
            }
            sb.append("|");
            Iterator<Integer> it2;
            switch (DotWriter.$SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat()[this.df.ordinal()]) {
                
                case 1: {
                    it2 = c.getReducedIntent().iterator();
                    while (it2.hasNext()) {
                        sb.append(String.valueOf(this.mbc.getAttributeName(it2.next())) + "\\n");
                    }
                    sb.append("|");
                    it2 = c.getReducedExtent().iterator();
                    while (it2.hasNext()) {
                        sb.append(String.valueOf(this.mbc.getObjectName(it2.next())) + "\\n");
                    }
                    break;
                }
                case 2: {
                    it2 = c.getIntent().iterator();
                    while (it2.hasNext()) {
                        sb.append(String.valueOf(this.mbc.getAttributeName(it2.next())) + "\\n");
                    }
                    sb.append("|");
                    it2 = c.getExtent().iterator();
                    while (it2.hasNext()) {
                        sb.append(String.valueOf(this.mbc.getObjectName(it2.next())) + "\\n");
                    }
                    break;
                }
                case 3: {
                    sb.append("Concept ID= " + c.getId() + "\\n");
                    sb.append("|");
                    sb.append("\\n");
                }
            }
            sb.append("}\"];\n");
        }
        it = this.lattice.getBasicIterator();
        while (it.hasNext()) {
            c = it.next();
            for (MyConcept child : c.getUpperCover().values()) {
                sb.append("\t" + c.hashCode() + " -> " + child.hashCode() + "\n");
            }
        }
    }

    private void appendHeader(StringBuffer sb) {
        sb.append("digraph G { \n");
        sb.append("\trankdir=BT;\n");
    }

    private void appendFooter(StringBuffer sb) {
        sb.append("}");
    }

    public void write() {
        try {
            String dot = this.buildDot();
            this._buff.write(dot);
            this._buff.flush();
            this._buff.close();
        }
        catch (Exception e) {
            System.err.println("Error, writing DOT data has stopped:  \n");
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return "DOT Writer";
    }

    static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[DisplayFormat.values().length];
        try {
            arrn[DisplayFormat.FULL.ordinal()] = 2;
        }
        catch (NoSuchFieldError v1) {}
        try {
            arrn[DisplayFormat.MINIMAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError v2) {}
        try {
            arrn[DisplayFormat.REDUCED.ordinal()] = 1;
        }
        catch (NoSuchFieldError v3) {}
        $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat = arrn;
        return $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat;
    }

    public static enum DisplayFormat {
        REDUCED,
        FULL,
        MINIMAL;
        

       
    }

}

