import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;


public class Graph {

	ArrayList<Node>[] nodes;

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
		Arrays.fill(deltaArray, 0);
	}

	private ArrayList<Loop> loops = new ArrayList<>();
	private ArrayList<Path> forwardPaths = new ArrayList<>();
	private ArrayList<Integer> currentForwardPath = new ArrayList<>();
	private ArrayList<Integer> currentLoop = new ArrayList<>();
	private ArrayList<Integer> subset = new ArrayList<>();
	private int[] deltaArray = new int[100];



	void printForwardPaths () {
		findFPathes(0);
		for (Path fp: forwardPaths) {
			System.out.println(fp.nodes);
			System.out.println(fp.getGainFrom(nodes));
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
			System.out.println(loop.getGainFrom(nodes));
		}
	}

	void generateSubs (int k, int n) {
		if (k == n) {
			if (subset.size() > 1) {
				if (!isTouched(subset)) {
					int sum = 1;
					for (int loop: subset) {
						sum *= loops.get(loop).getGainFrom(nodes);
					}

					deltaArray[subset.size()] += sum;
				}
			}
		} else {
			generateSubs(k+1, n);
			subset.add(k);
			generateSubs(k+1, n);
			subset.remove(subset.size()-1);
		}
	}

	boolean isTouched (ArrayList<Integer> temp) {
		ArrayList<Integer> arrayList = new ArrayList<>();
		for (int index: temp) {
			for (int node: loops.get(index).nodes) {
				arrayList.add(node);
			}
		}
		//check for duplicates
		Set<Integer> set = new HashSet<>(arrayList);
		if (set.size() != arrayList.size()) {
			return true;
		} else {
			return false;
		}
	}

	int getBigDelta () {
		Arrays.fill(deltaArray, 0);
		findLoops(0);
		generateSubs(0, loops.size());
		int sum = 0;
		for (int i = 2; i < 100; i++) {
			if (i%2 == 0) {
				sum += deltaArray[i];
			} else {
				sum -= deltaArray[i];
			}
		}
		for (Loop loop: loops) {
			sum -= loop.getGainFrom(nodes);
		}
		return 1+sum;
	}

	int getNunumerator () {
		Arrays.fill(deltaArray, 0);
		int sum = 0;
		for (Path path: forwardPaths) {
			// performing a deep copy of loops
			ArrayList<Loop> loopsCopy = new ArrayList<>();
			for (Loop loop: loops) {
				ArrayList<Integer> arrayList = new ArrayList<>();
				for (int i: loop.nodes) {
					arrayList.add(i);
				}
				Loop lp = new Loop(arrayList);
				loopsCopy.add(lp);
			}

			ArrayList<Integer> indxrm = new ArrayList<>();

			for (int i = 0; i < loops.size(); ++i) {
				for (int k: path.nodes) {
					if (loops.get(i).nodes.contains(k)) {
						indxrm.add(i);
						break;
					}
				}
			}

			for (int i: indxrm) {
				loops.remove(i);
			}

			sum += (getBigDelta())*path.getGainFrom(nodes);

			// deleting loops

			while (loops.size() > 0) {
				loops.remove(0);
			}
			// restoring loops from loopsCopy
			for (Loop loop: loopsCopy) {
				ArrayList<Integer> arrayList = new ArrayList<>();
				for (int i: loop.nodes) {
					arrayList.add(i);
				}
				Loop lp = new Loop(arrayList);
				loops.add(lp);
			}
		}
		return sum;
	}

}
