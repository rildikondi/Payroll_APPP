package packaging.payrollimplementation;

import packaging.util.DateUtil;
import packaging.payrolldomain.Paycheck;
import packaging.payrolldomain.PaymentClassification;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HourlyClassification implements PaymentClassification {
    private double hourlyRate;
    private Map<Date, TimeCard> timeCardList = new HashMap<>();

    public HourlyClassification(double hourlyRate) {
        super();
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public TimeCard getTimeCard(Date from) {
        return timeCardList.get(from);
    }

    public void addTimeCard(TimeCard timeCard) {
        this.timeCardList.put(timeCard.getDate(), timeCard);
    }

    @Override
    public double calculatePay(Paycheck paycheck) {
        double totalPay = 0.00;
        for (TimeCard timeCard : timeCardList.values()) {
            if (DateUtil.isInPayPeriod(timeCard.getDate(), paycheck.getPayPeriodStartDate(), paycheck.getPayPeriodEndDate())) {
                totalPay += calculatePayForTimeCard(timeCard);
            }
        }
        return totalPay;
    }

    private double calculatePayForTimeCard(TimeCard timeCard) {
        double overtimeHours = Math.max(0.0, timeCard.getHours() - 8);
        double normalHours = timeCard.getHours() - overtimeHours;
        return hourlyRate * (normalHours + 1.5 * overtimeHours);
    }

//    private boolean isInPayPeriod(nopackaging.TimeCard card, Date payPeriod) {
//        Date payPeriodEndDate = payPeriod;
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(payPeriod);
//        cal.add(Calendar.DAY_OF_MONTH, -5);
//        Date payPeriodStartDate = Date.from(cal.toInstant());
//        return card.getDate().getTime() <= payPeriodEndDate.getTime()  &&
//                card.getDate().getTime() >= payPeriodStartDate.getTime();
//    }
}
