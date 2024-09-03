import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthenticationService authService = new AuthenticationService();

        // Obtain a database connection
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Exiting application due to database connection failure.");
            scanner.close();
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("Select your role:");
            System.out.println("1. Administrator");
            System.out.println("2. Trainer");
            System.out.println("3. Employee");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleAdminFlow(scanner, authService);
                    break;
                case 2:
                    handleTrainerFlow(scanner, authService);
                    break;
                case 3:
                    handleEmployeeFlow(scanner, authService);
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        // Close the database connection
        DatabaseConnection.closeConnection();
        scanner.close();
    }

    private static void handleAdminFlow(Scanner scanner, AuthenticationService authService) {
        System.out.println("Administrator Flow:");
        User admin = promptForSignInOrSignUp(scanner, authService, "Admin");
        if (admin != null) {
            Admin adminObj = (Admin) admin;
            boolean loggedIn = true;
            while (loggedIn) {
                System.out.println("\nAdministrator Menu:");
                System.out.println("1. Manage Users");
                System.out.println("2. Monitor System Performance");
                System.out.println("3. Generate Reports");
                System.out.println("4. Issue Resolution");
                System.out.println("5. Logout");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        adminObj.manageUsers(scanner);
                        break;
                    case 2:
                        adminObj.monitorSystemPerformance();
                        break;
                    case 3:
                        adminObj.generateReports(scanner);
                        break;
                    case 4:
                        adminObj.resolveIssues(scanner);
                        break;
                    case 5:
                        loggedIn = false;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static void handleTrainerFlow(Scanner scanner, AuthenticationService authService) {
        System.out.println("Trainer Flow:");
        User trainer = promptForSignInOrSignUp(scanner, authService, "Trainer");
        if (trainer != null) {
            Trainer trainerObj = (Trainer) trainer;
            boolean loggedIn = true;
            while (loggedIn) {
                System.out.println("\nTrainer Menu:");
                System.out.println("1. Upload Curriculum");
                System.out.println("2. Generate Question Bank");
                System.out.println("3. Review and Edit Question Bank");
                System.out.println("4. Download Question Bank");
                System.out.println("5. Logout");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        trainerObj.uploadCurriculum(scanner);
                        break;
                    case 2:
                        trainerObj.generateQuestionBank(scanner);
                        break;
                    case 3:
                        trainerObj.reviewAndEditQuestionBank(scanner); // Corrected line: Pass scanner to the method
                        break;
                    case 4:
                        trainerObj.downloadQuestionBank(scanner);
                        break;
                    case 5:
                        loggedIn = false;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static void handleEmployeeFlow(Scanner scanner, AuthenticationService authService) {
        System.out.println("Employee Flow:");
        User employee = promptForSignInOrSignUp(scanner, authService, "Employee");
        if (employee != null) {
            Employee employeeObj = (Employee) employee;
            boolean loggedIn = true;
            while (loggedIn) {
                System.out.println("\nEmployee Menu:");
                System.out.println("1. Self-Assessment");
                System.out.println("2. Submit Feedback");
                System.out.println("3. Request Learning Plan");
                System.out.println("4. Logout");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        employeeObj.selfAssessment();
                        break;
                    case 2:
                        employeeObj.submitFeedback(scanner);
                        break;
                    case 3:
                        employeeObj.requestLearningPlan(scanner);
                        break;
                    case 4:
                        loggedIn = false;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static User promptForSignInOrSignUp(Scanner scanner, AuthenticationService authService, String role) {
        while (true) {
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    return authService.login(scanner, role);
                case 2:
                    authService.registerUser(scanner, role);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
