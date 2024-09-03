import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Employee extends User {

    public Employee(String name, String email) {
        super(name, email, "Employee");
    }

    @Override
    public void performRoleSpecificAction() {
        System.out.println("Employee-specific actions can be performed here.");
    }

    public void selfAssessment() {
        System.out.println("Performing self-assessment...");
    
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }
    
        try {
            // Retrieve questions for the self-assessment from the database
            // Adjust the query to match your table structure
            String selectQuery = "SELECT * FROM question_bank WHERE technology = 'Java' ORDER BY RAND() LIMIT 10"; // Adjust technology as needed
            PreparedStatement stmt = connection.prepareStatement(selectQuery);
            ResultSet rs = stmt.executeQuery();
    
            int score = 0;
            int totalQuestions = 0;
    
            Scanner scanner = new Scanner(System.in);
    
            while (rs.next()) {
                totalQuestions++;
                String question = rs.getString("question_text");
                System.out.println("Question: " + question);
                System.out.println("A. " + rs.getString("option_a"));
                System.out.println("B. " + rs.getString("option_b"));
                System.out.println("C. " + rs.getString("option_c"));
                System.out.println("D. " + rs.getString("option_d"));
    
                System.out.print("Enter your answer (A/B/C/D): ");
                String userAnswer = scanner.nextLine();
    
                String correctAnswer = rs.getString("correct_answer");
                if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                    score++;
                }
            }
    
            System.out.println("Self-assessment completed!");
            System.out.println("Your score: " + score + "/" + totalQuestions);
    
            // Save the score to a file
            try (FileWriter writer = new FileWriter("SelfAssessment_Result.txt")) {
                writer.write("Score: " + score + "/" + totalQuestions);
                System.out.println("Your score has been saved to 'SelfAssessment_Result.txt'.");
            } catch (IOException e) {
                System.err.println("Error saving self-assessment result to file: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error performing self-assessment: " + e.getMessage());
        }
    }
    
    // Method to submit feedback
    public void submitFeedback(Scanner scanner) {
        System.out.println("Submitting feedback...");
        System.out.print("Enter your feedback: ");
        String feedbackText = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            String insertQuery = "INSERT INTO feedback (user_id, feedback_text) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            stmt.setInt(1, getUserId(getEmail()));
            stmt.setString(2, feedbackText);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Feedback submitted successfully!");
            } else {
                System.out.println("Failed to submit feedback. Please try again.");
            }
        } catch (SQLException e) {
            System.err.println("Error submitting feedback: " + e.getMessage());
        }
    }

    // Method to request a learning plan
    public void requestLearningPlan(Scanner scanner) {
        System.out.println("Requesting learning plan...");
        System.out.print("Enter the desired technology (e.g., Java, Python): ");
        String technology = scanner.nextLine();
        System.out.print("Enter specific areas of improvement: ");
        String areasOfImprovement = scanner.nextLine();
        System.out.print("Enter learning goals: ");
        String learningGoals = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            String insertQuery = "INSERT INTO learning_plan_requests (user_id, technology, areas_of_improvement, learning_goals) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            stmt.setInt(1, getUserId(getEmail()));
            stmt.setString(2, technology);
            stmt.setString(3, areasOfImprovement);
            stmt.setString(4, learningGoals);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Learning plan requested successfully! You will receive a customized plan soon.");
            } else {
                System.out.println("Failed to request learning plan. Please try again.");
            }
        } catch (SQLException e) {
            System.err.println("Error requesting learning plan: " + e.getMessage());
        }
    }

    // Helper method to get user ID from email
    private int getUserId(String email) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            throw new SQLException("Cannot connect to the database.");
        }

        String query = "SELECT id FROM users WHERE email = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("User not found.");
        }
    }
}
