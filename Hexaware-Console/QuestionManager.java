import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class QuestionManager {
    public void createQuestion(Scanner scanner, User user) {
        String questionText = Utils.getValidatedInput(scanner, "Enter question text: ");
        String optionA = Utils.getValidatedInput(scanner, "Enter option A: ");
        String optionB = Utils.getValidatedInput(scanner, "Enter option B: ");
        String optionC = Utils.getValidatedInput(scanner, "Enter option C: ");
        String optionD = Utils.getValidatedInput(scanner, "Enter option D: ");
        String correctAnswer = Utils.getValidatedInput(scanner, "Enter correct answer (A/B/C/D): ");
        String difficulty = Utils.getValidatedInput(scanner, "Enter difficulty level (Easy/Medium/Hard): ");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_answer, difficulty, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, questionText);
            stmt.setString(2, optionA);
            stmt.setString(3, optionB);
            stmt.setString(4, optionC);
            stmt.setString(5, optionD);
            stmt.setString(6, correctAnswer);
            stmt.setString(7, difficulty);
            stmt.setInt(8, getUserId(user.getEmail()));

            stmt.executeUpdate();
            System.out.println("Question created successfully!");
        } catch (SQLException e) {
            Utils.handleSQLException("Error creating question", e);
        }
    }

    public void viewQuestions() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM questions";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Question: " + rs.getString("question_text"));
                System.out.println("Options: A) " + rs.getString("option_a") + ", B) " + rs.getString("option_b") + ", C) " + rs.getString("option_c") + ", D) " + rs.getString("option_d"));
                System.out.println("Correct Answer: " + rs.getString("correct_answer"));
                System.out.println("Difficulty: " + rs.getString("difficulty"));
                System.out.println("-----");
            }
        } catch (SQLException e) {
            Utils.handleSQLException("Error viewing questions", e);
        }
    }

    private int getUserId(String email) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        throw new SQLException("User not found.");
    }
}
