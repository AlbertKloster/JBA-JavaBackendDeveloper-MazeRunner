package maze.model;

public class Maze {

    private int height;
    private int width;

    private int[][] maze;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        maze = new int[height][width];
    }

    public int[][] getMaze() {
        return maze;
    }

    public void update(Index index, int value) {
        maze[index.getI()][index.getJ()] = value;
    }

    public Integer getByIndex(Index index) {
        if (index.getI() >= height|| index.getJ() >= width)
            return null;
        return maze[index.getI()][index.getJ()];
    }
}
