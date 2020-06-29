public class SalariedClassification extends PaymentClassification {
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
