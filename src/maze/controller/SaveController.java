package maze.controller;

import maze.Console;
import maze.FileHandler;

public class SaveController {
    public static void run() {
        String filename = Console.getFilename();
        FileHandler.save(filename);
    }
}
