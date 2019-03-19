import java.util.ArrayList;

public class Loop {
	ArrayList<Integer> nodes = new ArrayList<>();
	int hashValue = 1;

	public Loop(ArrayList<Integer> nodes) {
		this.nodes = nodes;

		if (nodes.size() == 2) {
			for (int s: nodes) {
				hashValue *= s + 1;
			}
			return;
		}

		int i = 0;

		for (int s: nodes) {
			i++;
			if (i%2 == 0) {
				hashValue *= s + 1;
			} else {
				hashValue *= s + 2;
			}
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
