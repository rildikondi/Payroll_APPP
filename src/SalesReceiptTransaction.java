import java.awt.dnd.InvalidDnDOperationException;
import java.util.Date;

public class SalesReceiptTransaction implements Transaction {

    private final Date date;
    private final double amount;
    private final int empId;

    public SalesReceiptTransaction(Date date, double amount, int empId) {
        this.date = date;
        this.amount = amount;
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);
        if (e != null) {
            CommissionedClassification commissionedClassification = (CommissionedClassification) e.getPaymentClassification();
            if (commissionedClassification != null)
                commissionedClassification.addSalesReceipt(new SalesReceipt(date, amount));
            else
                throw new InvalidDnDOperationException(
                        "Tried to add timecard to" +
                                "non-hourly employee");
        } else
            throw new InvalidDnDOperationException(
                    "No such employee.");
    }
}

