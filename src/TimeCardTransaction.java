import java.awt.dnd.InvalidDnDOperationException;
import java.util.Date;

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
        Employee e = PayrollDatabase.getEmployee(empId);
        if (e != null) {
            HourlyClassification hc = (HourlyClassification) e.getPaymentClassification();
            if (hc != null)
                hc.addTimeCard(new TimeCard(date, hours));
            else
                throw new InvalidDnDOperationException(
                        "Tried to add timecard to" +
                                "non-hourly employee");
        } else
            throw new InvalidDnDOperationException(
                    "No such employee.");
    }
}
