import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Trainer extends User {

    public Trainer(String name, String email) {
        super(name, email, "Trainer");
    }

    @Override
    public void performRoleSpecificAction() {
        System.out.println("Trainer-specific actions can be performed here.");
    }

    // Method to upload curriculum
    public void uploadCurriculum(Scanner scanner) {
        System.out.println("Uploading curriculum...");
        System.out.print("Enter the curriculum file path: ");
        String filePath = scanner.nextLine();
        System.out.print("Enter the technology (e.g., .NET, Java, Python): ");
        String technology = scanner.nextLine();

        // Process and store the curriculum in the database
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            String insertQuery = "INSERT INTO curriculum (technology, file_path) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            stmt.setString(1, technology);
            stmt.setString(2, filePath);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Curriculum uploaded successfully!");
            } else {
                System.out.println("Failed to upload curriculum. Please try again.");
            }
        } catch (SQLException e) {
            System.err.println("Error uploading curriculum: " + e.getMessage());
        }
    }

    // Method to generate a question bank
    public void generateQuestionBank(Scanner scanner) {
        System.out.println("Generating question bank...");
        System.out.print("Enter the technology (e.g., .NET, Java, Python): ");
        String technology = scanner.nextLine();
        System.out.print("Enter topics (comma-separated): ");
        String topics = scanner.nextLine();
        System.out.print("Enter the number of questions: ");
        int numOfQuestions = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter difficulty level (Easy/Medium/Hard): ");
        String difficulty = scanner.nextLine();

        // Generate and store the question bank in the database
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            String insertQuery = "INSERT INTO question_bank (technology, topics, number_of_questions, difficulty) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            stmt.setString(1, technology);
            stmt.setString(2, topics);
            stmt.setInt(3, numOfQuestions);
            stmt.setString(4, difficulty);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Question bank generated successfully!");
            } else {
                System.out.println("Failed to generate question bank. Please try again.");
            }
        } catch (SQLException e) {
            System.err.println("Error generating question bank: " + e.getMessage());
        }
    }

    // Method to review and edit a question bank
    public void reviewAndEditQuestionBank(Scanner scanner) {
        System.out.println("Reviewing and editing question bank...");

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            // Retrieve all question banks
            String selectQuery = "SELECT * FROM question_bank";
            PreparedStatement stmt = connection.prepareStatement(selectQuery);
            ResultSet rs = stmt.executeQuery();

            // Display all question banks to the trainer
            while (rs.next()) {
                int id = rs.getInt("id");
                String technology = rs.getString("technology");
                String topics = rs.getString("topics");
                int numberOfQuestions = rs.getInt("number_of_questions");
                String difficulty = rs.getString("difficulty");
                System.out.println("ID: " + id + ", Technology: " + technology + ", Topics: " + topics + ", Number of Questions: " + numberOfQuestions + ", Difficulty: " + difficulty);
            }

            // Prompt the trainer to choose a question bank to edit
            System.out.print("Enter the ID of the question bank you want to edit: ");
            int idToEdit = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Prompt for new details
            System.out.print("Enter new technology: ");
            String newTechnology = scanner.nextLine();
            System.out.print("Enter new topics: ");
            String newTopics = scanner.nextLine();
            System.out.print("Enter new number of questions: ");
            int newNumberOfQuestions = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new difficulty level: ");
            String newDifficulty = scanner.nextLine();

            // Update the question bank in the database
            String updateQuery = "UPDATE question_bank SET technology = ?, topics = ?, number_of_questions = ?, difficulty = ? WHERE id = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
            updateStmt.setString(1, newTechnology);
            updateStmt.setString(2, newTopics);
            updateStmt.setInt(3, newNumberOfQuestions);
            updateStmt.setString(4, newDifficulty);
            updateStmt.setInt(5, idToEdit);

            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Question bank updated successfully!");
            } else {
                System.out.println("Failed to update question bank. Please try again.");
            }

        } catch (SQLException e) {
            System.err.println("Error reviewing and editing question bank: " + e.getMessage());
        }
    }

    // Method to download a question bank
    public void downloadQuestionBank(Scanner scanner) {
        System.out.println("Downloading question bank...");

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            // Retrieve all question banks
            String selectQuery = "SELECT * FROM question_bank";
            PreparedStatement stmt = connection.prepareStatement(selectQuery);
            ResultSet rs = stmt.executeQuery();

            // Display all question banks to the trainer
            while (rs.next()) {
                int id = rs.getInt("id");
                String technology = rs.getString("technology");
                String topics = rs.getString("topics");
                int numberOfQuestions = rs.getInt("number_of_questions");
                String difficulty = rs.getString("difficulty");
                System.out.println("ID: " + id + ", Technology: " + technology + ", Topics: " + topics + ", Number of Questions: " + numberOfQuestions + ", Difficulty: " + difficulty);
            }

            // Prompt the trainer to choose a question bank to download
            System.out.print("Enter the ID of the question bank you want to download: ");
            int idToDownload = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Retrieve the selected question bank
            String downloadQuery = "SELECT * FROM question_bank WHERE id = ?";
            PreparedStatement downloadStmt = connection.prepareStatement(downloadQuery);
            downloadStmt.setInt(1, idToDownload);
            ResultSet downloadRs = downloadStmt.executeQuery();

            if (downloadRs.next()) {
                String technology = downloadRs.getString("technology");
                String topics = downloadRs.getString("topics");
                int numberOfQuestions = downloadRs.getInt("number_of_questions");
                String difficulty = downloadRs.getString("difficulty");

                // Save the question bank to a local file
                try (FileWriter writer = new FileWriter("QuestionBank_" + idToDownload + ".txt")) {
                    writer.write("Technology: " + technology + "\n");
                    writer.write("Topics: " + topics + "\n");
                    writer.write("Number of Questions: " + numberOfQuestions + "\n");
                    writer.write("Difficulty: " + difficulty + "\n");
                    System.out.println("Question bank downloaded successfully as 'QuestionBank_" + idToDownload + ".txt'.");
                } catch (IOException e) {
                    System.err.println("Error saving question bank to file: " + e.getMessage());
                }
            } else {
                System.out.println("Question bank not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error downloading question bank: " + e.getMessage());
        }
    }
}
