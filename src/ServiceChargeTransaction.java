import java.awt.dnd.InvalidDnDOperationException;
import java.util.Date;

public class ServiceChargeTransaction implements Transaction {
    private final int memberId;
    private final Date date;
    private final double charge;

    public ServiceChargeTransaction(int memberId, Date date, double charge) {
        this.memberId = memberId;
        this.date = date;
        this.charge = charge;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getUnionMember(memberId);
        if (e != null) {
            if (e.getAffiliation() instanceof UnionAffiliation)
                e.getAffiliation().addServiceCharge(new ServiceCharge(date, charge));
            else
                throw new InvalidDnDOperationException(
                        "Tries to add service charge to union"
                                + "member without a union affiliation");
        } else
            throw new InvalidDnDOperationException(
                    "No such union member.");
    }
}
