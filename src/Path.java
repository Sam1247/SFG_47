import java.util.ArrayList;

public class Path {
	ArrayList<Integer> nodes = new ArrayList<>();
	public Path(ArrayList<Integer> nodes) {
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
		return gain;
	}
}
