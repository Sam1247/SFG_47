import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;

public class Graph {

	ArrayList<Node>[] nodes;
	Boolean [] visited;
	Boolean added [];

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
			for (int i = currentLoop.size()-1; i >= 0 && currentLoop.get(i) != s; i--) {
				System.out.print(currentLoop.get(i) + " ");
			}
			return;
		}
		if (currentLoop.size() == 0) {
			return;
		}
		currentLoop.add(s);
		for (Node u: nodes[s]) {
			findLoops(u.to);
		}
		currentLoop.remove(currentLoop.size()-1);

	}




}
