package combat.util;

import java.util.Scanner;

/**
 * Utility for reading console input.
 */
// NOTE: AI-generated utility class—consider rewriting descriptions.
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads an integer from the user between min and max (inclusive).
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
        scanner.nextLine(); // consume newline
        return value;
    }

    /**
     * Pauses until the user presses Enter.
     */
    public static void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
