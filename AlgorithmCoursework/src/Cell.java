/**
 * Student ID: 20222011/w1985685
 * Name: Rithik Perera
 *
 *
 * Represents the attributes of a single value in the puzzle
 * */
public class Cell {
    public int i; //The index of the row number
    public int j;//The index of the column number
    public String value; //The value of that cell (".","0", "S", "F")
    public int currentHeuristicCost; //The current heuristic value of that particular cell
    public int finalCost; //Cost of the node from the starting node (f(n) = g(n) + h(n))
    public boolean inSolutionPath; //Boolean value identifying if cell is in the solution path
    public Cell parent; //Parent cell of the current cell

    public Cell(int i, int j, String value) {
        this.i = i;
        this.j = j;
        this.value = value;
    }
}
