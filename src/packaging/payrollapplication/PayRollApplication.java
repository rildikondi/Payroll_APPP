package packaging.payrollapplication;

import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldatabaseimplementation.PayrollDatabaseImplementation;
import packaging.payrollfactory.PayrollFactory;
import packaging.payrollimplementation.PayrollFactoryImplementation;
import packaging.textparsertrasactionsource.TextParserTransactionSource;
import packaging.transactionapplication.TransactionApplication;
import packaging.transactionfactory.TransactionFactory;
import packaging.transactionimplementation.TransactionFactoryImplementation;

public class PayRollApplication {

    public static void main(String[] args) {
        PayrollDatabase.globalPayrollDatabase = new PayrollDatabaseImplementation();
        TransactionFactory.transactionFactory = new TransactionFactoryImplementation();
        PayrollFactory.payrollFactory = new PayrollFactoryImplementation();
        TextParserTransactionSource source = new TextParserTransactionSource(TransactionFactory.transactionFactory, null);
        TransactionApplication app = new TransactionApplication(source);
        app.run();


//        boolean flag = true;
//        while (flag) {
//            nopackaging.TransactionSource transactionSource = new nopackaging.TextParserTransactionSource();
//            nopackaging.Transaction transaction = transactionSource.getTransaction();
//            transaction.execute();
//            flag = false;
//        }
    }
}
