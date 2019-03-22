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
		Arrays.fill(deltaArrayBeforeElimination, 0);
		Arrays.fill(deltaArrayAfterElimination, 0);
	}

	private ArrayList<Loop> loops = new ArrayList<>();
	private ArrayList<Integer> subset = new ArrayList<>();
	private int[] deltaArrayBeforeElimination = new int[100];
	private ArrayList<Loop> loopsAfterElimination = new ArrayList<>();
	private ArrayList<Integer> subsetAfterElimination = new ArrayList<>();
	private int[] deltaArrayAfterElimination = new int[100];

	private ArrayList<Path> forwardPaths = new ArrayList<>();
	private ArrayList<Integer> currentForwardPath = new ArrayList<>();
	private ArrayList<Integer> currentLoop = new ArrayList<>();




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

	void generateSubs (int k, int n, int[] deltaArray, ArrayList<Loop> loops) {
		if (k == n) {
			if (subset.size() > 1) {
				if (!isTouched(subset, loops)) {
					int sum = 1;
					for (int loop: subset) {
						sum *= loops.get(loop).getGainFrom(nodes);
					}

					deltaArray[subset.size()] += sum;
				}
			}
		} else {
			generateSubs(k+1, n, deltaArray, loops);
			subset.add(k);
			generateSubs(k+1, n, deltaArray, loops);
			subset.remove(subset.size()-1);
		}
	}

	boolean isTouched (ArrayList<Integer> temp, ArrayList<Loop> loops) {
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
		Arrays.fill(deltaArrayBeforeElimination, 0);
		findLoops(0);
		generateSubs(0, loops.size(), deltaArrayBeforeElimination, loops);
		int sum = 0;
		for (int i = 2; i < 100; i++) {
			if (i%2 == 0) {
				sum += deltaArrayBeforeElimination[i];
			} else {
				sum -= deltaArrayBeforeElimination[i];
			}
		}
		for (Loop loop: loops) {
			sum -= loop.getGainFrom(nodes);
		}
		return 1+sum;
	}

	int getNunumerator () {

		int sum = 0;

		for (Path path: forwardPaths) {

			int temp = path.getGainFrom(nodes);

			int p = getSmallDelta(path);

			temp *= p;

			sum += temp;
		}

		return sum;

	}

	int getSmallDelta (Path path) {
		Arrays.fill(deltaArrayAfterElimination, 0);
		loopsAfterElimination.clear();

		for (Loop loop: loops) {
			boolean isConatained = false;
			for (int node: loop.nodes) {
				if (path.nodes.contains(node)) {
					isConatained = true;
					break;
				}
			}
			if (!isConatained) {
				loopsAfterElimination.add(loop);
			}
		}


		subset.clear();
		generateSubs(0, loopsAfterElimination.size(), deltaArrayAfterElimination, loopsAfterElimination);
		int sum = 0;
		for (int i = 2; i < 100; i++) {
			if (i%2 == 0) {
				sum += deltaArrayAfterElimination[i];
			} else {
				sum -= deltaArrayAfterElimination[i];
			}
		}
		for (Loop loop: loopsAfterElimination) {
			sum -= loop.getGainFrom(nodes);
		}
		return 1+sum;



	}

}
