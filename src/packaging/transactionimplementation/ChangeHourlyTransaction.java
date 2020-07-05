package packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeClassificationTransaction;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {
    private final double hourlyRate;

    public ChangeHourlyTransaction(int id, double hourlyRate) {
        super(id);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return payrollFactory.makeHourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return payrollFactory.makeWeeklySchedule();
    }
}
