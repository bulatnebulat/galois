package gui;

import gsh.GSHBuilder;
import gsh.algorithm.GSHAlgorithm;
import gsh.algorithm.pluton.Pluton;
import gsh.types.GSH;
import gui.ContextController;
import gui.ContextTable;
import gui.JGraphApplet;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jcornflower.matrix.D2DUtil;
import jcornflower.matrix.Double2D;
import jcornflower.ui.JCSwing;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.DOTImporter;
import org.jgrapht.ext.EdgeNameProvider;
import org.jgrapht.ext.EdgeProvider;
import org.jgrapht.ext.VertexNameProvider;
import org.jgrapht.ext.VertexProvider;
import org.jgrapht.graph.DefaultEdge;

import fr.lirmm.marel.gsh2.algorithm.Ares;
import fr.lirmm.marel.gsh2.algorithm.Ceres;
import fr.lirmm.marel.gsh2.core.IBinaryContext;
import fr.lirmm.marel.gsh2.core.MyGSH;
import fr.lirmm.marel.gsh2.io.DotWriter;
import fr.lirmm.marel.gsh2.io.DotWriter.DisplayFormat;
import io.MyCSVReader;
import fr.lirmm.marel.gsh2.util.Chrono;
import galois.ReadCSV;
import javax.swing.ScrollPaneConstants;


public class GSHBStart extends JFrame {
	
	public String curFile;
	public ReadCSV csv; 
	private static final long serialVersionUID = -2799099279899362125L;
    private ContextController contextController;
    private JDialog aboutJDialog;
    private JTextArea aboutJTextArea;
    private JLabel attributesJLabel;
    private JLabel attributesJLabel1;
    private JTextField attributesJTextField_crea;
    private JTextField attributesJTextField_rand;
    private JScrollPane contextJScrollPane;
    private JTable contextJTable;
    private JDialog createNewContextJDialog;
    private JButton createNewContextSubmitJButton;
    private JMenuItem dotexportJMenuItem;
    private JMenu editJMenu;
    private JLabel errorJLabel_crea;
    private JLabel errorJLabel_rand;
    private JMenuItem exitJMenuItem;
    private JFileChooser exportToXmlJFileChooser;
    private JDialog generateRandomContextJDialog;
    private JButton generateRandomSubmitJButton;
    private JMenu gshJMenu;
    private JDialog helpJDialog;
    private JMenuItem importJMenuItem;
    private JFileChooser importxmlJFileChooser;
    private JFileChooser importcsvJFileChooser;
    private JMenuItem insertContextJMenuItem;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    private JLabel numberofJLabel;
    private JLabel objectsJLabel;
    private JLabel objectsJLabel1;
    private JTextField objectsJTextField_crea;
    private JTextField objectsJTextField_rand;
    private JMenuItem randomJMenuItem;
    private JLabel relationsJLabel;
    private JTextField relationsJTextField;
    private JMenuBar topJMenuBar;
    private JMenuItem xmlexportJMenuItem;
    private JButton btnAres = new JButton("Ares");
    private JScrollPane consoleJScrollPane;
    private JTextArea actionsConsole;

    public GSHBStart() {
        this.contextController = new ContextController(this);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                GSHBStart.this.exitProcedure();
            }
        });
        this.initComponents();
        JCSwing.display(this);
    }

    private void exitProcedure() {
        this.pullThePlug();
    }

    private void pullThePlug() {
        WindowEvent wev = new WindowEvent(this, 201);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }

    private void initComponents() {
        this.importxmlJFileChooser = new JFileChooser();
        this.importcsvJFileChooser = new JFileChooser();
        this.helpJDialog = new JDialog();
        this.jScrollPane1 = new JScrollPane();
        this.jTextArea1 = new JTextArea();
        this.aboutJDialog = new JDialog();
        this.aboutJTextArea = new JTextArea();
        this.createNewContextJDialog = new JDialog();
        this.objectsJTextField_crea = new JTextField();
        this.attributesJTextField_crea = new JTextField();
        this.objectsJLabel = new JLabel();
        this.attributesJLabel = new JLabel();
        this.createNewContextSubmitJButton = new JButton();
        this.errorJLabel_crea = new JLabel();
        this.generateRandomContextJDialog = new JDialog();
        this.objectsJTextField_rand = new JTextField();
        this.attributesJTextField_rand = new JTextField();
        this.objectsJLabel1 = new JLabel();
        this.attributesJLabel1 = new JLabel();
        this.generateRandomSubmitJButton = new JButton();
        this.relationsJTextField = new JTextField();
        this.numberofJLabel = new JLabel();
        this.relationsJLabel = new JLabel();
        this.errorJLabel_rand = new JLabel();
        this.exportToXmlJFileChooser = new JFileChooser();
        this.contextJScrollPane = new JScrollPane();
        this.contextJTable = new JTable();
        this.topJMenuBar = new JMenuBar();
        this.gshJMenu = new JMenu();
        this.dotexportJMenuItem = new JMenuItem();
        this.importxmlJFileChooser.setDialogTitle("Select a xml file that contains a context");
        this.importcsvJFileChooser.setDialogTitle("Select a csv file that contains a context");
        this.helpJDialog.setTitle("Help contents");
        this.helpJDialog.setName("helpJDialog");
        this.jTextArea1.setColumns(20);
        this.jTextArea1.setEditable(false);
        this.jTextArea1.setFont(new Font("SansSerif", 0, 12));
        this.jTextArea1.setRows(5);
        this.jTextArea1.setText("What do menu buttons do?\n\nCreate new Context\nContext is a two-dimensional array of true/false values, which is presented as a table with textual indexes.\nAs a reference to formal context of FCA (Formal Concept Analysis) the cell index is called attribute and the row index is called object. For example, if we look at database \ntables and use cases of a program, then tables could be attributes and use cases could be objects. Binary relations represent CRUD values (although GSHB retains \ndifferent values in relations of a context, they are represented as X or no-X).  Attribute and object index\u2019s length must be between 1 and 100.\n\n\nGenerate random context\nGenerates a binary context with given dimensions and relation density. Relation density is the number of true (X) values in a context and it must be between 0 and a*o. \nAttribute and object index\u2019s length must be between 1 and 100.\n\n\nImport from XML\nContext is imported from an xml file. Examples are provided in \u201cexamples\u201d folder.  In case of a relation between an attribute and an object, it is represented as X in the table. \nAll attribute and object indexes are imported. Notice, that objects are called properties and it\u2019s because Simple2.6.2 prohibits using \u201cobject\u201d as a variable name. \nXML parsing is done by Simple.\n\n\nExport to XML\nContext can be exported into an xml file. All attribute and object indexes are exported, whether they have relations or not. Only relations that are true are exported and \nare given a value of 1.0 in real number. This could be a source of problems, when the context was imported with different real numbers and then exported. As a result, \nold values are lost. Notice, that objects are called properties and it\u2019s because Simple2.6.2 prohibits using \u201cobject\u201d as a variable name. XML parsing is done by Simple.\n\n\nShow GSH visual\nThe Galois sub-hierarchy (GSH) of a concept lattice is the partially ordered set of elements X * Y,X union Y, Y not null, such that there exists a concept where X is the \nset of objects introduced by this concept and Y is the set of properties introduced by this concept. The ordering of the elements in the GSH is the same as in the lattice. \n\nIf selected, then the Pluton algorithm starts to build GSH out of the provided context. With larger contexts with high density it might take a while, but generally not more \nthan a minute. Concept finder is single-threaded and edge finder is multi-threaded. Concept lattice is undirected un-weighed graph. Object names are in italic format. \nConcepts and edges can be dragged around on the canvas, thus improving visual value. By double-clicking on a concept, its html code becomes editable. \nConcept\u2019s box size is alterable as well. The positioning of concepts is an area of future work. \n\n\nExport GSH to DOT file\nFirst Pluton algorithm builds GSH graph out of the provided context. Then, the graph is saved in DOT language (http://en.wikipedia.org/wiki/DOT_language) to a file \ngiven by the user.\n\n\nIf you need more functionality, you are more than welcome to edit the source code as GSHB is free software or request changes on the project\u2019s homepage in Sourceforge.\n");
        this.jScrollPane1.setViewportView(this.jTextArea1);
        GroupLayout helpJDialogLayout = new GroupLayout(this.helpJDialog.getContentPane());
        this.helpJDialog.getContentPane().setLayout(helpJDialogLayout);
        helpJDialogLayout.setHorizontalGroup(helpJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(helpJDialogLayout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 976, 32767).addContainerGap()));
        helpJDialogLayout.setVerticalGroup(helpJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(helpJDialogLayout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 486, 32767).addContainerGap()));
        this.aboutJDialog.setTitle("About");
        this.aboutJDialog.setName("aboutJDialog");
        this.aboutJTextArea.setColumns(20);
        this.aboutJTextArea.setEditable(false);
        this.aboutJTextArea.setFont(new Font("SansSerif", 0, 12));
        this.aboutJTextArea.setRows(5);
        this.aboutJTextArea.setText("GSH builder is a free Galois Sub-Hierarchy visualization tool. \nThe algorithm for calculating GSH is called Pluton and the present \nJava realization is based on two articles.\n\n\"Efficiently computing a linear extension of the sub-hierarchy of a \nConcept Lattice\" by Anne Berry, Marianne Huchard,\nRoss M. McConnell, Alain Sigayret and Jeremy P. Spinrad.  \n\n\"Performances of Galois Sub-hierarchy-building algorithms\" by \nGabriela Arevalo, Anne Berry, Marianne Huchard,\nGuillaume Perrot and Alain Sigayret\n\nIn you're in need of other GSH algorithms or their realizations in C++, \nyou might want to investigate a project called Galicia on sourceforge.\n\nThis program is free software and is licensed under GNU General Public License \nas published by the Free Software Foundation.\n\nFor more information, please visit http://gshbuilder.sourceforge.net/\n\nVersion 0.1\nMarch 2012\nCopyright (C) Kristo Aun");
        GroupLayout aboutJDialogLayout = new GroupLayout(this.aboutJDialog.getContentPane());
        this.aboutJDialog.getContentPane().setLayout(aboutJDialogLayout);
        aboutJDialogLayout.setHorizontalGroup(aboutJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(aboutJDialogLayout.createSequentialGroup().addGap(0, 11, 32767).addComponent(this.aboutJTextArea, -2, 472, -2).addGap(0, 11, 32767)));
        aboutJDialogLayout.setVerticalGroup(aboutJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(aboutJDialogLayout.createSequentialGroup().addGap(0, 12, 32767).addComponent(this.aboutJTextArea, -2, 410, -2).addGap(0, 12, 32767)));
        this.createNewContextJDialog.setTitle("Create new context");
        this.objectsJLabel.setText("Number of objects (rows)");
        this.attributesJLabel.setText("Number of attributes (cells)");
        this.createNewContextSubmitJButton.setText("Create");
        this.createNewContextSubmitJButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                GSHBStart.this.createNewContextSubmitJButtonActionPerformed(evt);
            }
        });
        GroupLayout createNewContextJDialogLayout = new GroupLayout(this.createNewContextJDialog.getContentPane());
        this.createNewContextJDialog.getContentPane().setLayout(createNewContextJDialogLayout);
        createNewContextJDialogLayout.setHorizontalGroup(createNewContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(createNewContextJDialogLayout.createSequentialGroup().addContainerGap().addGroup(createNewContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.errorJLabel_crea, -1, -1, 32767).addGroup(createNewContextJDialogLayout.createSequentialGroup().addGroup(createNewContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.attributesJLabel).addComponent(this.objectsJLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(createNewContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.attributesJTextField_crea, -1, 56, 32767).addComponent(this.objectsJTextField_crea)).addGap(0, 0, 32767)).addComponent(this.createNewContextSubmitJButton, -1, -1, 32767)).addContainerGap()));
        createNewContextJDialogLayout.setVerticalGroup(createNewContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(createNewContextJDialogLayout.createSequentialGroup().addContainerGap().addGroup(createNewContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.objectsJTextField_crea, -2, -1, -2).addComponent(this.objectsJLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(createNewContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.attributesJTextField_crea, -2, -1, -2).addComponent(this.attributesJLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.errorJLabel_crea, -1, 10, 32767).addGap(21, 21, 21).addComponent(this.createNewContextSubmitJButton).addContainerGap()));
        this.generateRandomContextJDialog.setTitle("Generate random context");
        this.objectsJLabel1.setText("- objects (rows)");
        this.attributesJLabel1.setText("- attributes (cells)");
        this.generateRandomSubmitJButton.setText("Create");
        this.generateRandomSubmitJButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                GSHBStart.this.generateRandomSubmitJButtonActionPerformed(evt);
            }
        });
        this.numberofJLabel.setText("Number of");
        this.relationsJLabel.setText("- relations");
        GroupLayout generateRandomContextJDialogLayout = new GroupLayout(this.generateRandomContextJDialog.getContentPane());
        this.generateRandomContextJDialog.getContentPane().setLayout(generateRandomContextJDialogLayout);
        generateRandomContextJDialogLayout.setHorizontalGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(generateRandomContextJDialogLayout.createSequentialGroup().addContainerGap().addGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(generateRandomContextJDialogLayout.createSequentialGroup().addComponent(this.numberofJLabel).addContainerGap()).addGroup(generateRandomContextJDialogLayout.createSequentialGroup().addGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(generateRandomContextJDialogLayout.createSequentialGroup().addGap(10, 10, 10).addGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.errorJLabel_rand, -2, 205, -2).addGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(generateRandomContextJDialogLayout.createSequentialGroup().addComponent(this.attributesJLabel1).addGap(18, 18, 18).addComponent(this.attributesJTextField_rand)).addGroup(GroupLayout.Alignment.LEADING, generateRandomContextJDialogLayout.createSequentialGroup().addGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.objectsJLabel1).addComponent(this.relationsJLabel)).addGap(27, 27, 27).addGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.objectsJTextField_rand, -2, 47, -2).addComponent(this.relationsJTextField, -2, 47, -2)))))).addComponent(this.generateRandomSubmitJButton, -2, 160, -2)).addGap(0, 20, 32767)))));
        generateRandomContextJDialogLayout.setVerticalGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(generateRandomContextJDialogLayout.createSequentialGroup().addContainerGap().addComponent(this.numberofJLabel).addGap(7, 7, 7).addGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.attributesJLabel1).addComponent(this.attributesJTextField_rand, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.objectsJTextField_rand, -2, -1, -2).addComponent(this.objectsJLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(generateRandomContextJDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.relationsJLabel).addComponent(this.relationsJTextField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.errorJLabel_rand, -1, 27, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.generateRandomSubmitJButton).addContainerGap()));
        this.exportToXmlJFileChooser.setDialogTitle("Export context to xml file");
        this.setDefaultCloseOperation(3);
        this.setTitle("GSH Builder 1.0");
        this.setName("gallonautJFrame");
        this.contextJScrollPane.setViewportView(this.contextJTable);
        this.editJMenu = new JMenu();
        this.insertContextJMenuItem = new JMenuItem();
        this.randomJMenuItem = new JMenuItem();
        this.importJMenuItem = new JMenuItem();
        this.xmlexportJMenuItem = new JMenuItem();
        this.editJMenu.setText("File");
        this.insertContextJMenuItem.setAccelerator(KeyStroke.getKeyStroke(78, 2));
        this.insertContextJMenuItem.setText("Create new context");
        this.insertContextJMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                GSHBStart.this.insertContextJMenuItemActionPerformed(evt);
            }
        });
        this.editJMenu.add(this.insertContextJMenuItem);
        this.randomJMenuItem.setText("Generate random context");
        this.randomJMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                GSHBStart.this.randomJMenuItemActionPerformed(evt);
            }
        });
        this.editJMenu.add(this.randomJMenuItem);
        this.importJMenuItem.setAccelerator(KeyStroke.getKeyStroke(73, 2));
        this.importJMenuItem.setText("Import from CSV");
        this.importJMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                GSHBStart.this.importJMenuItemActionPerformed(evt);
            }
        });
        this.editJMenu.add(this.importJMenuItem);
        this.xmlexportJMenuItem.setText("Export to CSV");
        this.xmlexportJMenuItem.setEnabled(false);
        this.xmlexportJMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                GSHBStart.this.xmlexportJMenuItemActionPerformed(evt);
            }
        });
        this.editJMenu.add(this.xmlexportJMenuItem);
        this.topJMenuBar.add(this.editJMenu);
        this.exitJMenuItem = new JMenuItem();
        editJMenu.add(exitJMenuItem);
        this.exitJMenuItem.setText("Exit");
        this.exitJMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                GSHBStart.this.exitJMenuItemActionPerformed(evt);
            }
        });
        this.gshJMenu.setText("GSH");
        this.gshJMenu.setEnabled(false);
        this.dotexportJMenuItem.setAccelerator(KeyStroke.getKeyStroke(69, 2));
        this.dotexportJMenuItem.setText("Export GSH to DOT file");
        this.dotexportJMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                GSHBStart.this.dotexportJMenuItemActionPerformed(evt);
            }
        });
        this.gshJMenu.add(this.dotexportJMenuItem);
        this.topJMenuBar.add(this.gshJMenu);
        this.setJMenuBar(this.topJMenuBar);
        
        JButton btnOpen = new JButton("Open");
        btnOpen.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
        			GSHBStart.this.importOpenBtnActionPerformed(arg0);
        			initContextJTable(csv);
				}
				catch(Exception ex) {
					
				}
        	}
        });
        
        JButton btnBuild = new JButton("Build");
        
        JButton btnPluton = new JButton("Pluton");
        btnPluton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		if (gshJMenu.isEnabled()) {
					GSHBStart.this.visualgshJMenuItemActionPerformed(evt);
				} else {
					actionsConsole.setText(actionsConsole.getText() + "\nEmpty context!");
				}
        	}
        });
        
        JButton btnCeres = new JButton("Ceres");
        btnCeres.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if (gshJMenu.isEnabled()) {
					try {
						IBinaryContext bc = new MyCSVReader(new BufferedReader(new FileReader(curFile))).readBinaryContext();
						long start, end, res;
						start = System.nanoTime();
						Ceres cer = new Ceres(bc);
						cer.exec();
						/*cer.getResult();
						String dotFileName = "1.dot";
						int pos = curFile.lastIndexOf(".");
						if (pos > 0) {
							dotFileName = curFile.substring(0, pos) + ".dot";
						}

						BufferedWriter buff = new BufferedWriter(new FileWriter(new File(dotFileName)));
						DotWriter dw = new DotWriter(buff, (MyGSH) cer.getResult(), bc, DisplayFormat.REDUCED, false);
						dw.write();*/
						JGraphApplet app = new JGraphApplet();
		                app.setJGraph(new GSH((MyGSH) cer.getResult(), bc));
		                end = System.nanoTime();
		                res = end - start;		                
		                app.run();
		                actionsConsole.setText(actionsConsole.getText() + "\nAlgorithm: Ceres\nObject amount:" +  bc.getObjectCount() + "\nAttributes amount:" + bc.getAttributeCount() + "\nTime:" + String.format("%,12d", res) + " ns" );
				       /* VertexProvider<String> vertexProvider = new VertexProvider<String>();
				        EdgeProvider<String, DefaultEdge> edgeProvider = new EdgeProvider<String, DefaultEdge>(); 
						DOTImporter<String, DefaultEdge> di = new DOTImporter<String, DefaultEdge>(vertexProvider, edgeProvider);*/
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					actionsConsole.setText(actionsConsole.getText() + "\nEmpty context!");
				}
        	}
        });              
        
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		actionsConsole.setText("");
        	}
        });
        
        consoleJScrollPane = new JScrollPane();
        consoleJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(consoleJScrollPane, GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
        				.addComponent(contextJScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(btnAres, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(btnCeres, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(btnPluton, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 551, Short.MAX_VALUE)
        					.addComponent(btnClear)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnBuild)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnOpen)))
        			.addContainerGap())
        );
        btnAres.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if (gshJMenu.isEnabled()) {
					try {
						IBinaryContext bc = new MyCSVReader(new BufferedReader(new FileReader(curFile))).readBinaryContext();
						long start, end, res;
						start = System.nanoTime();
						Ares ares = new Ares(bc);
						ares.exec();
						ares.getResult();
						/*String dotFileName = "1.dot";
						int pos = curFile.lastIndexOf(".");
						if (pos > 0) {
							dotFileName = curFile.substring(0, pos) + ".dot";
						}

						BufferedWriter buff = new BufferedWriter(new FileWriter(new File(dotFileName)));
						DotWriter dw = new DotWriter(buff, (MyGSH) ares.getResult(), bc, DisplayFormat.REDUCED, false);
						dw.write();*/
						JGraphApplet app = new JGraphApplet();
		                app.setJGraph(new GSH((MyGSH) ares.getResult(), bc));
		                end = System.nanoTime();
		                res = end - start;
		                app.run();
		                actionsConsole.setText(actionsConsole.getText() + "\nAlgorithm: Ares\nObject amount:" +  bc.getObjectCount() + "\nAttributes amount:" + bc.getAttributeCount() + "\nTime:" + String.format("%,12d", res) + " ns" );
				       /* VertexProvider<String> vertexProvider = new VertexProvider<String>();
				        EdgeProvider<String, DefaultEdge> edgeProvider = new EdgeProvider<String, DefaultEdge>(); 
						DOTImporter<String, DefaultEdge> di = new DOTImporter<String, DefaultEdge>(vertexProvider, edgeProvider);*/
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					actionsConsole.setText(actionsConsole.getText() + "\nEmpty context!");
				}
        	}
        });
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(contextJScrollPane, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(consoleJScrollPane, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnOpen)
        				.addComponent(btnAres)
        				.addComponent(btnPluton)
        				.addComponent(btnCeres)
        				.addComponent(btnBuild)
        				.addComponent(btnClear))
        			.addGap(23))
        );
        
        actionsConsole = new JTextArea();
        actionsConsole.setEditable(false);
        consoleJScrollPane.setViewportView(actionsConsole);
        this.getContentPane().setLayout(layout);
        this.pack();
    }

    public JFileChooser getImportxmlJFileChooser() {
        return this.importxmlJFileChooser;
    }
    
    public JFileChooser getImportcsvJFileChooser() {
        return this.importcsvJFileChooser;
    }

    private void visualgshJMenuItemActionPerformed(ActionEvent evt) {
        if (this.contextJTable instanceof ContextTable) {
            try {
            	long start, end, res;
            	start = System.nanoTime();
                Double2D d2d = D2DUtil.getContextFromTable(this.contextJTable);
                GSHBuilder gshf = new GSHBuilder(new Pluton());
                GSH gsh = gshf.generate(d2d, false);
                JGraphApplet app = new JGraphApplet();
                app.setJGraph(gsh);
                end = System.nanoTime();
                res = end - start;
                actionsConsole.setText(actionsConsole.getText() + "\nAlgorithm: Pluton\nObject amount:" +  d2d.getObjnames().size() + "\nAttributes amount:" + d2d.getAttrnames().size() + "\nTime:" + String.format("%,12d", res) + " ns" );
                app.run();
            }
            catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(GSHBStart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void exitJMenuItemActionPerformed(ActionEvent evt) {
        this.exitProcedure();
    }

    private void dotexportJMenuItemActionPerformed(ActionEvent evt) {
        this.contextController.exportGSHToFile(curFile);        
    }

    private void insertContextJMenuItemActionPerformed(ActionEvent evt) {
        JCSwing.display(this.createNewContextJDialog);
    }

    private void randomJMenuItemActionPerformed(ActionEvent evt) {
        JCSwing.display(this.generateRandomContextJDialog);
    }

    private void importJMenuItemActionPerformed(ActionEvent evt) {
        this.contextController.importFromXML();
    }
    
    private void importOpenBtnActionPerformed(ActionEvent evt) {
        csv = this.contextController.importFromCSV(csv, this);
    }

    public void initContextJTable(Double2D d2d) {
        this.contextJTable = new ContextTable(d2d);
        this.contextJScrollPane.setViewportView(this.contextJTable);
        this.allowExportAndGSH(true);
    }
    
    public void initContextJTable(ReadCSV csv) {
        this.contextJTable = new ContextTable(csv);
        this.contextJScrollPane.setViewportView(this.contextJTable);
        this.allowExportAndGSH(true);
    }

    private void xmlexportJMenuItemActionPerformed(ActionEvent evt) {
        this.contextController.exportContextToXML();
    }

    private void allowExportAndGSH(boolean enabled) {
        this.gshJMenu.setEnabled(enabled);
        this.xmlexportJMenuItem.setEnabled(enabled);
    }

    private void createNewContextSubmitJButtonActionPerformed(ActionEvent evt) {
        try {
            Integer objSize = Integer.parseInt(this.objectsJTextField_crea.getText());
            Integer attrSize = Integer.parseInt(this.attributesJTextField_crea.getText());
            if (objSize >= 1 && objSize <= 100 && attrSize >= 1 && attrSize <= 100) {
                this.createNewContextJDialog.setVisible(false);
                this.initContextJTable(D2DUtil.getEmptyContext(objSize, attrSize));
            } else {
                this.errorJLabel_crea.setText("Dimensions must be between 1 and 100");
            }
        }
        catch (NumberFormatException e) {
            this.errorJLabel_crea.setText("Dimensions must be between 1 and 100");
        }
    }

    private void generateRandomSubmitJButtonActionPerformed(ActionEvent evt) {
        try {
            Integer objSize = Integer.parseInt(this.objectsJTextField_rand.getText());
            Integer attrSize = Integer.parseInt(this.attributesJTextField_rand.getText());
            Integer relations = Integer.parseInt(this.relationsJTextField.getText());
            if (relations >= 0 && relations <= objSize * attrSize) {
                if (objSize >= 1 && objSize <= 100 && attrSize >= 1 && attrSize <= 100) {
                    this.generateRandomContextJDialog.setVisible(false);
                    this.initContextJTable(D2DUtil.getRandomContext(objSize, attrSize, relations));
                }
            } else {
                this.errorJLabel_rand.setText("Relation count must be between 0 and " + objSize * attrSize);
            }
        }
        catch (NumberFormatException e) {
            this.errorJLabel_rand.setText("Dimensions must be between 1 and 100");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.LookAndFeelInfo[] arrlookAndFeelInfo = UIManager.getInstalledLookAndFeels();
            int n = arrlookAndFeelInfo.length;
            int n2 = 0;
            while (n2 < n) {
                UIManager.LookAndFeelInfo info = arrlookAndFeelInfo[n2];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                ++n2;
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GSHBStart.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                new GSHBStart().setVisible(true);
            }
        });
    }

    public JTable getContextJTable() {
        return this.contextJTable;
    }
    
    public JTextArea getActionsConsole() {
    	return this.actionsConsole;
    }
}
