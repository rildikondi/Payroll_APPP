package packaging.transactionimplementation;

import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Employee;
import packaging.payrollimplementation.UnionAffiliation;
import packaging.transactionapplication.Transaction;

import java.awt.dnd.InvalidDnDOperationException;
import java.util.Date;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ServiceChargeTransaction extends Transaction {
    private final int memberId;
    private final Date date;
    private final double charge;

    public ServiceChargeTransaction(int memberId, Date date, double charge, PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
        this.memberId = memberId;
        this.date = date;
        this.charge = charge;
    }

    @Override
    public void execute() {
        Employee e =  payrollDatabase.getUnionMember(memberId);
        if (e != null) {
            if (e.getAffiliation() instanceof UnionAffiliation)
                e.getAffiliation().addServiceCharge(payrollFactory.makeServiceCharge(date, charge));
            else
                throw new InvalidDnDOperationException(
                        "Tries to add service charge to union"
                                + "member without a union affiliation");
        } else
            throw new InvalidDnDOperationException(
                    "No such union member.");
    }
}
