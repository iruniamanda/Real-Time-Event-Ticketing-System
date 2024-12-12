package utils;
import java.util.Scanner;

public class InputValidator {

    /**
     * Validates and returns a positive integer input from the user.
     *
     * @param prompt  The message to display for prompting the user.
     * @param scanner The Scanner instance used for reading user input.
     * @return A valid positive integer input from the user.
     */
    public static int validatePositiveInteger(String prompt, Scanner scanner) {
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input > 0) {
                    scanner.nextLine(); // Clear the buffer to avoid reading issues.
                    return input;
                } else {
                    System.out.println("Error: Please enter a positive integer greater than 0.");
                }
            } else {
                System.out.println("Error: Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume the invalid input
            }
        }
    }
}

