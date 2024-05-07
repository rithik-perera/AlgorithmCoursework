import java.util.InputMismatchException;

/**
 * Student ID: 20222011/w1985685
 * Name: Rithik Perera
 *
 * Main class where the program is run */
public class Main {
    public static void main(String[] args) {
            while (true) {
                int usersChoice = Input.inputInt("1. To choose your designated file\n2. To exit the code \nEnter Your Option:");

                try {
                    switch (usersChoice) {
                        case 1 -> {
                            System.out.println("You might need to minimize your windows to see the file selector");
                            FileReader fileReader = new FileReader();
                            Cell[][] grid = fileReader.parsePuzzleFromFile();
                            if (grid != null) {  // Check if the grid is not null
                                PathFinder shortPath = new PathFinder(grid);
                                shortPath.findPath();
                                shortPath.displayShortestPath();
                                double endingTime = System.nanoTime();
                                double startingTime = fileReader.getStartTime();
                                double totalTimeTaken = (endingTime - startingTime)/1000000;
                                System.out.println("Time Taken: " + totalTimeTaken + " milliseconds");
                            } else {
                                System.out.println("Encountered an error when parsing the puzzle in the text file.");
                            }
                        }
                        case 2 -> {
                            return;
                        }
                        default -> System.out.println("The entered value is incorrect! Please select from the two valid options.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please Enter Valid Option!");
                }
            }
        }
}