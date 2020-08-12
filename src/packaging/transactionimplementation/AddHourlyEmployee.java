package packaging.transactionimplementation;

import packaging.abstracttransactions.AddEmployeeTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class AddHourlyEmployee extends AddEmployeeTransaction {
    private final double hourlyRate;

    public AddHourlyEmployee(int id, String name, String address, double hourlyRate, PayrollDatabase payrollDatabase) {
        super(id, name, address, payrollDatabase);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return payrollFactory.makeHourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return payrollFactory.makeWeeklySchedule();
    }
}
