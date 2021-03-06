package maze.controller;

import maze.view.Console;
import maze.model.MazeHandler;
import maze.view.Menu;

public class MainController {

    public static void run() {
        while (true) {
            Menu.print(MazeHandler.isNull());
            switch (Console.getCommand(MazeHandler.isNull())) {
                case GENERATE: {
                    GenerateController.run();
                    break;
                }
                case LOAD: {
                    LoadController.run();
                    break;
                }
                case SAVE: {
                    SaveController.run();
                    break;
                }
                case DISPLAY: {
                    DisplayController.run();
                    break;
                }
                case FIND: {
                    FindController.run();
                    break;
                }
                case EXIT: {
                    System.out.println("Bye!");
                    return;
                }
            }
        }
    }
}
