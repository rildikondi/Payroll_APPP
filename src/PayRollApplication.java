public class PayRollApplication {

    public static void main(String[] args) {
        boolean flag = true;
        while (flag) {
            TransactionSource transactionSource = new TextParserTransactionSource();
            Transaction transaction = transactionSource.getTransaction();
            transaction.execute();
            flag = false;
        }
    }
}
