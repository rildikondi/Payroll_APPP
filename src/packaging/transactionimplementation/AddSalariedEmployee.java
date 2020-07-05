package packaging.transactionimplementation;

import packaging.abstracttransactions.AddEmployeeTransaction;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class AddSalariedEmployee extends AddEmployeeTransaction {
    private final double salary;

    public AddSalariedEmployee(int id, String name, String address, double salary){
        super(id, name, address);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return payrollFactory.makeSalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return payrollFactory.makeMonthlySchedule();
    }
}
