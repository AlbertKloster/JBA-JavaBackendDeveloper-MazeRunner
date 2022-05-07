package maze;

import java.util.*;

public class Graph {

    int vertexNumber;
    List<Edge> edges;

    public Graph(int vertexNumber) {
        this.vertexNumber = vertexNumber;
        edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    private int find(List<Subset> subsets, int i) {
        // find root and make root as parent of i
        // (path compression)
        if (subsets.get(i).parent != i)
            subsets.get(i).parent
                    = find(subsets, subsets.get(i).parent);
        return subsets.get(i).parent;
    }

    // unite two sets of x and y by rank
    private void unite(List<Subset> subsets, int x, int y)
    {
        int xRoot = find(subsets, x);
        int yRoot = find(subsets, y);

        // Attach smaller rank tree under root
        // of high rank tree (Union by Rank)
        if (subsets.get(xRoot).rank < subsets.get(yRoot).rank)
            subsets.get(xRoot).parent = yRoot;
        else if (subsets.get(xRoot).rank > subsets.get(yRoot).rank)
            subsets.get(yRoot).parent = xRoot;

            // If ranks are equal, then make one as
            // root and increment its rank by one
        else {
            subsets.get(yRoot).parent = xRoot;
            subsets.get(xRoot).rank++;
        }
    }

    public List<Edge> constructKruskalMST() {
        List<Edge> minSpanningTree = new ArrayList<>();

        // Step 1:  Sort all the edges in ascending
        // order of their weight.
        Collections.sort(edges);

        // Create single element subsets from every vertex with rank = 0
        List<Subset> subsets = new ArrayList<>();
        for (int vertex = 0; vertex < vertexNumber; vertex++) {
            Subset subset = new Subset();
            subset.parent = vertex;
            subset.rank = 0;
            subsets.add(subset);
        }

        // Number of edges to be taken is equal to vertexNumber - 1
        int i = 0;
        int count = 0;
        while (count < vertexNumber - 1) {
            // Step 2: Pick the lightest edge. And increment
            // the index for next iteration

            Edge currentEdge = edges.get(i);

            i++;
            int x = find(subsets, currentEdge.getSrc());
            int y = find(subsets, currentEdge.getDest());

            // If current edge doesn't produce a loop,
            // add it to result and increment the index
            // of result for next edge
            if (x != y) {
                count++;
                minSpanningTree.add(currentEdge);
                unite(subsets, x, y);
            }
        }
        return minSpanningTree;
    }

}
