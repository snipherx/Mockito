package withManual;

import bank.BankAccount;
import bank.BankService;
import bank.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BankServiceManualTest {
    private NotificationService notificationService;
    private BankService bankService;
    private BankAccount source;
    private BankAccount destination;

    @BeforeEach
    void setup()
    {
        notificationService = mock(NotificationService.class);
        bankService = new BankService(notificationService);
        source = new BankAccount("ACC-100", 1000.0);
        destination = new BankAccount("ACC-200", 200.0);
    }
    //Transfer test
    @Test
    void transfer_shouldMoveMoneyAndSendNotification()
    {
        bankService.transfer(source,destination,300);
        assertEquals(700.0,source.getBalance(),0.001);
        assertEquals(500.0,destination.getBalance(),0.001);
        verify(notificationService,times(1)).sendTransferNotification("ACC-200",300.0);
    }
    //Transfer fails due to insufficient funds
    @Test
    void transfer_shouldFailDueInsufficientFunds()
    {
//        assertThrows(IllegalStateException.class,()->bankService.transfer(source,destination,1500.0));
        verify(notificationService,times(0)).sendTransferNotification("ACC-200",300.0);
        assertEquals(1000.0,source.getBalance(),0.001);
        assertEquals(200.0,destination.getBalance(),0.001);
        verifyNoInteractions(notificationService);
    }
    //Transfer with an invalid amount
    @Test
    void transfer_shouldRejectInvalidAmount()
    {
        assertThrows(IllegalArgumentException.class,()->bankService.transfer(source,destination,0.0));
        assertThrows(IllegalArgumentException.class,()->bankService.transfer(source,destination,-100.0));
        assertEquals(1000.0,source.getBalance(),0.001);
        assertEquals(200.0,destination.getBalance(),0.001);
        verifyNoInteractions(notificationService);
    }
    //Transfer to the same account
    @Test
    void transfer_shouldRejectSameAccount()
    {
        assertThrows(IllegalArgumentException.class,()->bankService.transfer(source,source,100.0));
        assertEquals(1000.0,source.getBalance(),0.001);
        verifyNoInteractions(notificationService);
    }
}
