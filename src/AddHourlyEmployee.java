public class AddHourlyEmployee  extends AddEmployeeTransaction{
    private final double hourlyRate;

    public AddHourlyEmployee(int id, String name, String address, double hourlyRate) {
        super(id, name, address);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return new HourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return new WeeklySchedule();
    }
}
