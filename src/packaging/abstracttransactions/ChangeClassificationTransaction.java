package packaging.abstracttransactions;

import packaging.payrolldomain.Employee;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

    public ChangeClassificationTransaction(int id) {
        super(id);
    }

    @Override
    protected void change(Employee e) {
        e.setClassification(getClassification());
        e.setSchedule(getSchedule());
    }

    protected abstract PaymentClassification getClassification();


    protected abstract PaymentSchedule getSchedule();
}
