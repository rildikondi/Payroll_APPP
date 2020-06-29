import java.util.*;

public class PaydayTransaction  implements Transaction{
    private Date payDate;
    private Map<Integer, Paycheck> paychecks = new HashMap<>();

    public PaydayTransaction(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    public void execute() {
        List<Integer> employeeIds = PayrollDatabase.getAllEmployeeIds();
        for (int empId : employeeIds) {
            Employee employee = PayrollDatabase.getEmployee(empId);
            if (employee.isPayDate(payDate)) {
                Date startDate = employee.getPayPeriodStartDate(payDate);
                Paycheck pc = new Paycheck(startDate, payDate);
                paychecks.put(empId, pc);
                employee.payday(pc);
            }
        }
    }

    public Paycheck getPaycheck(int empId) {
        return paychecks.get(empId);
    }
}
