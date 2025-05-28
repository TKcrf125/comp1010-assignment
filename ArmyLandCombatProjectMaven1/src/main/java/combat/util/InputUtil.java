package majorAssignmentTopic1.src.main.java.combat.util;

import java.util.Scanner;

/**
 * all about reading what the player does.
 */
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads an integer from the user 
     */
    public static int readInt(String prompt, int min, int max) {
        int value;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid. " + prompt);
                scanner.next();
            }
            value = scanner.nextInt();
        } while (value < min || value > max);
        scanner.nextLine(); // consume newline (idk why but this took me ages to get rid of the double console stuff)
        return value;
    }

    /**
     *user presses Enter to continue 
     */
    public static void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
