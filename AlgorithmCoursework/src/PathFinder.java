import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *Student ID: 20222011/w1985685
 *Name: Rithik Perera
 *
 * Finds the shortest path from the grid
 * */
public class PathFinder {
    private Cell[][] grid; //2D array with the data type Cell
    public static final int VER_HOR_COST = 1; // Defining 1 as the cost to move horizontally and vertically
    private PriorityQueue<Cell> openCells; //The set of nodes or cells that are not yet evaluated and are put in ascending order of cost

    private boolean[][] closedCells; // The cells that are already evaluated

    //Indexes of the starting value and the final value (goal)
    private int iStart;
    private int jStart;
    private int iFinish;
    private int jFinish;

    /**
     * Constructor
     * @param grid 2D array of type cell to represent the puzzle
     * Evaluate the closed and open cells
     * Evaluate the starting and ending points
     * Calculate heuristic cost
     * */
    public PathFinder(Cell[][] grid){
        System.out.println("Starting PathFinder...");
        //Initialize the grid
        this.grid = grid;
        closedCells = new boolean[grid.length][grid[0].length]; // Will initialize the 2D array of Close list which will populate with the value false as the default
        openCells = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell cell1, Cell cell2) { //This comparator is setting the priority level for each cell according to the conditions below
                if (cell1.finalCost < cell2.finalCost) {
                    return -1;
                } else if (cell1.finalCost > cell2.finalCost) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        System.out.println("Evaluating the indexes for the starting and ending point ...");
        //Iterate through the puzzle to find the i and j value for the starting and finishing point
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Cell currentCell = grid[i][j];
                if (currentCell.value.equals("S")) {
                    iStart = i;
                    jStart = j;
                }
                if (currentCell.value.equals("F")) {
                    iFinish = i;
                    jFinish = j;
                }
            }
        }
        //Calculating the heuristic cost
        System.out.println("Calculating heuristic costs...");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Cell currentCell = grid[i][j];
                currentCell.currentHeuristicCost = Math.abs(i - iFinish) + Math.abs(j - jFinish);
                currentCell.inSolutionPath = false;
            }
        }
        //The final cost for the starting cell of the grid will be equated to 0
        grid[iStart][jStart].finalCost = 0;
    }

    /**
     * Update the cost of the neighbouring cell if valid
     * That's the neighbour to the open list, if it's already not added .
     *
     * @param current   The current cell .
     * @param neighbour The neighbouring cell.
     * @param cost      The cost to reach the neighbour cell.
     */
    public void updateCost(Cell current, Cell neighbour, int cost) {
        //Check if the neighbouring cell is a rock
        if (neighbour.value.equals("0") || closedCells[neighbour.i][neighbour.j]) {
            return;
        }

        boolean inOpenCells = false;
        int neighbourFinalCost = neighbour.currentHeuristicCost + cost;

        //Iterate through the openCells to check if the neighbouring cell is already in the openCells
        for (Cell cell : openCells) {
            if (cell.equals(neighbour)) {
                inOpenCells = true;
                break;
            }
        }

        if (!inOpenCells || neighbourFinalCost < neighbour.finalCost) {
            neighbour.finalCost = neighbourFinalCost;
            neighbour.parent = current; //Assign the neighbouring cells parent as the current cell

            if (!inOpenCells) {
                openCells.add(neighbour);
            }
        }
    }

    /**
     * Goes through the grid from the current cell to the direction which is provided ,
     * Updates the cost
     * checks for rocks in the path .
     * @param current      The current cell here are the sliding starts from .
     * @param iDirection   The vertical direction (-1 is for up movement, 1 is for down movement and 0 for no movement).
     * @param jDirection   The horizontal direction (-1 is for left movement, 1 is for right movement and 0 for no movement).
     */
    private void slide(Cell current, int iDirection, int jDirection) {
        //Increase movement vertically or horizontally based on direction
        int i = current.i + iDirection;
        int j = current.j + jDirection;
        int cost = 0;

        //Keep on iterating until it reaches the corner or reaches a rock
        while (isWithinWall(i, j) && !isRock(i, j)) {
            cost += VER_HOR_COST; // Increment the cost for sliding

            // Check if it has reached the finish line
            if (reachedFinishLine(i, j)) {
                //Setting the current cell as the parent cell and also updating the cost
                updateCost(current, grid[i][j], current.finalCost + cost);
                return; // Exit the method
            }

            // Move to the next cell in the sliding direction
            i += iDirection; // Update the row index
            j += jDirection; // Update the column index
        }

        // Update cost for the last reachable cell in the sliding direction
        if (isWithinWall(i - iDirection, j - jDirection)) {
            Cell neighbor = grid[i - iDirection][j - jDirection];
            updateCost(current, neighbor, current.finalCost + cost);
        }
    }


    /**
     * Helper method for method slide
     * @param i,j Indexes of the cell
     * Provides validation to make sure that it does not exceed the maximum index of the row and column
     * thus making sure that the indexes remain inside the puzzle
     * @return The boolean value indicating if within the puzzle*/
    private boolean isWithinWall(int i, int j) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[0].length;
    }


    /**
     * Helper method for method slide
     * @param i,j Indexes of the cell
     * Checks if a current cell is a rock (has the value 0)
     * @return  Boolean value true if it is a rock */
    private boolean isRock(int i, int j) {
        return grid[i][j].value.equals("0");
    }


    /**
     * Helper method for method slide
     * @param i,j Indexes of the cell
     *@return  Boolean value if the [i][j] is equal to "F"*/
    private boolean reachedFinishLine(int i, int j) {
        return i == iFinish && j == jFinish;
    }

    /**
     * Find the shortest path from the start cell to the finish cell*/
    public void findPath() {
        openCells.add(grid[iStart][jStart]); // add the starting cell To the openCells priority queue
        Cell current = null;

        //Iterating through all the elements in the openCells cell PriorityQueue
        while (!openCells.isEmpty()) {
            current = openCells.poll(); //Returns and removes the cell with the lowest final cost

            //Check if the current cell is a rock or if it has already been added to the closedCells list
            if (current.value.equals("0") || closedCells[current.i][current.j]) {
                break;
            }

            //Change the value in the closed cell array from false to true (Is false in by default)
            closedCells[current.i][current.j] = true;

            //Check if this cell is the finished cell
            //In that case we have found the path
            if (current.equals(grid[iFinish][jFinish])) {
                return;
            }

            slide(current, -1, 0); // Decreasing the i index by 1 (Moving up)
            slide(current, 0, 1); //Increasing the j index by 1 (Moving right)
            slide(current, 1, 0); //Increase in the i index by 1 (Moving down)
            slide(current, 0, -1); //Decreasing the j index by 1 (Moving left)

        }
    }

    /**
     * Helper method for printStep
     * @param currentCell,nextCell The current cell and the neighbouring cell respectively
     * @return String value of the direction */
    private static String directionFromCurrentToNext(Cell currentCell, Cell nextCell) {
        if (nextCell.i < currentCell.i) {
            return "up";
        }
        if (nextCell.j > currentCell.j) {
            return "right";
        }
        if (nextCell.i > currentCell.i) {
            return "down";
        }
        if (nextCell.j < currentCell.j) {
            return "left";
        }
        return "unknown";
    }

    /**
     * Displays the solution path (Shortest path) from the starting point to the ending point
     * */
    public void displayShortestPath() {
        // Check if there is an existing path
        if (closedCells[iFinish][jFinish]) {
            System.out.println("path found successfully ");

            Cell current = grid[iFinish][jFinish];

            //Initializing a new list to store the path
            List<Cell> path = new ArrayList<>();

            // Add the finishing cell to the path array list
            path.add(current);

            //Traversing back through the parent of each current cell until parent is null (Reached the starting cell)
            while (current.parent != null) {
                //Keep inserting the parents to the beginning of the arraylist
                //This way the existing elements gets pushed back
                path.add(0, current.parent);
                current = current.parent;
            }

            // Print the path
            printPath(path);

        } else {
            // No solution is found
            System.out.println("Unable to find any path. ");
        }
    }

    /**
     * Helper method for displaySolution
     * @param path  Contains list of cells to the shortest path from the starting cell to the finish cell
     * Prints out the steps from the starting cell to the finishing cell with all the steps */
    private void printPath(List<Cell> path) {
        System.out.println("Start at: (" + (path.get(0).j + 1) + "," + (path.get(0).i + 1) + ")");

        for (int step = 1; step < path.size(); step++) {
            printStep(path.get(step - 1), path.get(step), step);
        }

        System.out.println("Done!");
    }

    /**
     * Helper method for printPath
     * @param currentCell,nextCell The current cell and the neighbouring cell respectively
     * @param step The step number
     * Print out each step from current cell to next cell*/
    private void printStep(Cell currentCell, Cell nextCell, int step) {
        String direction = directionFromCurrentToNext(currentCell, nextCell);
        System.out.println("Step " + step + ": Go " + direction + " to (" + (nextCell.j + 1) + "," + (nextCell.i + 1) + ")");
    }
}
