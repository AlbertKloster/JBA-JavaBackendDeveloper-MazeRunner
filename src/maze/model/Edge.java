package maze.model;

import java.util.Objects;

public class Edge implements Comparable<Edge> {
    private final int src;
    private final int dest;
    private final int weight;
    private boolean isPath, visited;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public boolean isPath() {
        return isPath;
    }

    public void setPath(boolean path) {
        isPath = path;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }

    public int getWeight() {
        return weight;
    }

    // Override equals and hashCode for forming undirected graph
    // src and dest are interchangeable
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return src == edge.src && dest == edge.dest ||
                dest == edge.src && src == edge.dest;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.max(src, dest), Math.min(src, dest));
    }

    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}
