public class TextParserTransactionSource extends TransactionSource {
    @Override
    Transaction getTransaction() {
        return new Transaction() {
            @Override
            public void execute() {

            }
        };
    }

    //Read from stream
}
