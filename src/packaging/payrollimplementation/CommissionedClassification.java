import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommissionedClassification extends PaymentClassification {
    private double salary;
    private double commissionRate;
    private Map<Date, SalesReceipt> salesReceiptMap = new HashMap<>();

    public CommissionedClassification(double salary, double commissionRate) {
        super();
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    public double getSalary() {
        return salary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceiptMap.put(salesReceipt.getDate(), salesReceipt);
    }

    public SalesReceipt getSalesReceipt(Date date) {
        return salesReceiptMap.get(date);
    }

    @Override
    public double calculatePay(Paycheck paycheck) {
        double totalSales = 0.00;
        for (SalesReceipt salesReceipt : salesReceiptMap.values()) {
            if (DateUtil.isInPayPeriod(salesReceipt.getDate(), paycheck.getPayPeriodStartDate(), paycheck.getPayPeriodEndDate())) {
                totalSales += salesReceipt.getAmount();
            }
        }
        return salary/2 + totalSales * commissionRate;
    }

//    private boolean isInPayPeriod(SalesReceipt salesReceipt, Date payPeriod) {
//        Date payPeriodEndDate = payPeriod;
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(payPeriod);
//        cal.add(Calendar.DAY_OF_MONTH, -14);
//        Date payPeriodStartDate = Date.from(cal.toInstant());
//        return salesReceipt.getDate().getTime() <= payPeriodEndDate.getTime()  &&
//                salesReceipt.getDate().getTime() >= payPeriodStartDate.getTime();
//    }
}
