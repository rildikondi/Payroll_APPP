package packaging.payrollimplementation;


import packaging.payrolldomain.Paycheck;
import packaging.payrolldomain.PaymentClassification;

public class SalariedClassification implements PaymentClassification {
    private double salary;

    public SalariedClassification(double salary) {
        super();
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public double calculatePay(Paycheck paycheck) {
        return salary;
    }
}
