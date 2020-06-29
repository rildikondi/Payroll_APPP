import javax.swing.text.html.ListView;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class PayrollDatabase {
    private static Hashtable<Integer, Employee> employees = new Hashtable<>();
    private static Hashtable<Integer, Employee> unionMembers = new Hashtable<>();

    public static void addEmployee(int id, Employee employee) {
        employees.put(id, employee);
    }

    public static Employee getEmployee(int id) {
        return employees.get(id);
    }

    public static void deleteEmployee(int empId) {
        employees.remove(empId);
    }

    public static void addUnionMember(int memberId, Employee e) {
        unionMembers.put(memberId, e);
    }

    public static Employee getUnionMember(int memberId) {
        return unionMembers.get(memberId);
    }

    public static void removeUnionMember(int memberId) {
        unionMembers.remove(memberId);
    }

    public static List<Integer> getAllEmployeeIds() {
       return new ArrayList<>(employees.keySet());
    }
}
