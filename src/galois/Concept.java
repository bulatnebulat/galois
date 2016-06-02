package galois;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jcornflower.TypeOperations.JCLists;

public class Concept {

	private List<Integer> objects;
	private List<Integer> attributes;
	private List<Integer> originObj;
	private List<Integer> originAttr;	
	private int level;
		
	public Concept() {
		objects = new ArrayList<Integer>();
		attributes = new ArrayList<Integer>();
		originAttr = new ArrayList<Integer>(); 
		originObj = new ArrayList<Integer>(); 
	}
	
	public Concept(List<Integer> objects, List<Integer> attributes) {
		this.objects = objects;
		this.attributes = attributes;
	}
	
	public void addAttr(int attr) {
		attributes.add(attr);
	}
	
	public void addObj(int obj) {
		objects.add(obj);
	}
	
	public void addOriginAttr(int attr) {
		originAttr.add(attr);
	}
	
	public void addOriginObj(int obj) {
		originObj.add(obj);
	}
	
	public List<Integer> getAttr() {
		return attributes;
	}
	
	public List<Integer> getObj() {
		return objects;
	}
	
	public List<Integer> getOriginObj() {
		return originObj;
	}

	public void setOriginObj(List<Integer> originObj) {
		this.originObj = originObj;
	}

	public List<Integer> getOriginAttr() {
		return originAttr;
	}

	public void setOriginAttr(List<Integer> originAttr) {
		this.originAttr = originAttr;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getWidth(ReadCSV csv) {
        int length;
        int result = 0;
        List<String> labelsObj = getListOfObjStringNames(originObj, csv);
        List<String> labelsAttr = getListOfAttrStringNames(originAttr, csv);
        Iterator<String> p = labelsObj.iterator();
        while (p.hasNext()) {
            length = p.next().length();
            if (length <= result) continue;
            result = length;
        }
        p = labelsAttr.iterator();
        while (p.hasNext()) {
            length = p.next().length();
            if (length <= result) continue;
            result = length;
        }
        return result;
    }

    public int getHeight() {
        return this.getOriginObj().size() + this.getOriginAttr().size();
    }
    
    private String htmlName(ReadCSV csv) {
        ArrayList<String> labels = new ArrayList<String>();
        List<String> labelsObj = getListOfObjStringNames(originObj, csv);
        List<String> labelsAttr = getListOfAttrStringNames(originAttr, csv);
        if (this.attributes.size() > 0) {
            labels.add(JCLists.join(labelsAttr, "<br>"));
        }
        if (this.objects.size() > 0) {
            labels.add("<i>" + JCLists.join(labelsObj, "<br>") + "</i>");
        }
        String result = "<html><b>" + JCLists.join(labels, "<br><br>") + "</b></html>";
        return result;
    }
    
    public List<String> getListOfObjStringNames(List<Integer> list, ReadCSV csv) {
		List<String> res = new ArrayList<String>();
    	for(int i = 0; i < list.size(); i++) {
    		res.add(csv.getObjNames().get(list.get(i)));
    	}
    	return res;
    }
    
    public List<String> getListOfAttrStringNames(List<Integer> list, ReadCSV csv) {
		List<String> res = new ArrayList<String>();
    	for(int i = 0; i < list.size(); i++) {
    		res.add(csv.getAttrNames().get(list.get(i)));
    	}
    	return res;
    }
    
    public String toString(ReadCSV csv) {
        return this.htmlName(csv);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this.toString().equals(((Concept)obj).toString()) && this.level == ((Concept)obj).level) {
        	return true;
        }
        return false;
    }
}
