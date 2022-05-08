package maze.controller;

import maze.view.Console;
import maze.view.FileHandler;

public class SaveController {
    public static void run() {
        String filename = Console.getFilename();
        FileHandler.save(filename);
    }
}
