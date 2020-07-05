package packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeClassificationTransaction;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {
    private final double salary;
    private final double commissionRate;

    public ChangeCommissionedTransaction(int empId, double salary, double commissionRate) {
        super(empId);
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
