package util;

import core.IBinaryContext;
import core.IMySet;
import core.MyBinaryContext;
import core.MyConcept;
import core.MyConceptSet;
import core.MyGSH;
import core.MySetWrapper;
import core.MyTopDownIterator;
import io.ASigayretFormat1Reader;
import io.ASigayretFormat2Reader;
import io.MyAdjacencyMatrixReader;
import io.MyCSVReader;
import io.MyGaliciaXMLReader;
import io.MyIBMReader;
import io.MySLFReader;
import io.MySlfWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Random;

public class Utils {
    public static IBinaryContext createBinaryContext(int nbObjects, int nbAttributes, double density, String contextName) {
        int total = nbAttributes * nbObjects;
        long nbRelations = Math.round((double)total * density);
        boolean checkTrue = nbRelations < (long)(total / 2);
        MyBinaryContext dbc = new MyBinaryContext(nbObjects, nbAttributes, contextName);
        int numObj = 0;
        while (numObj < nbObjects) {
            int numAttr = 0;
            while (numAttr < nbAttributes) {
                dbc.set(numObj, numAttr, !checkTrue);
                ++numAttr;
            }
            ++numObj;
        }
        int done = 0;
        Random random = new Random();
        while ((long)done != nbRelations) {
            int numAttr = random.nextInt(nbAttributes);
            int numObj2 = random.nextInt(nbObjects);
            if (dbc.get(numObj2, numAttr) == checkTrue) continue;
            dbc.set(numObj2, numAttr, checkTrue);
            ++done;
        }
        return dbc;
    }

    public static void writeGSH(BufferedWriter buff, MyGSH shg, IBinaryContext mbc, boolean reduced) throws IOException {
        MyConcept cpt;
        MySetWrapper setOfAllObjects = new MySetWrapper(0);
        Iterator<MyConcept> it = shg.getMaximals().iterator();
        while (it.hasNext()) {
            cpt = it.next();
            setOfAllObjects.addAll(cpt.getExtent());
        }
        MySetWrapper setOfAllAttributes = new MySetWrapper(0);
        Iterator<MyConcept> it2 = shg.getMinimals().iterator();
        while (it2.hasNext()) {
            cpt = it2.next();
            setOfAllAttributes.addAll(cpt.getIntent());
        }
        buff.write("<GSH numberObj=\"" + setOfAllObjects.cardinality() + "\" numberAtt=\"" + setOfAllAttributes.cardinality() + "\" numberCpt=\"" + shg.size() + "\">\n");
        buff.write("<Name>" + shg.getNameId() + "</Name>\n");
        Iterator<Integer> it3 = setOfAllObjects.iterator();
        while (it3.hasNext()) {
            int fo = it3.next();
            buff.write("<Object>");
            buff.write(mbc.getObjectName(fo));
            buff.write("</Object>\n");
        }
        buff.flush();
        Iterator<Integer> it4 = setOfAllAttributes.iterator();
        while (it4.hasNext()) {
            int fa = it4.next();
            buff.write("<Attribute>");
            buff.write(mbc.getAttributeName(fa));
            buff.write("</Attribute>\n");
        }
        buff.flush();
        Utils.writeConceptualStructure(buff, shg, mbc, reduced);
        buff.write("</GSH>\n");
        buff.flush();
    }

    protected static void writeConceptualStructure(BufferedWriter buff, MyGSH theConceptualStructure, IBinaryContext mbc, boolean reduced) throws IOException {
        MyTopDownIterator it_TopDown = theConceptualStructure.getTopDownIterator();
        while (it_TopDown.hasNext()) {
            MyConcept cpt = it_TopDown.next();
            buff.write("<Concept>\n");
            buff.write("<ID> " + cpt.getId() + " </ID>\n");
            buff.write("<Extent>\n");
            Iterator<Integer> it_extent = reduced ? cpt.getReducedExtent().iterator() : cpt.getExtent().iterator();
            while (it_extent.hasNext()) {
                int fo = it_extent.next();
                buff.write("<Object_Ref>");
                buff.write(mbc.getObjectName(fo));
                buff.write("</Object_Ref>\n");
            }
            buff.write("</Extent>\n");
            buff.write("<Intent>\n");
            Iterator<Integer> it_intent = reduced ? cpt.getReducedIntent().iterator() : cpt.getIntent().iterator();
            while (it_intent.hasNext()) {
                int fa = it_intent.next();
                buff.write("<Attribute_Ref>");
                buff.write(mbc.getAttributeName(fa));
                buff.write("</Attribute_Ref>\n");
            }
            buff.write("</Intent>\n");
            buff.write("<UpperCovers>\n");
            Iterator<MyConcept> it = cpt.getUpperCover().iterator();
            while (it.hasNext()) {
                buff.write("<Concept_Ref>");
                buff.write("" + it.next().getId());
                buff.write("</Concept_Ref>\n");
            }
            buff.write("</UpperCovers>\n");
            buff.write("</Concept>\n");
        }
    }

    public static IBinaryContext readSigayretFormat1(File file) {
        try {
            ASigayretFormat1Reader reader = new ASigayretFormat1Reader(new BufferedReader(new FileReader(file)));
            return reader.readBinaryContext();
        }
        catch (Exception e) {
            System.err.println("Can not read Sigayret format1 binary context !");
            return null;
        }
    }

    public static IBinaryContext readSigayretFormat2(File file) {
        try {
            ASigayretFormat2Reader reader = new ASigayretFormat2Reader(new BufferedReader(new FileReader(file)));
            return reader.readBinaryContext();
        }
        catch (Exception e) {
            System.err.println("Can not read Sigayret format2 binary context !");
            return null;
        }
    }

    public static IBinaryContext readSLF(File file, boolean verbose, boolean econome) {
        try {
            MySLFReader reader = new MySLFReader(new BufferedReader(new FileReader(file)), econome);
            reader.read();
            return reader.getBinaryContext();
        }
        catch (Exception e) {
            if (verbose) {
                System.err.println("Can not read SLF binary context !");
            }
            return null;
        }
    }

    public static IBinaryContext readCSV(File file, boolean verbose) {
        try {
            MyCSVReader reader = new MyCSVReader(new BufferedReader(new FileReader(file)));
            return reader.readBinaryContext();
        }
        catch (Exception e) {
            if (verbose) {
                System.err.println("Can not read CSV binary context !");
            }
            return null;
        }
    }

    public static IBinaryContext readAdjacencyMatrix(File file, boolean verbose) {
        try {
            MyAdjacencyMatrixReader reader = new MyAdjacencyMatrixReader(new BufferedReader(new FileReader(file)));
            reader.read();
            return reader.getBinaryContext();
        }
        catch (Exception e) {
            if (verbose) {
                System.err.println("Can not read adjacency matrix !");
            }
            return null;
        }
    }

    public static IBinaryContext readGaliciaXML(File file, boolean verbose) {
        try {
            MyGaliciaXMLReader reader = new MyGaliciaXMLReader(new FileInputStream(file));
            return reader.readBinaryContext();
        }
        catch (Exception e) {
            if (verbose) {
                System.err.println("Can not read XML binary context !");
            }
            return null;
        }
    }

    public static IBinaryContext readIBM(File file, boolean verbose) {
        try {
            MyIBMReader reader = new MyIBMReader(new BufferedReader(new FileReader(file)));
            reader.read();
            return reader.getBinaryContext();
        }
        catch (Exception e) {
            if (verbose) {
                System.err.println("Can not read IBM binary context !");
            }
            return null;
        }
    }

    public static void writeSLF(File file, IBinaryContext mbc, boolean econome) {
        try {
            MySlfWriter writer = new MySlfWriter(new BufferedWriter(new FileWriter(file)), mbc, econome);
            writer.write();
        }
        catch (Exception e) {
            System.err.println("Can not write the binary context !");
        }
    }
}

