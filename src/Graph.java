import java.util.ArrayList;
import java.util.Collections;

public class Graph {

	ArrayList<Node>[] nodes;
	ArrayList<Loop> loops = new ArrayList<>();


	private boolean containLoop (Loop loop) {
		for (Loop lp : loops) {
			if (lp.isEqualTo(loop))
				return true;
		}
		return false;
	}

	Graph (int size) {
		nodes = new ArrayList[size];
		for (int i = 0; i < size; ++i) {
			nodes[i] = new ArrayList<Node>();
		}

	}

	private ArrayList<Path> forwardPaths = new ArrayList<>();
	private ArrayList<Integer> currentForwardPath = new ArrayList<>();
	private ArrayList<Integer> currentLoop = new ArrayList<>();



	void printForwardPaths () {
		findFPathes(0);
		for (Path fp: forwardPaths) {
			System.out.println(fp.nodes);
		}
	}



	void findFPathes (int s) {
		if (s == nodes.length-1) {
			currentForwardPath.add(s);
			// print
			//System.out.println(currentForwardPath);
			ArrayList<Integer> nodes = new ArrayList<>();
			for (int node: currentForwardPath) {
				nodes.add(node);
			}
			Path path = new Path(nodes);
			forwardPaths.add(path);
			currentForwardPath.remove(currentForwardPath.size()-1);
			return;
		}
		currentForwardPath.add(s);
		for (Node u: nodes[s]) {
			if (s < u.to) {
				findFPathes(u.to);
			}
		}
		currentForwardPath.remove(currentForwardPath.size()-1);
	}


	void findLoops (int s) {
		if (currentLoop.contains(s)) {
			// there is a loop
			ArrayList<Integer> nodes = new ArrayList<>();
			for (int i = currentLoop.size()-1; i >= 0 && currentLoop.get(i) != s; i--) {
				nodes.add(currentLoop.get(i));
			}
			nodes.add(s);
			Collections.reverse(nodes);
			//nodes.add(s);
			Loop loop = new Loop(nodes);
			if (containLoop(loop)) {
				return;
			} else {
				loops.add(loop);
				return;
			}
		}
		currentLoop.add(s);
		for (Node u: nodes[s]) {
			findLoops(u.to);
		}
		currentLoop.remove(currentLoop.size()-1);
	}

	void printLoops () {
		findLoops(0);
		for (Loop loop: loops) {
			System.out.println(loop.nodes);
			//System.out.println(loop.hashValue);
		}
	}



}
