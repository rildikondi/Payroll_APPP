package packaging.mvp.presenter;

import packaging.payrolldatabase.PayrollDatabase;
import packaging.transactionapplication.Transaction;

public class MockTransaction extends Transaction {

    public boolean wasExecuted;

    public MockTransaction(PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
    }

    @Override
    public void execute() {
        wasExecuted = true;
    }
}
