package maze.controller;

import maze.MazeHandler;

public class FindController {
    public static void run() {
        MazeHandler mazeHandler = MazeHandler.getInstance();
        mazeHandler.findEscape();
        mazeHandler.printPath();
    }
}
