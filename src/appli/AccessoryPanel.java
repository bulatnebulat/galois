/*
 * Decompiled with CFR 0_114.
 */
package appli;

import fr.lirmm.marel.gsh2.core.IMySet;
import fr.lirmm.marel.gsh2.io.DotWriter;
import fr.lirmm.marel.gsh2.util.VerticalFlowLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class AccessoryPanel
extends JPanel {
    JTextField xmlField = null;
    JTextField dotField = null;
    JRadioButton rbAres = new JRadioButton("Ares");
    JRadioButton rbCeres = new JRadioButton("Ceres");
    JRadioButton rbHermes = new JRadioButton("Hermes");
    JRadioButton rbPluton = new JRadioButton("Pluton");
    JRadioButton rbBitSet = new JRadioButton("BitSet");
    JRadioButton rbHashSet = new JRadioButton("HashSet");
    JRadioButton rbTIntHashSet = new JRadioButton("TIntHashSet");
    JRadioButton rbDisplayFull = new JRadioButton("Full");
    JRadioButton rbDisplaySimplified = new JRadioButton("Simplified");
    JRadioButton rbDisplayMinimal = new JRadioButton("Minimal");
    JCheckBox cbSize = new JCheckBox("Display intent/extent size");
    JCheckBox cbReduced = new JCheckBox("Simplified intent/extent in AOC-poset xml representation");
    JCheckBox cbDot = new JCheckBox("Build a .DOT file for GraphViz");
    JCheckBox cbXml = new JCheckBox("Build XML file");
    JLabel labelFileDot = new JLabel("File:");
    JLabel labelFileXml = new JLabel("File:");
    private static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl;
    private static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat;

    public AccessoryPanel() {
        super(new VerticalFlowLayout(0, 0, 0, true, false));
    }

    public void init(String algoName, String outputFileName, String dotFileName, IMySet.Impl impl, DotWriter.DisplayFormat format, boolean displaySize, boolean reduced) {
        JPanel panelProg = new JPanel(new GridLayout(0, 2));
        JPanel panelAlgo = new JPanel(new VerticalFlowLayout(0, 0, 0, true, false));
        panelAlgo.add(this.rbAres);
        panelAlgo.add(this.rbCeres);
        panelAlgo.add(this.rbHermes);
        panelAlgo.add(this.rbPluton);
        panelAlgo.setBorder(BorderFactory.createTitledBorder("Algorithm"));
        ButtonGroup group = new ButtonGroup();
        group.add(this.rbAres);
        group.add(this.rbCeres);
        group.add(this.rbHermes);
        group.add(this.rbPluton);
        if (algoName == null) {
            this.rbHermes.setSelected(true);
        } else if ("ares".equalsIgnoreCase(algoName)) {
            this.rbAres.setSelected(true);
        } else if ("ceres".equalsIgnoreCase(algoName)) {
            this.rbCeres.setSelected(true);
        } else if ("hermes".equalsIgnoreCase(algoName)) {
            this.rbHermes.setSelected(true);
        } else if ("pluton".equalsIgnoreCase(algoName)) {
            this.rbPluton.setSelected(true);
        }
        JPanel panelImpl = new JPanel(new VerticalFlowLayout(0, 0, 0, true, false));
        panelImpl.add(this.rbBitSet);
        panelImpl.add(this.rbHashSet);
        panelImpl.add(this.rbTIntHashSet);
        panelImpl.setBorder(BorderFactory.createTitledBorder("Implementation"));
        ButtonGroup group2 = new ButtonGroup();
        group2.add(this.rbBitSet);
        group2.add(this.rbHashSet);
        group2.add(this.rbTIntHashSet);
        switch (AccessoryPanel.$SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl()[impl.ordinal()]) {
            case 1: {
                this.rbBitSet.setSelected(true);
                break;
            }
            case 2: {
                this.rbHashSet.setSelected(true);
                break;
            }
            case 3: {
                this.rbTIntHashSet.setSelected(true);
            }
        }
        panelProg.add(panelAlgo);
        panelProg.add(panelImpl);
        this.add(panelProg);
        JPanel panelDot = new JPanel(new VerticalFlowLayout(0, 0, 0, true, false));
        panelDot.add(this.cbDot);
        JPanel filePanel = new JPanel();
        this.dotField = new JTextField();
        if (dotFileName != null) {
            this.dotField.setText(dotFileName);
        }
        this.dotField.setPreferredSize(new Dimension(250, 20));
        filePanel.add(this.labelFileDot);
        filePanel.add(this.dotField);
        final JButton browseButton = new JButton("...");
        browseButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal;
                JFileChooser fc2 = new JFileChooser();
                String path = AccessoryPanel.this.dotField.getText();
                if (path != null && path.length() > 1) {
                    fc2.setCurrentDirectory(new File(path).getParentFile());
                }
                if ((returnVal = fc2.showOpenDialog(null)) == 0) {
                    File iFile = fc2.getSelectedFile();
                    AccessoryPanel.this.dotField.setText(iFile.getAbsolutePath());
                }
            }
        });
        filePanel.add(browseButton);
        JPanel panelDisplay = new JPanel();
        this.cbDot.setSelected(dotFileName != null);
        this.cbDot.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                AccessoryPanel.this.cbSize.setEnabled(AccessoryPanel.this.cbDot.isSelected());
                AccessoryPanel.this.rbDisplayFull.setEnabled(AccessoryPanel.this.cbDot.isSelected());
                AccessoryPanel.this.rbDisplaySimplified.setEnabled(AccessoryPanel.this.cbDot.isSelected());
                AccessoryPanel.this.rbDisplayMinimal.setEnabled(AccessoryPanel.this.cbDot.isSelected());
                AccessoryPanel.this.labelFileDot.setEnabled(AccessoryPanel.this.cbDot.isSelected());
                browseButton.setEnabled(AccessoryPanel.this.cbDot.isSelected());
                AccessoryPanel.this.dotField.setEnabled(AccessoryPanel.this.cbDot.isSelected());
            }
        });
        this.cbSize.setSelected(displaySize);
        panelDisplay.add(this.rbDisplayFull);
        panelDisplay.add(this.rbDisplaySimplified);
        panelDisplay.add(this.rbDisplayMinimal);
        panelDisplay.add(this.cbSize);
        ButtonGroup group3 = new ButtonGroup();
        group3.add(this.rbDisplayFull);
        group3.add(this.rbDisplaySimplified);
        group3.add(this.rbDisplayMinimal);
        this.rbDisplayFull.setEnabled(dotFileName != null);
        this.rbDisplayMinimal.setEnabled(dotFileName != null);
        this.rbDisplaySimplified.setEnabled(dotFileName != null);
        this.cbSize.setEnabled(dotFileName != null);
        this.labelFileDot.setEnabled(dotFileName != null);
        browseButton.setEnabled(dotFileName != null);
        this.dotField.setEnabled(dotFileName != null);
        switch (AccessoryPanel.$SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat()[format.ordinal()]) {
            case 2: {
                this.rbDisplayFull.setSelected(true);
                break;
            }
            case 1: {
                this.rbDisplaySimplified.setSelected(true);
                break;
            }
            case 3: {
                this.rbDisplayMinimal.setSelected(true);
            }
        }
        panelDot.setBorder(BorderFactory.createTitledBorder("GraphViz options"));
        panelDot.add(filePanel);
        panelDot.add(panelDisplay);
        this.add(panelDot);
        JPanel panelXml = new JPanel(new VerticalFlowLayout(0, 0, 0, true, false));
        JPanel filePanel2 = new JPanel();
        this.xmlField = new JTextField();
        if (outputFileName != null) {
            this.xmlField.setText(outputFileName);
        }
        this.xmlField.setPreferredSize(new Dimension(250, 20));
        filePanel2.add(this.labelFileXml);
        filePanel2.add(this.xmlField);
        final JButton browseButton2 = new JButton("...");
        browseButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal;
                JFileChooser fc2 = new JFileChooser();
                String path = AccessoryPanel.this.xmlField.getText();
                if (path != null && path.length() > 1) {
                    fc2.setCurrentDirectory(new File(path).getParentFile());
                }
                if ((returnVal = fc2.showOpenDialog(null)) == 0) {
                    File iFile = fc2.getSelectedFile();
                    AccessoryPanel.this.xmlField.setText(iFile.getAbsolutePath());
                }
            }
        });
        this.cbXml.setSelected(outputFileName != null);
        this.cbXml.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                AccessoryPanel.this.cbReduced.setEnabled(AccessoryPanel.this.cbXml.isSelected());
                AccessoryPanel.this.xmlField.setEnabled(AccessoryPanel.this.cbXml.isSelected());
                AccessoryPanel.this.labelFileXml.setEnabled(AccessoryPanel.this.cbXml.isSelected());
                browseButton2.setEnabled(AccessoryPanel.this.cbXml.isSelected());
            }
        });
        this.cbReduced.setEnabled(outputFileName != null);
        this.xmlField.setEnabled(outputFileName != null);
        this.labelFileXml.setEnabled(outputFileName != null);
        browseButton2.setEnabled(outputFileName != null);
        filePanel2.add(browseButton2);
        panelXml.add(this.cbXml);
        panelXml.add(filePanel2);
        this.cbReduced.setSelected(reduced);
        panelXml.add(this.cbReduced);
        panelXml.setBorder(BorderFactory.createTitledBorder("XML representation"));
        this.add(panelXml);
    }

    public String getAlgoName() {
        if (this.rbAres.isSelected()) {
            return "Ares";
        }
        if (this.rbCeres.isSelected()) {
            return "Ceres";
        }
        if (this.rbHermes.isSelected()) {
            return "Hermes";
        }
        if (this.rbPluton.isSelected()) {
            return "Pluton";
        }
        return null;
    }

    public String getOutputFileName() {
        return this.cbXml.isSelected() ? this.xmlField.getText() : null;
    }

    public String getDotFileName() {
        return this.cbDot.isSelected() ? this.dotField.getText() : null;
    }

    public IMySet.Impl getImpl() {
        if (this.rbBitSet.isSelected()) {
            return IMySet.Impl.BITSET;
        }
        if (this.rbHashSet.isSelected()) {
            return IMySet.Impl.HASHSET;
        }
        if (this.rbTIntHashSet.isSelected()) {
            return IMySet.Impl.TROVE_HASHSET;
        }
        return null;
    }

    public DotWriter.DisplayFormat getFormat() {
        if (this.rbDisplayFull.isSelected()) {
            return DotWriter.DisplayFormat.FULL;
        }
        if (this.rbDisplayMinimal.isSelected()) {
            return DotWriter.DisplayFormat.MINIMAL;
        }
        if (this.rbDisplaySimplified.isSelected()) {
            return DotWriter.DisplayFormat.REDUCED;
        }
        return null;
    }

    public boolean getDisplaySize() {
        return this.cbSize.isSelected();
    }

    public boolean getReduced() {
        return this.cbReduced.isSelected();
    }

    static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[IMySet.Impl.values().length];
        try {
            arrn[IMySet.Impl.BITSET.ordinal()] = 1;
        }
        catch (NoSuchFieldError v1) {}
        try {
            arrn[IMySet.Impl.HASHSET.ordinal()] = 2;
        }
        catch (NoSuchFieldError v2) {}
        try {
            arrn[IMySet.Impl.TROVE_HASHSET.ordinal()] = 3;
        }
        catch (NoSuchFieldError v3) {}
        $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl = arrn;
        return $SWITCH_TABLE$fr$lirmm$marel$gsh2$core$IMySet$Impl;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[DotWriter.DisplayFormat.values().length];
        try {
            arrn[DotWriter.DisplayFormat.FULL.ordinal()] = 2;
        }
        catch (NoSuchFieldError v1) {}
        try {
            arrn[DotWriter.DisplayFormat.MINIMAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError v2) {}
        try {
            arrn[DotWriter.DisplayFormat.REDUCED.ordinal()] = 1;
        }
        catch (NoSuchFieldError v3) {}
        $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat = arrn;
        return $SWITCH_TABLE$fr$lirmm$marel$gsh2$io$DotWriter$DisplayFormat;
    }

}

