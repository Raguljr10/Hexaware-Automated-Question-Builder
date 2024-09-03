import java.sql.SQLException;
import java.util.Scanner;

public class Utils {

    public static String getValidatedInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static void handleSQLException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace(); // Print stack trace for debugging
    }
}
