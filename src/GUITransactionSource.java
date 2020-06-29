public class GUITransactionSource extends TransactionSource {
    @Override
    Transaction getTransaction() {
        return new Transaction() {
            @Override
            public void execute() {

            }
        };
    }

    //read from UI
}
