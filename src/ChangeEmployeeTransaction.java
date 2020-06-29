import java.awt.dnd.InvalidDnDOperationException;

public abstract class ChangeEmployeeTransaction implements Transaction {
    private final int empId;

    public ChangeEmployeeTransaction(int empId) {
        this.empId = empId;
    }


    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);
        if (e != null)
            change(e);
        else
            throw new InvalidDnDOperationException(
                    "No such employee.");
    }

    protected abstract void change(Employee e);
}