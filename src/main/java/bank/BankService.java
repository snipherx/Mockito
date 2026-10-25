package bank;

public class BankService {
    private final NotificationService notificationService;

    public BankService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void transfer(BankAccount from, BankAccount to, double amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Both accounts are required");
        }
        if (from == to) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        // Withdraw first. If this fails, the destination is not credited.
        from.withdraw(amount);
        to.deposit(amount);

        notificationService.sendTransferNotification(to.getAccountNumber(), amount);
    }
}
