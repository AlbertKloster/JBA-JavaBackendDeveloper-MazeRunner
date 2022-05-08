package maze.enums;

public enum Command {
    GENERATE,
    LOAD,
    SAVE,
    DISPLAY,
    FIND,
    EXIT;

    public static Command getCommand(int n) {
        switch (n) {
            case 1: return Command.GENERATE;
            case 2: return Command.LOAD;
            case 3: return Command.SAVE;
            case 4: return Command.DISPLAY;
            case 5: return Command.FIND;
            case 0: return Command.EXIT;
            default: return null;
        }
    }
}
