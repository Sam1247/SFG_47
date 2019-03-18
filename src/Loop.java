import java.util.ArrayList;
import java.util.LinkedList;

public class Loop {
	ArrayList<Integer> nodes = new ArrayList<>();
	int hashValue = 1;

	public Loop(ArrayList<Integer> nodes) {
		this.nodes = nodes;
		for (int s: nodes) {
			hashValue *= s;
		}
	}

	boolean isEqualTo (Loop loop) {
		if (loop.nodes.size() == this.nodes.size() && loop.hashValue == this.hashValue) {
			return true;
		} else {
			return false;
		}
	}
}
