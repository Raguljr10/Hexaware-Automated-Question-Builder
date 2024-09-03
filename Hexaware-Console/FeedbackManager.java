import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class FeedbackManager {

    public void submitFeedback(Scanner scanner, Employee employee) {
        System.out.print("Enter feedback: ");
        String feedback = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            String query = "INSERT INTO feedback (email, feedback) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, employee.getEmail());
            stmt.setString(2, feedback);

            stmt.executeUpdate();
            System.out.println("Feedback submitted successfully!");
        } catch (SQLException e) {
            System.err.println("Error submitting feedback: " + e.getMessage());
        }
    }
}
