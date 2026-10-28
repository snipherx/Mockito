package bank;

public interface NotificationService {
    void sendTransferNotification(String accountNumber, double amount);
    boolean isNotificationEnabled(String accountNumber);
}
