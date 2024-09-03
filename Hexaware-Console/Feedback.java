import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Feedback {

    public void submitFeedback(Scanner scanner, Employee employee) {
        System.out.println("Submit Feedback:");
        String feedback = Utils.getValidatedInput(scanner, "Enter your feedback: ");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                System.out.println("Cannot connect to the database. Please try again later.");
                return;
            }

            String query = "INSERT INTO feedback (employee_email, feedback) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, employee.getEmail());
                stmt.setString(2, feedback);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Feedback submitted successfully!");
                } else {
                    System.out.println("Failed to submit feedback.");
                }
            } catch (SQLException e) {
                Utils.handleSQLException("Error while submitting feedback", e);
            }
        } catch (SQLException e) {
            Utils.handleSQLException("Error while getting database connection", e);
        }
    }

    // Add additional methods related to feedback management here if needed
}
