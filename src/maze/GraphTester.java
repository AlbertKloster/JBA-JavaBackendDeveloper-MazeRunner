package maze;

import java.util.ArrayList;
import java.util.List;

public class GraphTester {

    // Driver Code
    public static void test() {

        /* maze graph
                 10      3
            0--------1--------2
            |        |        |
           6|        |15      |12
            |        |        |
            3--------4--------5
            |    2   |   7    |
           2|        |13      |3
            |        |        |
            6--------7--------8
                8        4
        */

        int vertexNumber = 9;
        Graph graph = new Graph(vertexNumber);

        // add edge 0-1
        graph.addEdge(new Edge(0, 1, 10));

        // add edge 0-2
        graph.addEdge(new Edge(0, 3, 6));

        // add edge 1-0
        graph.addEdge(new Edge(1, 0, 10));

        // add edge 1-2
        graph.addEdge(new Edge(1, 2, 3));

        // add edge 1-3
        graph.addEdge(new Edge(1, 4, 15));

        // add edge 2-1
        graph.addEdge(new Edge(2, 1, 3));

        // add edge 2-5
        graph.addEdge(new Edge(2, 5, 12));

        // add edge 3-0
        graph.addEdge(new Edge(3, 0, 6));

        // add edge 3-4
        graph.addEdge(new Edge(3, 4, 2));

        // add edge 3-6
        graph.addEdge(new Edge(3, 6, 2));

        // add edge 4-1
        graph.addEdge(new Edge(4, 1, 15));

        // add edge 4-3
        graph.addEdge(new Edge(4, 3, 2));

        // add edge 4-5
        graph.addEdge(new Edge(4, 5, 7));

        // add edge 4-7
        graph.addEdge(new Edge(4, 7, 13));

        // add edge 5-2
        graph.addEdge(new Edge(5, 2, 12));

        // add edge 5-4
        graph.addEdge(new Edge(5, 4, 7));

        // add edge 5-8
        graph.addEdge(new Edge(5, 8, 3));

        // add edge 6-3
        graph.addEdge(new Edge(6, 3, 2));

        // add edge 6-7
        graph.addEdge(new Edge(6, 7, 8));

        // add edge 7-4
        graph.addEdge(new Edge(7, 4, 13));

        // add edge 7-6
        graph.addEdge(new Edge(7, 6, 8));

        // add edge 7-8
        graph.addEdge(new Edge(7, 8, 4));

        // add edge 8-5
        graph.addEdge(new Edge(8, 5, 3));

        // add edge 8-7
        graph.addEdge(new Edge(8, 7, 4));

        // Function call
        List<Edge> output = graph.constructKruskalMST();

                /* min spanning tree
            0--------1--------2
            |
            |
            |
            3--------4--------5
            |                 |
            |                 |
            |                 |
            6        7--------8

        */


        List<Edge> expectedOutput = new ArrayList<>();
        expectedOutput.add(new Edge(3, 4, 2));
        expectedOutput.add(new Edge(3, 6, 2));
        expectedOutput.add(new Edge(1, 2, 3));
        expectedOutput.add(new Edge(5, 8, 3));
        expectedOutput.add(new Edge(7, 8, 4));
        expectedOutput.add(new Edge(0, 3, 6));
        expectedOutput.add(new Edge(4, 5, 7));
        expectedOutput.add(new Edge(0, 1, 10));

        boolean isSuccess = expectedOutput.stream().mapToInt(Edge::getWeight).sum() ==
                output.stream().mapToInt(Edge::getWeight).sum();
        for (int i = 0; i < output.size(); i++) {
            isSuccess = isSuccess &&
                    output.get(i).getSrc() == expectedOutput.get(i).getSrc() &&
                    output.get(i).getDest() == expectedOutput.get(i).getDest() &&
                    output.get(i).getWeight() == expectedOutput.get(i).getWeight() &&
                    output.size() == expectedOutput.size();
        }

        if (isSuccess)
            System.out.println("Test passed!");
        else
            System.out.println("Test failed!");
    }

}
