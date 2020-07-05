package packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeClassificationTransaction;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {
    private final double salary;

    public ChangeSalariedTransaction(int empId, double salary) {
        super(empId);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification getClassification() {
        return payrollFactory.makeSalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return payrollFactory.makeMonthlySchedule();
    }
}
