package maze.controller;

import maze.Console;
import maze.FileHandler;

public class LoadController {
    public static void run() {
        String filename = Console.getFilename();
        FileHandler.load(filename);
    }
}
