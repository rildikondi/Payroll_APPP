package packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeMethodTransaction;
import packaging.payrolldomain.PaymentMethod;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeHoldTransaction extends ChangeMethodTransaction {
    public ChangeHoldTransaction(int empId) {
        super(empId);
    }

    @Override
    protected PaymentMethod getMethod() {
        return payrollFactory.makeHoldMethod();
    }
}
