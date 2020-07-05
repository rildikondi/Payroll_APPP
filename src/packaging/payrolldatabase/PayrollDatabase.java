package packaging.payrolldatabase;

import packaging.payrolldomain.Employee;

import java.util.List;

public abstract class PayrollDatabase {

    public static PayrollDatabase globalPayrollDatabase;

    public abstract void addEmployee(int id, Employee employee);

    public abstract Employee getEmployee(int id);

    public abstract void deleteEmployee(int empId);

    public abstract void addUnionMember(int memberId, Employee e);

    public abstract Employee getUnionMember(int memberId);

    public abstract void removeUnionMember(int memberId);

    public abstract List<Integer> getAllEmployeeIds();
}
