import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Arrays;

public class Graph {

	ArrayList<Node>[] nodes;
	Boolean [] visited;
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
		visited = new Boolean[size];
		Arrays.fill(visited, Boolean.FALSE);
		for (int i = 0; i < size; ++i) {
			nodes[i] = new ArrayList<Node>();
		}

	}

	private ArrayList<ArrayList<Integer>> forwardPathes = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> currentForwardPath = new ArrayList<>();
	private ArrayList<Integer> currentLoop = new ArrayList<>();



	ArrayList<ArrayList<Integer>> getforwardPathes () {
		findFPathes(0);
		return forwardPathes;
	}



	void findFPathes (int s) {
		if (nodes[s].size() == 0) {
			currentForwardPath.add(s);
			// print
			System.out.println(currentForwardPath);
			currentForwardPath.remove(currentForwardPath.size()-1);
			return;
		}
		currentForwardPath.add(s);
		visited[s] = true;
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
			nodes.add(s);
			for (int i = currentLoop.size()-1; i >= 0 && currentLoop.get(i) != s; i--) {
				nodes.add(currentLoop.get(i));
			}
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
		}
	}



}
