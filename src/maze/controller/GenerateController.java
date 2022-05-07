package maze.controller;

import maze.Console;
import maze.MazeHandler;
import maze.Menu;

public class GenerateController {
    public static void run() {
        Menu.printGenerate();
        int size = Console.getSize();
        MazeHandler mazeHandler = MazeHandler.getInstance();
        mazeHandler.update(size);
        mazeHandler.printMaze();
    }
}
