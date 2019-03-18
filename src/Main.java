import java.util.*;
import java.util.Scanner;  // Import the Scanner class


public class Main {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		int size = scanner.nextInt();
		int edges = scanner.nextInt();
		//ArrayList<Node> Graph = new ArrayList<Node>();

		//ArrayList<Node> [] graph = new ArrayList[size];

		Graph graph = new Graph(size);



		for (int i = 0; i < edges; i++) {
			int from; int to; int gain;
			from = scanner.nextInt();
			to = scanner.nextInt();
			gain = scanner.nextInt();
			graph.nodes[from].add(new Node(to, gain));
		}

		graph.printLoops();

		//ArrayList<ArrayList<Integer>> test = new ArrayList<ArrayList<Integer>>();


		//test = graph.getforwardPathes();
		//graph.findLoops(0);

//		for (int i = 0; i < test.size(); ++i) {
//			for (int j= 0; j < test.get(i).size(); j++) {
//				System.out.print(test.get(i).get(j));
//				System.out.print(" ");
//			}
//			System.out.println();
//		}
//





	}
}
