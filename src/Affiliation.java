import java.util.Date;

public abstract class Affiliation {

    public  abstract ServiceCharge getServiceCharge(Date date);

    public abstract void addServiceCharge(ServiceCharge serviceCharge);

    public static final Affiliation NULL = new NoAffiliation();

    public abstract double calculateDeductions(Paycheck paycheck);

    private static class NoAffiliation extends Affiliation {

        @Override
        public ServiceCharge getServiceCharge(Date date) {
            return new ServiceCharge(date, 0.00);
        }

        @Override
        public void addServiceCharge(ServiceCharge serviceCharge) {

        }

        @Override
        public double calculateDeductions(Paycheck paycheck) {
            return 0.00;
        }
    }
}
