package maze.controller;

import maze.MazeHandler;

public class DisplayController {
    public static void run() {
        if (MazeHandler.isNull()) {
            System.out.println("No maze available.");
            return;
        }
        MazeHandler.getInstance().printMaze();
    }
}
