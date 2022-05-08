package maze.controller;

import maze.view.Console;
import maze.view.FileHandler;

public class LoadController {
    public static void run() {
        String filename = Console.getFilename();
        FileHandler.load(filename);
    }
}
