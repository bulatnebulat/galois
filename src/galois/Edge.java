package galois;

public class Edge {
	private final Concept parent;
	private final Concept child;

	public Edge(Concept child, Concept parent) {
		this.parent = parent;
		this.child = child;
	}

	public Concept getParent() {
		return this.parent;
	}

	public Concept getChild() {
		return this.child;
	}

	public String toString() {
		return String.valueOf(this.parent.toString()) + " - " + this.child.toString();
	}
}
