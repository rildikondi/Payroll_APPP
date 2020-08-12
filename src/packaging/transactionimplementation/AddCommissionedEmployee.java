package packaging.transactionimplementation;

import packaging.abstracttransactions.AddEmployeeTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;
import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class AddCommissionedEmployee extends AddEmployeeTransaction {
    private double salary;
    private double commissionRate;

    public AddCommissionedEmployee(int empId, String name, String address, double salary, double commissionRate, PayrollDatabase payrollDatabase) {
        super(empId, name, address, payrollDatabase);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return  payrollFactory.makeCommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return payrollFactory.makeBiweeklySchedule();
    }
}
