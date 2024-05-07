import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Student ID: 20222011/w1985685
 * Name: Rithik Perera
 *
 *
 * Read the text file from the selected file in the JFileChooser
 * Parses this file into the grid format
 * This allows us to use the AStar algorithm to find the shortest path
 * */
public class FileReader {

    private double startingTime;

    /**
     * The selected text file of the puzzle gets parsed into a grid of cells
     * @return The grid, or null in case of an error
     */
    public Cell[][] parsePuzzleFromFile() {
        //create new file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        int result = fileChooser.showOpenDialog(null);
        //returns null if no file is selected
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("There was no file selected.");
            return null;
        }
        //gets the selected file and stores it in the variable named file
        File file = fileChooser.getSelectedFile();
        System.out.println("Selected file: " + file.getAbsolutePath());

        Scanner reader = null;

        try {
            //A scanner will be used to read the file
            reader = new Scanner(file);
            startingTime = System.nanoTime();
            //height and width will be used to initialize the grid size
            int height = calculateHeight(reader);
            int width = calculateWidth(new Scanner(file));

            //Debugging line to print out the height and width to the console
            System.out.println("Height: " + height + ", Width: " + width);

            Cell[][] grid = new Cell[height][width];
            fillGrid(new Scanner(file), grid);
            return grid;

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }

    /**
     * Helper method for parsepuzzleFromFile
     * Calculate the height of the text file
     * @param scanner The scanner used to read the file.
     * @return The height of the grid.*/
    private int calculateHeight(Scanner scanner) {
        int rowCount = 0;

        while (scanner.hasNextLine()) {
            scanner.nextLine();
            rowCount++;
        }

        return rowCount;
    }
    /**
     * Helper method for parsePuzzleFromFile
     * Calculate the width of the text file
     * @param reader The scanner used to read the file.
     * @return The width of the grid.*/
    private int calculateWidth(Scanner reader) {
        String line = reader.nextLine();
        return line.length();
    }


    /**
     *Helper method for parsePuzzleFromFile
     * Fills out the cells in the grid according to the values in the text file
     * @param reader The scanner used to read the file.
     * @param grid   The empty grid to be filled with cell values. */
    private void fillGrid(Scanner reader, Cell[][] grid) {
        // Print a message to indicate the start of filling the grid
        System.out.println("Filling the grid...");

        for (int i = 0; i < grid.length; i++) {// Keep iterating until the number of rows matches the height
            if (reader.hasNextLine()) {
                String line = reader.nextLine();
                for (int j = 0; j < grid[i].length; j++) { // Keep iterating until the number of elements in the column  matches the width
                    if (j < line.length()) {
                        grid[i][j] = new Cell(i, j, String.valueOf(line.charAt(j)));

                        // Print the current cell's value for debugging
                        System.out.print(grid[i][j].value + " ");
                    } else {
                        System.out.println("line has unexpectedly stopped in row number:  " + (i + 1));
                        return;
                    }
                }
                // Move to the next line
                System.out.println();
            } else {
                System.out.println("Unexpectedly reached the end of the file ");
                return;
            }
        }
        // Print a message to indicate the end of filling the grid
        System.out.println("Successfully filled out the grid!");
    }

    public double getStartTime() {
        return startingTime;
    }
}
