import java.util.*;
import java.util.Scanner;  // Import the Scanner class


public class Main {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		int size = scanner.nextInt();
		int edges = scanner.nextInt();

		Graph graph = new Graph(size);



		for (int i = 0; i < edges; i++) {
			int from; int to; int gain;
			from = scanner.nextInt();
			to = scanner.nextInt();
			gain = scanner.nextInt();
			graph.nodes[from].add(new Node(to, gain));
		}

		System.out.println("Forward paths:");
		graph.printForwardPaths();
		System.out.println("All Loops:");
		graph.printLoops();
		System.out.print("Big Delta = ");
		System.out.println(graph.getBigDelta());
		System.out.print("Nunumerator: ");
		System.out.println(graph.getNunumerator());
		System.out.println("Result: ");
		System.out.println(graph.getResult());

	}
}
