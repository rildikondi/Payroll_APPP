package packaging.abstracttransactions;

import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Affiliation;
import packaging.payrolldomain.Employee;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {
    
    public ChangeAffiliationTransaction(int empId, PayrollDatabase database) {
        super(empId, database);
    }

    @Override
    protected void change(Employee e) {
        recordMembership(e);
        e.setAffiliation(getAffiliation());
    }

    protected abstract void recordMembership(Employee e);

    protected abstract Affiliation getAffiliation();
}
