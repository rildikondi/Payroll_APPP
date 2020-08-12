package packaging.payrollapplication;

import packaging.application.Application;
import packaging.mvp.view.WindowViewLoader;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldatabaseimplementation.InMemoryPayrollDatabase;
import packaging.payrollfactory.PayrollFactory;
import packaging.payrollimplementation.PayrollFactoryImplementation;
import packaging.textparsertrasactionsource.TextParserTransactionSource;
import packaging.transactionapplication.TransactionApplication;
import packaging.transactionfactory.TransactionFactory;
import packaging.transactionimplementation.TransactionFactoryImplementation;

public class PayRollApplication {

    public static void main(String[] args) {
//        PayrollDatabase payrollDatabase = new InMemoryPayrollDatabase();
//        TransactionFactory.transactionFactory = new TransactionFactoryImplementation(payrollDatabase);
//        PayrollFactory.payrollFactory = new PayrollFactoryImplementation();
//        TextParserTransactionSource source = new TextParserTransactionSource(TransactionFactory.transactionFactory, null);
//        TransactionApplication app = new TransactionApplication(source);
//        app.run();

        PayrollDatabase database = new InMemoryPayrollDatabase();
        TransactionFactory.transactionFactory = new TransactionFactoryImplementation(database);
        PayrollFactory.payrollFactory = new PayrollFactoryImplementation();
        WindowViewLoader viewLoader = new WindowViewLoader(database);
        viewLoader.loadPayrollView();
    }
}
