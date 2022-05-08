package maze.controller;

import maze.view.Console;
import maze.model.MazeHandler;
import maze.view.Menu;

public class GenerateController {
    public static void run() {
        Menu.printGenerate();
        int size = Console.getSize();
        MazeHandler mazeHandler = MazeHandler.getInstance();
        mazeHandler.update(size);
        mazeHandler.printMaze();
    }
}
