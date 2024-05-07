import java.util.Scanner;

/**
 * Student ID: 20222011/w1985685
 * Name: Rithik Perera
 *
 * Put class to make the user inputs much easier */
public class Input {

    /**
     * @param label is the message that gets displayed to the user in the console (What we need from the user)
     * Is a method to take in string values from the user
     * @return The users input (string)*/
    public static String inputStr(String label) {
        Scanner scan = new Scanner(System.in);
        System.out.print(label); //Label as to what the user needs to input

        while (true) {
            try {
                return scan.nextLine(); // The value entered by the user
            } catch (java.util.InputMismatchException e) { // To make sure that the user doesn't input the wrong data type
                System.out.println("Entered input type is incorrect (Expecting a string). Please try again.");
                scan.nextLine(); // Clear the buffer
                System.out.print(label);
            }
        }
    }

    /**
     * @param label is the message that gets displayed to the user in the console (What we need from the user)
     * Is a method to take in double values from the user
     * @return The users input (double)*/
    public static double inputDouble(String label) {
        Scanner scan = new Scanner(System.in);
        System.out.print(label); //Label as to what the user needs to input

        while (true) {
            try {
                return scan.nextDouble(); // The value entered by the user
            } catch (java.util.InputMismatchException e) {// To make sure that the user doesn't input the wrong data type
                System.out.println("Entered input type is incorrect (Expecting a double). Please try again.");
                scan.nextLine(); // Clear the buffer
                System.out.print(label);
            }
        }
    }

    /**
     * @param label is the message that gets displayed to the user in the console (What we need from the user)
     * Is a method to take in integer values from the user
     * @return The users input (integer)*/
    public static int inputInt(String label) {
        Scanner scan = new Scanner(System.in);
        System.out.print(label); //Label as to what the user needs to input

        while (true) {
            try {
                return scan.nextInt(); // The value entered by the user
            } catch (java.util.InputMismatchException e) {// To make sure that the user doesn't input the wrong data type
                System.out.println("Entered input type is incorrect (Expecting an integer). Please try again.");
                scan.nextLine();
                System.out.print(label);
            }
        }
    }
}
