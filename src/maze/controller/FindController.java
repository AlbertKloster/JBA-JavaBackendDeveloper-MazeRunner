package maze.controller;

import maze.model.MazeHandler;

public class FindController {
    public static void run() {
        MazeHandler mazeHandler = MazeHandler.getInstance();
        mazeHandler.findEscape();
        mazeHandler.printPath();
    }
}
