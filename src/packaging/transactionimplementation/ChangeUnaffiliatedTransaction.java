package packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeAffiliationTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Affiliation;
import packaging.payrolldomain.Employee;
import packaging.payrollimplementation.UnionAffiliation;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {

    public ChangeUnaffiliatedTransaction(int empId) {
        super(empId);
    }

    @Override
    protected void recordMembership(Employee e) {
        Affiliation affiliation = e.getAffiliation();
        if (affiliation instanceof UnionAffiliation) {
            UnionAffiliation unionAffiliation = (UnionAffiliation) affiliation;
            int memberId = unionAffiliation.getMemberId();
            PayrollDatabase.globalPayrollDatabase.removeUnionMember(memberId);
        }
    }

    @Override
    protected Affiliation getAffiliation() {
        return Affiliation.NULL;
    }
}
