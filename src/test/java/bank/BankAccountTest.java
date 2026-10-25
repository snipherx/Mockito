package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {
    private BankAccount account;
    @BeforeEach
    void bankAccountSetup()
    {
        account = new BankAccount("ACC-100",1000.0);
    }
    @Test
    void constructor_setsAccountNumber()
    {
        assertEquals("ACC-100",account.getAccountNumber());
    }
    @Test
    void constructor_setsOpeningBalance()
    {
        assertEquals(1000.0,account.getBalance(),0.001);
    }
    // ================= Positive Test Cases =================
    @Test
    void deposit_updatesBalance()
    {
        account.deposit(250.0);
        assertEquals(1250.0,account.getBalance(),0.001);
    }
    @Test
    void withdraw_updatesBalance()
    {
        account.withdraw(300.0);
        assertEquals(700.0,account.getBalance(),0.001);
    }
    // ================= Negative Test Cases =================
    @Test
    void withdraw_insufficientBalance()
    {
        assertThrows(IllegalStateException.class,()->account.withdraw(1500.0));
        assertEquals(1000.0,account.getBalance(),0.001);
    }
    @Test
    void withdraw_invalidAmount()
    {
        assertThrows(IllegalArgumentException.class,()->account.withdraw(0.0));
        assertThrows(IllegalArgumentException.class,()->account.withdraw(-100.0));
        assertEquals(1000.0,account.getBalance(),0.001);
    }
    @Test
    void deposit_invalidAmount()
    {
        assertThrows(IllegalArgumentException.class,()->account.deposit(0.0));
        assertThrows(IllegalArgumentException.class,()->account.deposit(-100.0));
        assertEquals(1000.0,account.getBalance(),0.001);
    }
    @Test
    void constructor_negativeOpeningBalance()
    {
        assertThrows(IllegalArgumentException.class,()->new BankAccount("ACC-200",-1.0));
    }
}