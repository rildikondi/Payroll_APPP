package packaging.guitransactionsource;

import packaging.transactionapplication.Transaction;
import packaging.transactionapplication.TransactionSource;

public class GUITransactionSource implements TransactionSource {
    @Override
    public Transaction getTransaction() {
        return new Transaction() {
            @Override
            public void execute() {

            }
        };
    }

    //read from UI
}
