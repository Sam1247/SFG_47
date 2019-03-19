import java.util.ArrayList;

public class Loop {
	ArrayList<Integer> nodes = new ArrayList<>();
	//int hashValue = 1;

	public Loop(ArrayList<Integer> nodes) {
		this.nodes = nodes;

//		if (nodes.size() == 2) {
//			for (int s: nodes) {
//				hashValue *= s + 1;
//			}
//			return;
//		}
//
//		int i = 0;
//
//		for (int s: nodes) {
//			i++;
//			if (i%2 == 0) {
//				hashValue *= s + 1;
//			} else {
//				hashValue *= s + 2;
//			}
//		}
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