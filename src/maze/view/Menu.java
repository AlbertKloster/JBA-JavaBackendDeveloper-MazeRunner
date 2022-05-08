package maze.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    private static final Map<Integer, String> menu = initMenu();

    private static Map<Integer, String> initMenu() {
        Map<Integer, String> menu = new HashMap<>();
        menu.put(1, "Generate a new maze");
        menu.put(2, "Load a maze");
        menu.put(3, "Save the maze");
        menu.put(4, "Display the maze");
        menu.put(5, "Find the escape");
        menu.put(0, "Exit");
        return menu;
    }

    public static void print(boolean isShort) {
        if (isShort)
            printShortMenu();
        else
            printFullMenu();
    }

    private static void printShortMenu() {
        printMenu(List.of(1, 2, 0));
    }

    private static void printFullMenu() {
        printMenu(List.of(1, 2, 3, 4, 5, 0));
    }

    private static void printMenu(List<Integer> list) {
        System.out.println("\n=== Menu ===");
        list.forEach(k -> System.out.printf("%d. %s\n", k, menu.get(k)));
    }

    public static void printGenerate() {
        System.out.println("Enter the size of a new maze");
    }

}
