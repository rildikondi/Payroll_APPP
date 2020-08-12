package packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeClassificationTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {
    private final double salary;
    private final double commissionRate;

    public ChangeCommissionedTransaction(int empId, double salary, double commissionRate, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return payrollFactory.makeCommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return payrollFactory.makeBiweeklySchedule();
    }
}
