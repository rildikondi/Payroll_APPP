public abstract class AddEmployeeTransaction implements Transaction {
    private final int employerId;
    private final String name;
    private final String address;

    public AddEmployeeTransaction(int employerId, String name, String address) {
        this.employerId = employerId;
        this.name = name;
        this.address = address;
    }

    protected abstract PaymentClassification makeClassification();

    protected abstract PaymentSchedule makeSchedule();

    @Override
    public void execute() {
        PaymentClassification paymentClassification = makeClassification();
        PaymentSchedule paymentSchedule = makeSchedule();
        PaymentMethod paymentMethod = new HoldMethod();
        Employee e = new Employee(employerId, name, address);
        e.setClassification(paymentClassification);
        e.setSchedule(paymentSchedule);
        e.setMethod(paymentMethod);
        PayrollDatabase.addEmployee(employerId, e);
    }
}
