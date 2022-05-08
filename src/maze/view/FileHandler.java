package maze.view;

import maze.model.Index;
import maze.model.MazeHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    public static void save(String filename) {
        File file = new File("./" + filename);

        try (FileWriter writer = new FileWriter(file)) {
            int[][] maze = MazeHandler.getInstance().getMaze().getMaze();
            writer.write(getMazeDimensions(maze));
            for (int[] row : maze) {
                for (int c : row) {
                    writer.write(Integer.toString(c));
                }
                writer.write("\r\n");
            }
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }

    }

    private static String getMazeDimensions(int[][] maze) {
        return String.format("%d %d%n",maze.length, maze[0].length);
    }


    public static void load(String filename) {
        File file = new File("./" + filename);

        try (Scanner scanner = new Scanner(file)) {
            int height;
            int width;
            List<List<String>> loadedMaze = new ArrayList<>();

            if (scanner.hasNext()) {
                String input = scanner.nextLine();
                Dimension dimension = getDimension(input);
                if (dimension == null) {
                    printWrongFileFormatException();
                    return;
                }
                height = dimension.height;
                width = dimension.width;

            } else {
                printWrongFileFormatException();
                return;
            }


            int count = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (isNotMazeRow(line, width)) {
                    printWrongFileFormatException();
                    return;
                }

                loadedMaze.add(List.of(line.split("")));
                count++;
                if (count > height) {
                    printWrongFileFormatException();
                    return;
                }
            }

            if (count != height) {
                printWrongFileFormatException();
                return;
            }
            updateMaze(loadedMaze);

        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + filename);
        }
    }

    private static void updateMaze(List<List<String>> loadedMaze) {
        MazeHandler mazeHandler = MazeHandler.getInstance();
        int height = loadedMaze.size();
        int width = loadedMaze.get(0).size();
        mazeHandler.update(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeHandler.getMaze().update(new Index(i, j), Integer.parseInt(loadedMaze.get(i).get(j)));
            }
        }
    }

    private static void printWrongFileFormatException() {
        System.out.println("Wrong file format!");
    }

    private static Dimension getDimension(String input) {
        if (!input.matches("\\d+\\s+\\d+"))
            return null;
        String[] dimensions = input.split("\\s+");
        return new Dimension(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));
    }

    private static boolean isNotMazeRow(String line, int width) {
        String regex = String.format("[012]{%d}", width);
        return !line.matches(regex);
    }


    private static class Dimension {
        int height;
        int width;

        public Dimension(int height, int width) {
            this.height = height;
            this.width = width;
        }
    }


}



