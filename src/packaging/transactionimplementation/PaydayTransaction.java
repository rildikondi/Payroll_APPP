package packaging.transactionimplementation;

import packaging.payrolldomain.Paycheck;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Employee;
import packaging.transactionapplication.Transaction;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaydayTransaction  implements Transaction {
    private Date payDate;
    private Map<Integer, Paycheck> paychecks = new HashMap<>();

    public PaydayTransaction(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    public void execute() {
        List<Integer> employeeIds =  PayrollDatabase.globalPayrollDatabase.getAllEmployeeIds();
        for (int empId : employeeIds) {
            Employee employee =  PayrollDatabase.globalPayrollDatabase.getEmployee(empId);
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
