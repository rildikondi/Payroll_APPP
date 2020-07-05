package packaging.payrolldatabaseimplementation;

import packaging.payrolldomain.Employee;
import packaging.payrolldatabase.PayrollDatabase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class PayrollDatabaseImplementation extends PayrollDatabase {
    private  Hashtable<Integer, Employee> employees = new Hashtable<>();
    private  Hashtable<Integer, Employee> unionMembers = new Hashtable<>();

    @Override
    public  void addEmployee(int id, Employee employee) {
        employees.put(id, employee);
    }

    @Override
    public Employee getEmployee(int id) {
        return employees.get(id);
    }

    @Override
    public void deleteEmployee(int empId) {
        employees.remove(empId);
    }

    @Override
    public void addUnionMember(int memberId, Employee e) {
        unionMembers.put(memberId, e);
    }

    @Override
    public Employee getUnionMember(int memberId) {
        return unionMembers.get(memberId);
    }

    @Override
    public void removeUnionMember(int memberId) {
        unionMembers.remove(memberId);
    }

    @Override
    public List<Integer> getAllEmployeeIds() {
        return new ArrayList<>(employees.keySet());
    }
}
