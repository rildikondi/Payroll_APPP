package packaging.transactionimplementation;

import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Employee;
import packaging.payrollimplementation.HourlyClassification;
import packaging.transactionapplication.Transaction;

import java.awt.dnd.InvalidDnDOperationException;
import java.util.Date;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class TimeCardTransaction implements Transaction {

    private final Date date;
    private final double hours;
    private final int empId;

    public TimeCardTransaction(Date date, double hours, int empId) {
        this.date = date;
        this.hours = hours;
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.globalPayrollDatabase.getEmployee(empId);
        if (e != null) {
            HourlyClassification hc = (HourlyClassification) e.getPaymentClassification();
            if (hc != null)
                hc.addTimeCard(payrollFactory.makeTimeCard(date, hours));
            else
                throw new InvalidDnDOperationException(
                        "Tried to add timecard to" +
                                "non-hourly employee");
        } else
            throw new InvalidDnDOperationException(
                    "No such employee.");
    }
}
