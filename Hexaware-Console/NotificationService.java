

public class NotificationService {
    public void sendNotification(User user, String message) {
        System.out.println("Notification sent to " + user.getEmail() + ": " + message);
    }
}
