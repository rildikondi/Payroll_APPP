package packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeClassificationTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {
    private final double hourlyRate;

    public ChangeHourlyTransaction(int id, double hourlyRate, PayrollDatabase payrollDatabase) {
        super(id, payrollDatabase);
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
