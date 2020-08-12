package packaging.abstracttransactions;

import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Employee;
import packaging.payrolldomain.PaymentMethod;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {

    public ChangeMethodTransaction(int empId, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
    }

    @Override
    protected void change(Employee e) {
        e.setMethod(getMethod());
    }

    protected abstract PaymentMethod getMethod();
}
