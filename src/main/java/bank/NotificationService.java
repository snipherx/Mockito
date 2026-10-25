package bank;

public interface NotificationService {
    void sendTransferNotification(String accountNumber, double amount);
}
