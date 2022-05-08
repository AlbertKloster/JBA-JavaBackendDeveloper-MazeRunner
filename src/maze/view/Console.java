package maze.view;

import maze.enums.Command;

import java.util.List;
import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getInt() {
        String input = scanner.nextLine();
        if (isNumber(input)) {
            return Integer.parseInt(input);
        }
        System.out.println("Not a number. Please try again.");
        return getSize();
    }

    public static int getSize() {
        String input = scanner.nextLine();
        if (isNumber(input)) {
            int size = Integer.parseInt(input);
            if (isGreaterTwo(size))
                return size;
        }
        System.out.println("Incorrect size. Please try again.");
        return getSize();
    }

    private static boolean isNumber(String input) {
        return input.matches("\\d+");
    }

    private static boolean isGreaterTwo(int size) {
        return size > 2;
    }

    public static Command getCommand(boolean isShort) {
        int input = getInt();
        if (validInputs(isShort).contains(input))
            return Command.getCommand(input);
        System.out.println("Incorrect option. Please try again.");
        return getCommand(isShort);
    }

    private static List<Integer> validInputs(boolean isShort) {
        if (isShort)
            return List.of(1, 2, 0);
        else
            return List.of(1, 2, 3, 4, 5, 0);
    }

    public static String getFilename() {
        return scanner.nextLine();
    }

}
