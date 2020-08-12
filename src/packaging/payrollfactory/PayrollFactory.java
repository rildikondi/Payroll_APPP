package packaging.payrollfactory;


import packaging.payrollimplementation.*;

import java.util.Date;

public abstract class PayrollFactory {
    public static PayrollFactory payrollFactory;

    public abstract BiweeklySchedule makeBiweeklySchedule();

    public abstract CommissionedClassification makeCommissionedClassification(double salary, double commissionRate);

    public abstract DirectDepositMethod makeDirectMethod(String bank, String account);

    public abstract HoldMethod makeHoldMethod();

    public abstract HourlyClassification makeHourlyClassification(double hourlyRate);

    public abstract MailMethod makeMailMethod(String address);

    public abstract MonthlySchedule makeMonthlySchedule();

    public abstract SalariedClassification makeSalariedClassification(double salary);

    public abstract SalesReceipt makeSalesReceipt(Date date, double amount);

    public abstract ServiceCharge makeServiceCharge(Date date, double charge);

    public abstract TimeCard makeTimeCard(Date date, double hours);

    public abstract UnionAffiliation makeUnionAffiliation();

    public abstract UnionAffiliation makeUnionAffiliation(int memberId, double dues);

    public abstract WeeklySchedule makeWeeklySchedule();
}
