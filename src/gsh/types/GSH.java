/*
 * Decompiled with CFR 0_114.
 */
package gsh.types;

import gsh.types.Concept;
import gsh.types.Edge;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.lirmm.marel.gsh2.core.IBinaryContext;
import fr.lirmm.marel.gsh2.core.MyBasicIterator;
import fr.lirmm.marel.gsh2.core.MyConcept;
import fr.lirmm.marel.gsh2.core.MyGSH;

public class GSH {
    private final List<Concept> concepts;
    private final List<Edge> edges = new ArrayList<Edge>();
    private IBinaryContext bc;
    private MyGSH gsh;
    

    public GSH(List<Concept> concepts) {
        this.concepts = concepts;
    }
    
    public GSH(MyGSH gsh, IBinaryContext bc) {
    	this.bc = bc;
    	this.gsh = gsh;
    	this.concepts = new ArrayList<Concept>();
    	convertGSH(gsh, bc);
    }

    private void convertGSH(MyGSH gsh, IBinaryContext bc) {
    	convertConcepts();
		createEdges();
	}
    
    private void convertConcepts() {
    	MyConcept c;
        MyBasicIterator it = gsh.getBasicIterator();
		while (it.hasNext()) {
			c = it.next();
			List<String> extents = new ArrayList<String>();
			List<String> intents = new ArrayList<String>();
			Iterator<Integer> it2;
			it2 = c.getReducedIntent().iterator();
			while (it2.hasNext()) {
				intents.add(String.valueOf(bc.getAttributeName(it2.next())));
			}

			it2 = c.getReducedExtent().iterator();
			while (it2.hasNext()) {
				extents.add(String.valueOf(bc.getObjectName(it2.next())));
			}
			Concept con = new Concept(extents, intents, false, NamingConvention.eiSimple);
			con.setLevel(c.getExtent().cardinality());
			concepts.add(con);
		}
    }
    
    private void createEdges() {
    	MyConcept c;
    	MyBasicIterator it = gsh.getBasicIterator();
        while (it.hasNext()) {
            c = it.next();
            for (MyConcept child : c.getUpperCover().values()) {
            	List<String> extents = new ArrayList<String>();
    			List<String> intents = new ArrayList<String>();
    			List<String> childExtents = new ArrayList<String>();
    			List<String> childIntents = new ArrayList<String>();
    			Iterator<Integer> it2;
    			it2 = c.getReducedIntent().iterator();
    			while (it2.hasNext()) {
    				intents.add(String.valueOf(bc.getAttributeName(it2.next())));
    			}

    			it2 = c.getReducedExtent().iterator();
    			while (it2.hasNext()) {
    				extents.add(String.valueOf(bc.getObjectName(it2.next())));
    			}
    			it2 = child.getReducedIntent().iterator();
    			while (it2.hasNext()) {
    				childIntents.add(String.valueOf(bc.getAttributeName(it2.next())));
    			}

    			it2 = child.getReducedExtent().iterator();
    			while (it2.hasNext()) {
    				childExtents.add(String.valueOf(bc.getObjectName(it2.next())));
    			}    
    			Concept parentCon = new Concept(extents, intents, false, NamingConvention.eiSimple);
    			parentCon.setLevel(c.getExtent().cardinality());
    			Concept childCon =  new Concept(childExtents, childIntents, false, NamingConvention.eiSimple);
    			childCon.setLevel(child.getExtent().cardinality());
                edges.add(new Edge(parentCon, childCon));                
            }
        }
    }

	public GSH() {
        this.concepts = new ArrayList<Concept>();
    }

    public List<Concept> getConcepts() {
        return this.concepts;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public boolean containsEdge(Edge edge) {
        for (Edge p : this.edges) {
            if (!p.getChild().toString().equals(edge.getChild().toString()) || !p.getParent().toString().equals(edge.getParent().toString())) continue;
            return true;
        }
        return false;
    }
}

