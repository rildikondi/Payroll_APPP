package packaging.mvp.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import packaging.transactionapplication.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionContainerTest {
    private TransactionContainer container;
    private boolean addActionCalled;
    private Transaction transaction;

    @Before
    public void setUp() {
        container = new TransactionContainer(new ArrayList<>());
        container.setAction(this::SillyAddAction);
        transaction = new MockTransaction(null);
    }

    @Test
    public void testConstruction() {
        Assert.assertEquals(0, container.getTransactions().size());
    }

    @Test
    public void testAddingTransaction() {
        container.add(transaction);
        List<Transaction> transactions = container.getTransactions();
        Assert.assertEquals(1, transactions.size());
        Assert.assertSame(transaction, transactions.get(0));
    }

    @Test
    public void testAddingTransactionTriggersDelegate() {
        container.add(transaction);
        Assert.assertTrue(addActionCalled);
    }

    private void SillyAddAction() {
        addActionCalled = true;
    }
}