package maze;

import java.util.*;
import java.util.stream.Collectors;

import static maze.Move.*;

/**
 *  Singleton class
 */
public class MazeHandler {

    private static MazeHandler instance;
    private int height;
    private int width;
    private int vertexNumber;
    private Index indexStart;
    private Index indexFinish;
    private Maze maze;
    private Graph graph;
    private List<Edge> minSpanningTree;

    public static MazeHandler getInstance() {
        if (instance == null) {
            instance = new MazeHandler();
        }
        return instance;
    }

    public static boolean isNull() {
        return instance == null;
    }

    private MazeHandler() { }


    public void findEscape() {
        int startVertexId = mapToVertexId(indexStart);
        Edge edge = findNotVisitedNotPathEdgeByAdjacentId(startVertexId);
        if (edge == null)
            return;
        Deque<Edge> path = new ArrayDeque<>();
        int adjacentId = getAdjacentId(edge, startVertexId);
        searchPath(path, edge, adjacentId);
        deleteEdgeWithDoubleStartVertexId(path, startVertexId);
        stracePath(path);
    }

    private void deleteEdgeWithDoubleStartVertexId(Deque<Edge> path, int startVertexId) {
        List<Edge> srcStartEdges = path.stream().filter(edge -> edge.getSrc() == startVertexId).collect(Collectors.toList());
        List<Edge> destStartEdge = path.stream().filter(edge -> edge.getDest() == startVertexId).collect(Collectors.toList());

        if (srcStartEdges.size() + destStartEdge.size() < 2)
            return;

        srcStartEdges.forEach(edge -> setNotPath(path, edge, startVertexId));

        destStartEdge.forEach(edge -> setNotPath(path, edge, startVertexId));

    }

    private void setNotPath(Deque<Edge> path, Edge startEdge, int startVertexId) {
        int srcAdjacentId = getAdjacentId(startEdge, startVertexId);
        Edge srcAdjacentEdge = path.stream()
                .filter(edge -> !edge.equals(startEdge))
                .filter(edge -> edge.getSrc() == srcAdjacentId || edge.getDest() == srcAdjacentId).findFirst().orElse(null);
        if (srcAdjacentEdge == null)
            startEdge.setPath(false);
    }

    private void stracePath(Deque<Edge> path) {
        path.stream()
                .filter(Edge::isPath)
                .forEach(edge -> {
                    int srcId = edge.getSrc();
                    int destId = edge.getDest();
                    Index srcIndex = mapToIndex(srcId);
                    Index destIndex = mapToIndex(destId);
                    Index passIndex = getPassIndex(srcIndex, destIndex);
                    maze.update(srcIndex, 2);
                    maze.update(destIndex, 2);
                    maze.update(passIndex, 2);
                });
    }

    private void searchPath(Deque<Edge> path, Edge edge, int adjacentId) {

        edge.setVisited(true);
        path.offerLast(edge);

        if (hasVertexFinish(edge)) {
            edge.setPath(true);
            return;
        }

        Edge nextEdge = findNotVisitedNotPathEdgeByAdjacentId(adjacentId);
        int nextAdjacentId;
        if (nextEdge == null) {
            Edge polledEdge = path.pollLast(); // poll the dead end

            assert polledEdge != null;
            polledEdge.setPath(false);
            edge.setPath(false);

            nextAdjacentId = getAdjacentId(polledEdge, adjacentId);

            if (path.isEmpty())
                nextEdge = polledEdge;
            else
                nextEdge = findVisitedAndPathEdgeByAdjacentId(getAdjacentId(edge, nextAdjacentId));

            if (nextEdge == null) {
                nextEdge = path.pollLast(); // poll the dead end
                nextAdjacentId = getAdjacentId(polledEdge, adjacentId);
            }

            assert nextEdge != null;
            nextEdge.setPath(false);
            edge.setPath(false);

        } else {
            nextAdjacentId = getAdjacentId(nextEdge, adjacentId);
            edge.setPath(true);
        }

        searchPath(path, nextEdge, nextAdjacentId);

    }

    private int getAdjacentId(Edge edge, int vertexId) {
        if (edge.getSrc() == vertexId)
            return edge.getDest();
        if (edge.getDest() == vertexId)
            return edge.getSrc();
        return -1;
    }

    private boolean hasVertexFinish(Edge edge) {
        Index srcIndex = mapToIndex(edge.getSrc());
        Index destIndex = mapToIndex(edge.getDest());
        boolean srcEqualsFinish = srcIndex.equals(indexFinish);
        boolean destEqualsFinish = destIndex.equals(indexFinish);
        return srcEqualsFinish || destEqualsFinish;
    }

    private Edge findVisitedAndPathEdgeByAdjacentId(int vertexId) {
        return minSpanningTree.stream()
                .filter(Edge::isVisited)
                .filter(Edge::isPath)
                .filter(edge -> edge.getSrc() == vertexId || edge.getDest() == vertexId)
                .findFirst()
                .orElse(null);
    }

    private Edge findNotVisitedNotPathEdgeByAdjacentId(int vertexId) {
        return minSpanningTree.stream()
                .filter(edge -> !edge.isVisited())
                .filter(edge -> !edge.isPath())
                .filter(edge -> edge.getSrc() == vertexId || edge.getDest() == vertexId)
                .findFirst()
                .orElse(null);
    }

    public void update(int size) {
        update(size, size);
    }

    public void update(int height, int width) {
        maze = new Maze(height, width);
        this.height = height;
        this.width = width;
        int vertexImax = (height - 1)/2;
        int vertexJmax = (width - 1)/2;
        vertexNumber = vertexImax * vertexJmax;
        graph = new Graph(vertexNumber);
        initGraph();
        minSpanningTree = constructKruskalMST();
        initMaze();
        makeRandomHorizontalBreach(0);
        makeRandomHorizontalBreach(width - 1);
        findEscape();
    }

    private void initMaze() {
        minSpanningTree.forEach(edge -> {
            Index srcIndex = mapToIndex(edge.getSrc());
            Index destIndex = mapToIndex(edge.getDest());
            Index passIndex = getPassIndex(srcIndex, destIndex);
            brakeWall(srcIndex);
            brakeWall(destIndex);
            brakeWall(passIndex);
        });
    }

    private void makeRandomHorizontalBreach(int j) {
        makeBreach(new Index(getRandomIndex(getBound()), j));
    }

    private int getBound() {
        return (getUpperLimit(height) - 1)/2;
    }

    private int getRandomIndex(int bound) {
        Random random = new Random();
        return bound == 0 ? 1 : random.nextInt(bound)*2 + 1;
    }

    private void makeBreach(Index index) {
        setStartFinishIndex(index);
        maze.update(index, 2);
        Index left = getNextToLeft(index);
        if (isInMazeBound(left) && maze.getByIndex(left) == 0) {
            makeBreach(left);
        }
        Index right = getNextToRight(index);
        if (isInMazeBound(right) && maze.getByIndex(right) == 0) {
            makeBreach(right);
        }
    }

    private void setStartFinishIndex(Index index) {
        if (isLeftWall(index))
            indexStart = getIndexStart(index);
        if (isRightWall(index))
            indexFinish = getIndexFinish(index);
    }

    private boolean isLeftWall(Index index) {
        return index.getJ() == 0;
    }

    private boolean isRightWall(Index index) {
        return index.getJ() == (width % 2 == 0 ? width - 2 : width - 1);
    }

    private Index getIndexStart(Index entryIndex) {
        return new Index(entryIndex.getI(), entryIndex.getJ() + 1);
    }

    private Index getIndexFinish(Index exitIndex) {
        return new Index(exitIndex.getI(), exitIndex.getJ() - 1);
    }

    private Index getNextToLeft(Index index) {
        return new Index(index.getI(), index.getJ() - 1);
    }

    private Index getNextToRight(Index index) {
        return new Index(index.getI(), index.getJ() + 1);
    }

    private boolean isInMazeBound(Index index) {
        return index.getI() < height && index.getI() >= 0 &&
                index.getJ() < width && index.getJ() >= 0;
    }

    public void printMaze() {
        print(false);
    }


    public void printPath() {
        print(true);
    }

    private void print(boolean path) {
        int[][] m = maze.getMaze();
        for (int[] row : m) {
            for (int c : row) {
                System.out.print(c == 0 ? "██" : c == 1 ? "  " : path ? "//" : "  ");
            }
            System.out.println();
        }
    }



    private void brakeWall(Index index) {
        maze.update(index, 1);
    }

    private Index getPassIndex(Index srcIndex, Index destIndex) {
        int i = (srcIndex.getI() + destIndex.getI())/2;
        int j = (srcIndex.getJ() + destIndex.getJ())/2;
        return new Index(i, j);
    }

    private List<Edge> constructKruskalMST() {
        return graph.constructKruskalMST();
    }

    private void initGraph() {
        getEdges().forEach(edge -> graph.addEdge(edge));
    }

    private List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (isVertex(i, j))
                    addVertexEdges(new Index(i, j), edges);
        return edges;
    }

    private boolean isVertex(int i, int j) {
        return isOdd(i) && isOdd(j) && isInRange(i, j);
    }

    private boolean isInRange(int i, int j) {
        return i < getUpperLimit(height) &&
                j < getUpperLimit(width);
    }

    private boolean isInRange(int vertexId) {
        return vertexId < vertexNumber;
    }

    private boolean isValidEdge(Edge edge) {
        return isInRange(edge.getSrc()) && isInRange(edge.getDest());
    }

    private void addVertexEdges(Index index, List<Edge> edges) {
        getMoves(index).forEach(move -> {
            Edge edge = getEdge(index, move);
            if (isValidEdge(edge))
                edges.add(getEdge(index, move));
        });
    }

    private List<Move> getMoves(Index index) {
        List<Move> moves = new ArrayList<>();
        int i = index.getI();
        int j = index.getJ();
        if (i > 1)
            moves.add(UP);
        if (j < getUpperLimit(width) - 1)
            moves.add(RIGHT);
        if (i < getUpperLimit(height) - 1)
            moves.add(DOWN);
        if (j > 1)
            moves.add(LEFT);
        return moves;
    }

    private int getUpperLimit(int mazeDimension) {
        return (mazeDimension - 1)/2 + (mazeDimension - 1)/2;
    }

    private Edge getEdge(Index index, Move move) {
        Random random = new Random();
        return new Edge(mapToVertexId(index), getDest(index, move), random.nextInt(100));
    }

    private int mapToVertexId(Index index) {
        int i = index.getI();
        int j = index.getJ();
        int vertexesInRow = (width - 1)/2;
        int iStep = vertexesInRow*(i - 1)/2;
        int jStep = Math.min(vertexesInRow, (j - 1)/2); // upperbound for even maze width (wall has two bricks)
        return iStep + jStep;
    }

    private Index mapToIndex(int vertexId) {
        int vertexesInRow = (width - 1)/2;
        int i = 2*(vertexId / vertexesInRow) + 1;
        int j = 2*(vertexId % vertexesInRow) + 1;
        return new Index(i, j);
    }

    private int getDest(Index index, Move move) {
        switch (move) {
            case UP: return mapToVertexId(new Index(index.getI() - 2, index.getJ()));
            case RIGHT: return mapToVertexId(new Index(index.getI(), index.getJ() + 2));
            case DOWN: return mapToVertexId(new Index(index.getI() + 2, index.getJ()));
            case LEFT: return mapToVertexId(new Index(index.getI(), index.getJ() - 2));
        }
        return -1;
    }

    private boolean isOdd(int n) {
        return !isEven(n);
    }

    private boolean isEven(int n) {
        return n % 2 == 0;
    }

    public Maze getMaze() {
        return maze;
    }

}
