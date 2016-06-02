package gui;

import gsh.types.Concept;
import gsh.types.Edge;
import gsh.types.GSH;
import gsh.types.ToGraph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.undo.UndoableEdit;
import jcornflower.ui.JCSwing;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.ParentMap;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;

import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

import galois.ReadCSV;

public class JGraphApplet
extends JApplet {
    private static final long serialVersionUID = 3256444702936019250L;
    private static final Dimension DEFAULT_SIZE = JCSwing.getScreenSize();
    private mxGraphModel jgAdapter;
    private mxGraphComponent graphComponent;
    private mxGraphOutline graphOutline;
    private List<Object> listOfPoints;
    private List<Concept> listOfConcepts;
    private List<galois.Concept> listOfConcepts2;

    public void setJGraph(GSH gsh) {
        //this.jgAdapter = new mxGraphModel<String, DefaultEdge>(ToGraph.fromGSH(gsh));
        mxGraph jgraph = new mxGraph();
        
        jgraph.setCellsEditable(false);
        jgraph.setAllowDanglingEdges(false);
        jgraph.setCellsDeletable(false);
        jgraph.setCellsDisconnectable(false);
        jgraph.setDropEnabled(false);
        jgraph.setConnectableEdges(false);
        jgraph.setCellsResizable(false);
        jgraph.setCellsBendable(false);
        jgraph.setCellsCloneable(false);
        jgraph.setHtmlLabels(true);
       
        Object parent = jgraph.getDefaultParent();

		jgraph.getModel().beginUpdate();
        this.adjustDisplaySettings(jgraph);
        try {
        this.resize(DEFAULT_SIZE);
        HashMap<Concept, Integer> levels = new HashMap<Concept, Integer>();
        for (Concept p : gsh.getConcepts()) {
            levels.put(p, p.getLevel());
        }
        int ymax = 0;
        HashMap<Integer, Integer> col = new HashMap<Integer, Integer>();
        for (Concept p2 : levels.keySet()) {
            int level = (Integer)levels.get(p2);
            int value = !col.containsKey(level) ? 1 : (Integer)col.get(level) + 1;
            col.put(level, value);
            if (level <= ymax) continue;
            ymax = level;
        }
        HashMap<Integer, Integer> counters = new HashMap<Integer, Integer>(col);
        listOfPoints = new ArrayList<Object>();
        listOfConcepts = new ArrayList<Concept>();
        for (Concept p3 : levels.keySet()) {
            int level = (Integer)levels.get(p3);
            int onlevel = (Integer)col.get(level);
            int x = JGraphApplet.DEFAULT_SIZE.width / (onlevel + 1) * (Integer)counters.get(level) - 70;
            int y = JGraphApplet.DEFAULT_SIZE.height / (ymax + 1) * (ymax - level + 1);
            counters.put(level, (Integer)counters.get(level) - 1);
            //this.positionAndResize(p3.toString(), x, y, p3.getWidth() * 8 + 15, p3.getHeight() * 30 + 10);
            listOfPoints.add(jgraph.insertVertex(parent, null, p3.toString(), x, y, p3.getWidth() * 8 + 15, p3.getHeight() * 30 + 10, "fillColor=#C0C0C0"));
            listOfConcepts.add(p3);
        }
        for (Edge p : gsh.getEdges()) {
        	jgraph.insertEdge(parent, null, "", listOfPoints.get(listOfConcepts.indexOf(p.getParent())), listOfPoints.get(listOfConcepts.indexOf(p.getChild())));           
        }
        } finally {
        	jgraph.getModel().endUpdate();
        }      
        graphComponent = new mxGraphComponent(jgraph); 
        graphComponent.setConnectable(false);
        graphComponent.getViewport().setOpaque(true);
        graphComponent.getViewport().setBackground(Color.WHITE);
        
        graphOutline = new mxGraphOutline(graphComponent);
        this.getContentPane().add(graphComponent);
        installZoomListener(); 
    }
    
    public void setJGraph(ReadCSV gsh) {
        //this.jgAdapter = new mxGraphModel<String, DefaultEdge>(ToGraph.fromGSH(gsh));
        mxGraph jgraph = new mxGraph();
        
        jgraph.setCellsEditable(false);
        jgraph.setAllowDanglingEdges(false);
        jgraph.setCellsDeletable(false);
        jgraph.setCellsDisconnectable(false);
        jgraph.setDropEnabled(false);
        jgraph.setConnectableEdges(false);
        jgraph.setCellsResizable(false);
        jgraph.setCellsBendable(false);
        jgraph.setCellsCloneable(false);
        jgraph.setHtmlLabels(true);
       
        Object parent = jgraph.getDefaultParent();

		jgraph.getModel().beginUpdate();
        this.adjustDisplaySettings(jgraph);
        try {
        this.resize(DEFAULT_SIZE);
        HashMap<galois.Concept, Integer> levels = new HashMap<galois.Concept, Integer>();
        for (galois.Concept p : gsh.getL()) {
            levels.put(p, p.getLevel());
        }
        int ymax = 0;
        HashMap<Integer, Integer> col = new HashMap<Integer, Integer>();
        for (galois.Concept p2 : levels.keySet()) {
            int level = (Integer)levels.get(p2);
            int value = !col.containsKey(level) ? 1 : (Integer)col.get(level) + 1;
            col.put(level, value);
            if (level <= ymax) continue;
            ymax = level;
        }
        HashMap<Integer, Integer> counters = new HashMap<Integer, Integer>(col);
        listOfPoints = new ArrayList<Object>();
        listOfConcepts2 = new ArrayList<galois.Concept>();
        for (galois.Concept p3 : levels.keySet()) {
            int level = (Integer)levels.get(p3);
            int onlevel = (Integer)col.get(level);
            int x = JGraphApplet.DEFAULT_SIZE.width / (onlevel + 1) * (Integer)counters.get(level) - 70;
            int y = JGraphApplet.DEFAULT_SIZE.height / (ymax + 1) * (ymax - level + 1);
            counters.put(level, (Integer)counters.get(level) - 1);
            //this.positionAndResize(p3.toString(), x, y, p3.getWidth() * 8 + 15, p3.getHeight() * 30 + 10);
            listOfPoints.add(jgraph.insertVertex(parent, null, p3.toString(gsh), x, y, p3.getWidth(gsh) * 8 + 15, p3.getHeight() * 30 + 10, "fillColor=#C0C0C0"));
            listOfConcepts2.add(p3);
        }
        for (galois.Edge p : gsh.getEdges()) {
        	jgraph.insertEdge(parent, null, "", listOfPoints.get(listOfConcepts2.indexOf(p.getParent())), listOfPoints.get(listOfConcepts2.indexOf(p.getChild())));           
        }
        } finally {
        	jgraph.getModel().endUpdate();
        }      
        graphComponent = new mxGraphComponent(jgraph); 
        graphComponent.setConnectable(false);
        graphComponent.getViewport().setOpaque(true);
        graphComponent.getViewport().setBackground(Color.WHITE);
        
        graphOutline = new mxGraphOutline(graphComponent);
        this.getContentPane().add(graphComponent);
        installZoomListener(); 
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("Galois' Sub-Hierarchy graph visualization");
        frame.setDefaultCloseOperation(2);
        frame.pack();
        frame.setVisible(true);
    }
    
    private void installZoomListener() {
    	MouseWheelListener wheelTracker = new MouseWheelListener()
		{
			/**
			 * 
			 */
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				if (e.getSource() instanceof mxGraphOutline
						|| e.isControlDown())
				{
					JGraphApplet.this.mouseWheelMoved(e);
				}
			}

		};

		// Handles mouse wheel events in the outline and graph component
		graphOutline.addMouseWheelListener(wheelTracker);
		graphComponent.addMouseWheelListener(wheelTracker);
    }
    
    private void adjustDisplaySettings(mxGraph jg) {    	
    }
    
   
   	protected void mouseWheelMoved(MouseWheelEvent e)
   	{
   		if (e.getWheelRotation() < 0)
   		{
   			graphComponent.zoomIn();
   		}
   		else
   		{
   			graphComponent.zoomOut();
   		}

   		/*status(mxResources.get("scale") + ": "
   				+ (int) (100 * graphComponent.getGraph().getView().getScale())
   				+ "%");*/
   	}

    public void positionAndResize(Object vertex, int x, int y, int width, int height) {
       /* DefaultGraphCell cell = this.jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D.Double newBounds = new Rectangle2D.Double(x, y, width, height);
        GraphConstants.setBounds(attr, newBounds);
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        this.jgAdapter.edit((Map)cellAttr, null, null, null);*/
    }
}

