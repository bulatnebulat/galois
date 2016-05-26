/*
 * Decompiled with CFR 0_114.
 */
package appli;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import algorithm.AbstractAlgorithm;
import algorithm.Ares;
import algorithm.Ceres;
import algorithm.Hermes;
import algorithm.Pluton;
import appli.AccessoryPanel;
import appli.Worker;
import core.IBinaryContext;
import core.IMySet;
import core.MyGSH;
import core.MySetWrapper;
import io.DotWriter;
import util.Chrono;
import util.Utils;

public class AOCPosetBuilder {
    static final String VERSION = "1.0";
    static String algoName = null;
    static IMySet.Impl impl = IMySet.Impl.BITSET;
    static DotWriter.DisplayFormat format = DotWriter.DisplayFormat.REDUCED;
    static boolean reduced = false;
    static boolean displaySize = false;
    static String inputFileName = null;
    static String outputFileName = null;
    static String dotFileName = null;
    static ReadFormat rf = null;
    static boolean verbose = false;
    static boolean help = false;
    static boolean gui = false;
    private static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$appli$AOCPosetBuilder$ReadFormat;

    public static void main(String[] args) {
        block29 : {
            PosixParser parser = new PosixParser();
            Options options = new Options();
            OptionBuilder.withLongOpt("implementation");
            OptionBuilder.withDescription("select implementation. Available implementations are BITSET (default), HASHSET and TROVEHASHSET. respectively with java.util.BitSet, java.util.HashSet, gnu.trove.set.hash.TIntHashSet");
            OptionBuilder.hasArg();
            OptionBuilder.withArgName("IMPL");
            options.addOption(OptionBuilder.create('m'));
            OptionBuilder.withLongOpt("inputformat");
            OptionBuilder.withDescription("format of the input file. Supported formats are CSV, IBM, SLF, GALICIA_XML, ADJACENCY_MATRIX");
            OptionBuilder.hasArg();
            OptionBuilder.withArgName("INPUT-FORMAT");
            options.addOption(OptionBuilder.create('n'));
            options.addOption("h", "help", false, "print the help content");
            options.addOption("g", "gui", false, "user interface");
            OptionBuilder.withLongOpt("input");
            OptionBuilder.withDescription("input filename of binary context");
            OptionBuilder.hasArg();
            OptionBuilder.withArgName("INPUT");
            options.addOption(OptionBuilder.create('i'));
            OptionBuilder.withLongOpt("output");
            OptionBuilder.withDescription("output filename of Galois Sub-Hierarchy");
            OptionBuilder.hasArg();
            OptionBuilder.withArgName("OUTPUT");
            options.addOption(OptionBuilder.create('o'));
            OptionBuilder.withLongOpt("dotfile");
            OptionBuilder.withDescription("output .dot filename for GraphViz");
            OptionBuilder.hasArg();
            OptionBuilder.withArgName("DOTFILE");
            options.addOption(OptionBuilder.create('d'));
            OptionBuilder.withLongOpt("algo");
            OptionBuilder.withDescription("name of algorithm to be used. Available algo are ARES, CERES, PLUTON or HERMES,");
            OptionBuilder.hasArg();
            OptionBuilder.withArgName("ALGO");
            options.addOption(OptionBuilder.create('a'));
            OptionBuilder.withLongOpt("format");
            OptionBuilder.withDescription("display format of the concepts for GraphViz. Available formats are SIMPLIFIED (default), FULL and MINIMAL");
            OptionBuilder.hasArg();
            OptionBuilder.withArgName("DISPLAY-FORMAT");
            options.addOption(OptionBuilder.create('f'));
            OptionBuilder.withLongOpt("simplified");
            OptionBuilder.withDescription("simplified intent and extent in Galois Sub-Hierarchy xml representation.");
            options.addOption(OptionBuilder.create('s'));
            OptionBuilder.withLongOpt("size");
            OptionBuilder.withDescription("option for GraphViz. Display size of intent and extent");
            options.addOption(OptionBuilder.create('z'));
            OptionBuilder.withLongOpt("verbose");
            OptionBuilder.withDescription("print a report of the algorithm execution");
            options.addOption(OptionBuilder.create('v'));
            try {
                CommandLine line = parser.parse(options, args);
                if (line.hasOption("verbose")) {
                    verbose = true;
                }
                if (line.hasOption("inputformat")) {
                    rf = ReadFormat.valueOf(line.getOptionValue("inputformat").toUpperCase());
                }
                if (line.hasOption('h')) {
                    AOCPosetBuilder.printHelp(options);
                    help = true;
                }
                if (line.hasOption("gui")) {
                    gui = true;
                }
                if (line.hasOption("algo")) {
                    algoName = line.getOptionValue("algo");
                } else if (!help) {
                    gui = true;
                }
                if (line.hasOption("format")) {
                    String sformat = line.getOptionValue("format");
                    if ("full".equalsIgnoreCase(sformat)) {
                        format = DotWriter.DisplayFormat.FULL;
                    } else if ("minimal".equalsIgnoreCase(sformat)) {
                        format = DotWriter.DisplayFormat.MINIMAL;
                    }
                }
                if (line.hasOption("simplified")) {
                    reduced = true;
                }
                if (line.hasOption("size")) {
                    displaySize = true;
                }
                if (line.hasOption("input")) {
                    inputFileName = line.getOptionValue("input");
                } else if (!help) {
                    gui = true;
                }
                if (line.hasOption("output")) {
                    outputFileName = line.getOptionValue("output");
                }
                if (line.hasOption("dotfile")) {
                    dotFileName = line.getOptionValue("dotfile");
                }
                if (!line.hasOption("implementation")) break block29;
                String strImpl = line.getOptionValue("implementation");
                if (strImpl.equalsIgnoreCase("BITSET")) {
                    impl = IMySet.Impl.BITSET;
                    break block29;
                }
                if (strImpl.equalsIgnoreCase("HASHSET")) {
                    impl = IMySet.Impl.HASHSET;
                    break block29;
                }
                if (strImpl.equalsIgnoreCase("TROVEHASHSET")) {
                    impl = IMySet.Impl.TROVE_HASHSET;
                    break block29;
                }
                System.err.println("option value for -implementation option is BITSET, HASHSET or TROVE_HASHSET");
                AOCPosetBuilder.printHelp(options);
                return;
            }
            catch (ParseException exp) {
                System.err.println("Command line error:" + exp.getMessage());
                AOCPosetBuilder.printHelp(options);
                return;
            }
            catch (Exception e) {
                System.err.println("Error:" + e.getMessage());
                AOCPosetBuilder.printHelp(options);
                return;
            }
        }
        if (gui) {
            AOCPosetBuilder.gui();
        } else if (algoName != null && inputFileName != null) {
            try {
                AOCPosetBuilder.runIt(new File(inputFileName), null);
            }
            catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    private static void runIt(File theBinCtxFile, Worker.Task task) throws Exception {
        AbstractAlgorithm algo;
        MySetWrapper.setDefaultImplementation(impl);
        if (!theBinCtxFile.exists()) {
            throw new Exception("The specified file path for input file is not existing");
        }
        if (!theBinCtxFile.canRead()) {
            throw new Exception("The specified file path for input file is not readable");
        }
        File theGshFile = null;
        if (outputFileName != null) {
            theGshFile = new File(outputFileName);
            if (!theGshFile.exists()) {
                theGshFile.createNewFile();
            } else if (!theGshFile.canWrite()) {
                throw new Exception("The specified file path for the result is not writable ! ");
            }
        }
        IBinaryContext mbc = null;
        ReadFormat usedRf = rf;
        if (rf != null) {
            switch (AOCPosetBuilder.$SWITCH_TABLE$fr$lirmm$marel$gsh2$appli$AOCPosetBuilder$ReadFormat()[rf.ordinal()]) {
                case 3: {
                    mbc = Utils.readSLF(theBinCtxFile, true, false);
                    break;
                }
                case 2: {
                    mbc = Utils.readIBM(theBinCtxFile, true);
                    break;
                }
                case 5: {
                    mbc = Utils.readAdjacencyMatrix(theBinCtxFile, true);
                    break;
                }
                case 4: {
                    mbc = Utils.readGaliciaXML(theBinCtxFile, true);
                    break;
                }
                case 1: {
                    mbc = Utils.readCSV(theBinCtxFile, true);
                }
            }
            if (mbc == null) {
                return;
            }
        } else {
            usedRf = ReadFormat.SLF;
            mbc = Utils.readSLF(theBinCtxFile, false, false);
            if (mbc == null) {
                usedRf = ReadFormat.GALICIA_XML;
                mbc = Utils.readGaliciaXML(theBinCtxFile, false);
            }
            if (mbc == null) {
                usedRf = ReadFormat.IBM;
                mbc = Utils.readIBM(theBinCtxFile, false);
            }
            if (mbc == null) {
                usedRf = ReadFormat.ADJACENCY_MATRIX;
                mbc = Utils.readAdjacencyMatrix(theBinCtxFile, false);
            }
            if (mbc == null) {
                usedRf = ReadFormat.CSV;
                mbc = Utils.readCSV(theBinCtxFile, false);
            }
            if (mbc == null) {
                throw new Exception("Unknown format for input. Please use autorized input format");
            }
        }
        if (verbose) {
            System.out.println(AOCPosetBuilder.getReportBeg(usedRf, mbc));
        }
        if (task != null) {
            task.message(AOCPosetBuilder.getReportBeg(usedRf, mbc));
        }
        Object result = null;
        Chrono chrono = verbose || task != null ? new Chrono("chono") : null;
        if ("ceres".equalsIgnoreCase(algoName)) {
            algo = new Ceres(mbc, chrono);
            algo.exec();
            result = algo.getResult();
        } else if ("hermes".equalsIgnoreCase(algoName)) {
            algo = new Hermes(mbc, chrono);
            algo.exec();
            result = algo.getResult();
        } else if ("ares".equalsIgnoreCase(algoName)) {
            algo = new Ares(mbc, chrono);
            algo.exec();
            result = algo.getResult();
        } else if ("pluton".equalsIgnoreCase(algoName)) {
            algo = new Pluton(mbc, chrono);
            algo.exec();
            result = algo.getResult();
        } else {
            throw new Exception("The algorithm \"" + algoName + "\" cannot be found");
        }
        if (verbose) {
            System.out.println(AOCPosetBuilder.getReportEnd(algo, impl, chrono, (MyGSH)result));
        }
        if (task != null) {
            task.message(AOCPosetBuilder.getReportEnd(algo, impl, chrono, (MyGSH)result));
        }
        if (theGshFile != null) {
            BufferedWriter buff = new BufferedWriter(new FileWriter(theGshFile));
            Utils.writeGSH(buff, (MyGSH)result, mbc, reduced);
        }
        if (dotFileName != null) {
            DotWriter dw = new DotWriter(new BufferedWriter(new FileWriter(new File(dotFileName))), (MyGSH)result, mbc, format, displaySize);
            dw.write();
        }
    }

    private static String getReportEnd(AbstractAlgorithm algo, IMySet.Impl impl, Chrono chrono, MyGSH result) {
        return "*** Results ****\n Algorithm: " + algo.getDescription() + " (" + (Object)((Object)impl) + ")\n" + " Exec.time: " + chrono.getResult() + " ms.\n" + "Nb.concept: " + result.size();
    }

    private static String getReportBeg(ReadFormat rf, IBinaryContext mbc) {
        return "**** Input *****\n    Format: " + (Object)((Object)rf) + "\n" + " Nb.object: " + mbc.getObjectCount() + "\n" + "  Nb.attr.: " + mbc.getAttributeCount() + "\n" + "   Density: " + new DecimalFormat("#.#####").format(mbc.getDensity());
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar AOCPosetBuilder.jar", options, true);
    }

    private static void gui() {
        int returnVal;
        FileNameExtensionFilter[] filters = new FileNameExtensionFilter[]{new FileNameExtensionFilter("CSV document (*.csv)", "txt", "csv"), new FileNameExtensionFilter("IBM document (*.ibm)", "basenum", "ibm"), new FileNameExtensionFilter("SLF document (*.slf)", "slf"), new FileNameExtensionFilter("Galicia XML (*.xml)", "galicia", "xml"), new FileNameExtensionFilter("Adjacency matrix (*.adj)", "adj")};
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.indexOf("mac") < 0) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
        }
        catch (Throwable t3) {
            System.out.println("Problem occurs when loading Look & Feel");
        }
        AccessoryPanel accessoryPanel = new AccessoryPanel();
        JFileChooser fc = new JFileChooser(){

            @Override
            public void approveSelection() {
                int reply;
                File f;
                if (AccessoryPanel.this.getOutputFileName() == null && AccessoryPanel.this.getDotFileName() == null) {
                    JOptionPane.showMessageDialog(null, "There is nothing to build. Please check to build DOT and/or XML representation file to build", "Error", 0);
                    return;
                }
                if (AccessoryPanel.this.getOutputFileName() != null && (f = new File(AccessoryPanel.this.getOutputFileName())).exists() && (reply = JOptionPane.showConfirmDialog(null, "File " + f.getPath() + " already exists. Replace it ?", "Writing XML file", 0)) != 0) {
                    return;
                }
                if (AccessoryPanel.this.getDotFileName() != null && (f = new File(AccessoryPanel.this.getDotFileName())).exists() && (reply = JOptionPane.showConfirmDialog(null, "File " + f.getPath() + " already exists. Replace it ?", "Writing DOT file", 0)) != 0) {
                    return;
                }
                super.approveSelection();
            }
        };
        FileFilter default_filter = fc.getFileFilter();
        FileNameExtensionFilter[] arrfileNameExtensionFilter = filters;
        int n = arrfileNameExtensionFilter.length;
        int n2 = 0;
        while (n2 < n) {
            FileNameExtensionFilter filter = arrfileNameExtensionFilter[n2];
            fc.addChoosableFileFilter(filter);
            ++n2;
        }
        if (rf != null) {
            switch (AOCPosetBuilder.$SWITCH_TABLE$fr$lirmm$marel$gsh2$appli$AOCPosetBuilder$ReadFormat()[rf.ordinal()]) {
                case 1: {
                    fc.setFileFilter(filters[0]);
                    break;
                }
                case 2: {
                    fc.setFileFilter(filters[1]);
                    break;
                }
                case 3: {
                    fc.setFileFilter(filters[2]);
                    break;
                }
                case 4: {
                    fc.setFileFilter(filters[3]);
                    break;
                }
                case 5: {
                    fc.setFileFilter(filters[4]);
                }
                default: {
                    break;
                }
            }
        } else {
            fc.setFileFilter(default_filter);
        }
        fc.addPropertyChangeListener("fileFilterChanged", new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Object o = evt.getNewValue();
                if (o instanceof FileNameExtensionFilter) {
                    FileNameExtensionFilter filter = (FileNameExtensionFilter)o;
                    if (filter == val$filters[0]) {
                        AOCPosetBuilder.rf = ReadFormat.CSV;
                    }
                    if (filter == val$filters[1]) {
                        AOCPosetBuilder.rf = ReadFormat.IBM;
                    }
                    if (filter == val$filters[2]) {
                        AOCPosetBuilder.rf = ReadFormat.SLF;
                    }
                    if (filter == val$filters[3]) {
                        AOCPosetBuilder.rf = ReadFormat.GALICIA_XML;
                    }
                    if (filter == val$filters[4]) {
                        AOCPosetBuilder.rf = ReadFormat.ADJACENCY_MATRIX;
                    }
                } else {
                    AOCPosetBuilder.rf = null;
                }
            }
        });
        accessoryPanel.init(algoName, outputFileName, dotFileName, impl, format, displaySize, reduced);
        fc.setAccessory(accessoryPanel);
        fc.setApproveButtonText("Continue");
        fc.setApproveButtonMnemonic('x');
        fc.setApproveButtonToolTipText("Build AOC-poset of selected matrix");
        fc.setDialogTitle("AOC-poset Builder");
        while ((returnVal = fc.showOpenDialog(null)) == 0) {
            File iFile = fc.getSelectedFile();
            try {
                algoName = accessoryPanel.getAlgoName();
                outputFileName = accessoryPanel.getOutputFileName();
                dotFileName = accessoryPanel.getDotFileName();
                impl = accessoryPanel.getImpl();
                format = accessoryPanel.getFormat();
                displaySize = accessoryPanel.getDisplaySize();
                reduced = accessoryPanel.getReduced();
                Worker worker = new Worker(){

                    @Override
                    public boolean work(Worker.Task task) {
                        try {
                            AOCPosetBuilder.runIt(File.this, task);
                        }
                        catch (Throwable t) {
                            JOptionPane.showMessageDialog(null, t);
                        }
                        return true;
                    }

                    @Override
                    public boolean terminate(Worker.Task task) {
                        return true;
                    }
                };
                worker.go(null, "Running " + algoName + "(" + (Object)((Object)impl) + ")", "<html>Input: <b>" + iFile.getPath() + "</b><br>Dot file: <b>" + (dotFileName == null ? "no" : dotFileName) + "</b><br>Xml file: <b>" + (outputFileName == null ? "no" : outputFileName) + "</b></html>");
            }
            catch (Exception worker) {}
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$appli$AOCPosetBuilder$ReadFormat() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$fr$lirmm$marel$gsh2$appli$AOCPosetBuilder$ReadFormat;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[ReadFormat.values().length];
        try {
            arrn[ReadFormat.ADJACENCY_MATRIX.ordinal()] = 5;
        }
        catch (NoSuchFieldError v1) {}
        try {
            arrn[ReadFormat.CSV.ordinal()] = 1;
        }
        catch (NoSuchFieldError v2) {}
        try {
            arrn[ReadFormat.GALICIA_XML.ordinal()] = 4;
        }
        catch (NoSuchFieldError v3) {}
        try {
            arrn[ReadFormat.IBM.ordinal()] = 2;
        }
        catch (NoSuchFieldError v4) {}
        try {
            arrn[ReadFormat.SLF.ordinal()] = 3;
        }
        catch (NoSuchFieldError v5) {}
        $SWITCH_TABLE$fr$lirmm$marel$gsh2$appli$AOCPosetBuilder$ReadFormat = arrn;
        return $SWITCH_TABLE$fr$lirmm$marel$gsh2$appli$AOCPosetBuilder$ReadFormat;
    }

    static enum ReadFormat {
        CSV,
        IBM,
        SLF,
        GALICIA_XML,
        ADJACENCY_MATRIX;
        

        
    }

}

