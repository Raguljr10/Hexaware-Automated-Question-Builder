import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin extends User {

    public Admin(String name, String email) {
        super(name, email, "Admin");
    }

    @Override
    public void performRoleSpecificAction() {
        System.out.println("Admin-specific actions can be performed here.");
    }

    // Method to manage users: add, remove, or update users
    public void manageUsers(Scanner scanner) {
        boolean managing = true;
        while (managing) {
            System.out.println("\nManage Users:");
            System.out.println("1. Add New User");
            System.out.println("2. Remove User");
            System.out.println("3. Update User Role");
            System.out.println("4. Back to Admin Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addUser(scanner);
                    break;
                case 2:
                    removeUser(scanner);
                    break;
                case 3:
                    updateUserRole(scanner);
                    break;
                case 4:
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to add a new user
    private void addUser(Scanner scanner) {
        System.out.print("Enter new user's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new user's email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new user's password: ");
        String password = scanner.nextLine();
        System.out.print("Enter new user's role (Admin/Trainer/Employee): ");
        String role = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            String insertQuery = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User added successfully!");
            } else {
                System.out.println("Failed to add user. Please try again.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    // Method to remove a user
    private void removeUser(Scanner scanner) {
        System.out.print("Enter the email of the user to be removed: ");
        String email = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            String deleteQuery = "DELETE FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);
            stmt.setString(1, email);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User removed successfully!");
            } else {
                System.out.println("User not found or could not be removed.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing user: " + e.getMessage());
        }
    }

    // Method to update user role
    private void updateUserRole(Scanner scanner) {
        System.out.print("Enter the email of the user whose role is to be updated: ");
        String email = scanner.nextLine();
        System.out.print("Enter the new role (Admin/Trainer/Employee): ");
        String newRole = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Cannot connect to the database. Please try again later.");
            return;
        }

        try {
            String updateQuery = "UPDATE users SET role = ? WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            stmt.setString(1, newRole);
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User role updated successfully!");
            } else {
                System.out.println("User not found or could not be updated.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating user role: " + e.getMessage());
        }
    }

    // Method to monitor system performance
    public void monitorSystemPerformance() {
        System.out.println("Monitoring system performance...");
        // Example: Print dummy performance metrics
        System.out.println("Server Status: Online");
        System.out.println("Active Users: 10");
        System.out.println("Database Queries per Second: 20");
    }

    // Method to generate reports
    public void generateReports(Scanner scanner) {
        System.out.println("Generating reports...");
        // Example: Provide options for different types of reports
        System.out.println("1. Usage Statistics");
        System.out.println("2. Question Bank Generation Summaries");
        System.out.println("3. System Health Reports");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Generating usage statistics report...");
                // Implement report generation logic here
                break;
            case 2:
                System.out.println("Generating question bank generation summaries...");
                // Implement report generation logic here
                break;
            case 3:
                System.out.println("Generating system health report...");
                // Implement report generation logic here
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Method to resolve issues
    public void resolveIssues(Scanner scanner) {
        System.out.println("Resolving issues...");
        System.out.print("Enter the issue ID or description to investigate: ");
        String issueDescription = scanner.nextLine();
        // Example logic to resolve issues
        System.out.println("Investigating issue: " + issueDescription);
        System.out.println("Issue resolved successfully or escalated to technical support.");
    }
}
