package packaging.abstracttransactions;

import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Employee;
import packaging.transactionapplication.Transaction;

import java.awt.dnd.InvalidDnDOperationException;

public abstract class ChangeEmployeeTransaction extends Transaction {
    private final int empId;

    public ChangeEmployeeTransaction(int empId, PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e =  payrollDatabase.getEmployee(empId);
        if (e != null)
            change(e);
        else
            throw new InvalidDnDOperationException(
                    "No such employee.");
    }

    protected abstract void change(Employee e);
}
