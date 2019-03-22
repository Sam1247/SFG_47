import java.util.ArrayList;

public class Loop {
	ArrayList<Integer> nodes = new ArrayList<>();
	public Loop(ArrayList<Integer> nodes) {
		this.nodes = nodes;

	}

	int getGainFrom (ArrayList<Node>[] graphNodes) {
		int gain = 1;
		for (int i = 0; i < nodes.size()-1; i++) {
			for (Node node: graphNodes[nodes.get(i)]) {
				if (node.to == nodes.get(i+1)) {
					gain *= node.gain;
					break;
				}
			}
		}

		for (Node node: graphNodes[nodes.get(nodes.size()-1)]) {
			if (node.to == nodes.get(0)) {
				gain *= node.gain;
				break;
			}
		}

		return gain;
	}

	boolean isEqualTo (Loop loop) {
		if (loop.nodes.size() == this.nodes.size()) {
			int index2 = loop.nodes.indexOf(this.nodes.get(0));
			if (index2 == -1) {
				return false;
			}

			for (int i = 1; i < this.nodes.size(); i++) {
				index2 = ((index2 + 1) % (loop.nodes.size()));
				if (this.nodes.get(i) != loop.nodes.get(index2)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
