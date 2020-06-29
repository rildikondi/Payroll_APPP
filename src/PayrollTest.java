import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PayrollTest {

    @Test
    public void testAddSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertEquals("Bob", e.getName());
        PaymentClassification paymentClassification = e.getPaymentClassification();
        Assert.assertTrue(paymentClassification instanceof SalariedClassification);
        SalariedClassification salariedClassification = (SalariedClassification) paymentClassification;
        Assert.assertEquals(1000.00, salariedClassification.getSalary(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof MonthlySchedule);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void testAddHourlyEmployee() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Sam", "Home", 20.00);
        t.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertEquals("Sam", e.getName());
        PaymentClassification paymentClassification = e.getPaymentClassification();
        Assert.assertTrue(paymentClassification instanceof HourlyClassification);
        HourlyClassification hourlyClassification = (HourlyClassification) paymentClassification;
        Assert.assertEquals(20.00, hourlyClassification.getHourlyRate(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof WeeklySchedule);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void testAddCommissionedEmployee() {
        int empId = 3;
        AddCommissionedEmployee transaction = new AddCommissionedEmployee(
                empId, "John", "Home", 1500.00, 15.00);
        transaction.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertEquals("John", e.getName());
        PaymentClassification paymentClassification = e.getPaymentClassification();
        Assert.assertTrue(paymentClassification instanceof CommissionedClassification);
        CommissionedClassification commissionedClassification = (CommissionedClassification) paymentClassification;
        Assert.assertEquals(1500.00, commissionedClassification.getSalary(), .001);
        Assert.assertEquals(15.00, commissionedClassification.getCommissionRate(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof BiweeklySchedule);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void deleteEmployee() {
        int empId = 4;
        AddCommissionedEmployee t = new AddCommissionedEmployee(
                empId, "Bill", "Home", 2500, 3.2);
        t.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        DeleteEmployeeTransaction dt = new DeleteEmployeeTransaction(empId);
        dt.execute();
        e = PayrollDatabase.getEmployee(empId);
        Assert.assertNull(e);
    }

    @Test
    public void testTimeCardTransaction() {
        int empId = 5;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Date date = getDate("2005-7-31");
        TimeCardTransaction tct = new TimeCardTransaction(date, 8.0, empId);
        tct.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        TimeCard tc = hc.getTimeCard(date);
        Assert.assertNotNull(tc);
        Assert.assertEquals(8.0, tc.getHours(), .001);
    }

    @Test
    public void testSalesReceiptTransaction() {
        int empId = 6;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill", "Home", 1500.00, 15.00);
        t.execute();
        Date date = getDate("2005-7-31");
        SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(date, 20.00, empId);
        salesReceiptTransaction.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification commissionedClassification = (CommissionedClassification) pc;
        Assert.assertNotNull(commissionedClassification);
        SalesReceipt salesReceipt = commissionedClassification.getSalesReceipt(date);
        Assert.assertEquals(20.00, salesReceipt.getAmount(), 0.001);
        Assert.assertEquals(date, salesReceipt.getDate());
    }

    @Test
    public void testAddServiceCharge() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(
                empId, "Bill", "Home", 15.25);
        t.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        UnionAffiliation af = new UnionAffiliation();
        e.setAffiliation(af);
        int memberId = 86; // Maxwell Smart
        PayrollDatabase.addUnionMember(memberId, e);
        Date date = getDate("2005-8-8");
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, date, 12.95);
        sct.execute();
        ServiceCharge sc = af.getServiceCharge(date);
        Assert.assertNotNull(sc);
        Assert.assertEquals(12.95, sc.getAmount(), .001);
    }

    @Test
    public void testChangeNameTransaction() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        ChangeNameTransaction cnt = new ChangeNameTransaction(empId, "Bob");
        cnt.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        Assert.assertEquals("Bob", e.getName());
    }

    @Test
    public void testChangeHourlyTransaction() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(
                empId, "Lance", "Home", 2500, 3.2);
        t.execute();
        ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empId, 27.52);
        cht.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        Assert.assertEquals(27.52, hc.getHourlyRate(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof WeeklySchedule);
    }

    @Test
    public void testChangeSalariedTransaction() {
        int empId = 7;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Sam", "Home", 1200.00);
        t.execute();
        ChangeSalariedTransaction changeSalariedTransaction = new ChangeSalariedTransaction(empId, 1700.00);
        changeSalariedTransaction.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc instanceof SalariedClassification);
        SalariedClassification sc = (SalariedClassification) pc;
        Assert.assertEquals(1700.00, sc.getSalary(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof MonthlySchedule);
    }

    @Test
    public void testChangeCommissionedTransaction() {
        int empId = 9;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "John", "Home", 1350.00, 22.50);
        t.execute();
        ChangeCommissionedTransaction changeCommissionedTransaction = new ChangeCommissionedTransaction(empId, 1400.00, 25.00);
        changeCommissionedTransaction.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;
        Assert.assertEquals(1400.00, cc.getSalary(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof BiweeklySchedule);
    }

    @Test
    public void testChangeDirectMethodTransaction() {
        int empId = 10;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        ChangeDirectTransaction changeMethodTransaction = new ChangeDirectTransaction(empId, "Raiffeisen", "RZOOATL...");
        changeMethodTransaction.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertNotNull(pm);
        Assert.assertTrue(pm instanceof DirectMethod);
        DirectMethod dm = (DirectMethod) pm;
        Assert.assertEquals("Raiffeisen", dm.getBank());
        Assert.assertEquals("RZOOATL...", dm.getAccount());
    }

    @Test
    public void testChangeMailMethodTransaction() {
        int empId = 10;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        ChangeMailTransaction changeMailTransaction = new ChangeMailTransaction(empId, "Home");
        changeMailTransaction.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertNotNull(pm);
        Assert.assertTrue(pm instanceof MailMethod);
        MailMethod mm = (MailMethod) pm;
        Assert.assertEquals("Home", mm.getAddress());

    }

    @Test
    public void testChangeHoldMethodTransaction() {
        int empId = 10;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        ChangeHoldTransaction changeHoldTransaction = new ChangeHoldTransaction(empId);
        changeHoldTransaction.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertNotNull(pm);
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void testChangeAffiliationTransaction() {
        int empId = 8;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        int memberId = 7743;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 99.42);
        cmt.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        Affiliation affiliation = e.getAffiliation();
        Assert.assertNotNull(affiliation);
        Assert.assertTrue(affiliation instanceof UnionAffiliation);
        UnionAffiliation uf = (UnionAffiliation) affiliation;
        Assert.assertEquals(99.42, uf.getDues(), .001);
        Employee member = PayrollDatabase.getUnionMember(memberId);
        Assert.assertNotNull(member);
        Assert.assertEquals(e, member);
    }

    @Test
    public void testChangeUnaffiliatedTransaction() {
        int empId = 8;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        ChangeUnaffiliatedTransaction changeUnaffiliatedTransaction = new ChangeUnaffiliatedTransaction(empId);
        changeUnaffiliatedTransaction.execute();
        Employee e = PayrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        Affiliation affiliation = e.getAffiliation();
        Assert.assertNotNull(affiliation);
        Assert.assertSame(affiliation, Affiliation.NULL);
    }

    @Test
    public void testPaySingleSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        Date payDate = getDate("2001-11-30");
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(1000.00, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(0.0, pc.getDeductions(), .001);
        Assert.assertEquals(1000.00, pc.getNetPay(), .001);
    }

    @Test
    public void testPaySingleSalariedEmployeeOnWrongDate() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(
                empId, "Bob", "Home", 1000.00);
        t.execute();
        Date payDate = getDate("2001-11-29");
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPayingSingleHourlyEmployeeNoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(
                empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9");
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 0.0);
    }

    private Date getDate(String date) {
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            instance.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Date.from(instance.toInstant());
    }

    private void validatePaycheck(PaydayTransaction pt, int empid, Date payDate, double pay) {
        Paycheck pc = pt.getPaycheck(empid);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(pay, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(0.0, pc.getDeductions(), .001);
        Assert.assertEquals(pay, pc.getNetPay(), .001);
    }

    @Test
    public void testPaySingleHourlyEmployeeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(
                empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9"); // Friday
        TimeCardTransaction tc = new TimeCardTransaction(payDate, 2.0, empId);
        tc.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 30.5);
    }

    @Test
    public void testPaySingleHourlyEmployeeOvertimeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(
                empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9"); // Friday
        TimeCardTransaction tc = new TimeCardTransaction(payDate, 9.0, empId);
        tc.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate,
                (8 + 1.5) * 15.25);
    }

    @Test
    public void testPaySingleHourlyEmployeeOnWrongDate() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-8"); // Thursday
        TimeCardTransaction tc = new TimeCardTransaction(payDate, 9.0, empId);
        tc.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeTwoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9"); // Friday
        TimeCardTransaction tc = new TimeCardTransaction(payDate, 2.0, empId);
        tc.execute();
        Calendar cal = Calendar.getInstance();
        cal.setTime(payDate);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        TimeCardTransaction tc2 = new TimeCardTransaction(Date.from(cal.toInstant()), 5.0, empId);
        tc2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 7 * 15.25);
    }

    @Test
    public void testPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9"); // Friday
        Date dateInPreviousPayPeriod = getDate("2001-11-2");
        TimeCardTransaction tc = new TimeCardTransaction(payDate, 2.0, empId);
        tc.execute();
        TimeCardTransaction tc2 = new TimeCardTransaction(dateInPreviousPayPeriod, 5.0, empId);
        tc2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 2 * 15.25);
    }

    @Test
    public void testCommissionedEmployeeNoSales() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07);
        t.execute();
        Date payDate = getDate("2020-06-26");
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(700.00, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(0.0, pc.getDeductions(), .001);
        Assert.assertEquals(700.00, pc.getNetPay(), .001);
    }

    @Test
    public void testPayCommissionedEmployeeWrongDate() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07);
        t.execute();
        Date payDate = getDate("2020-06-25");
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPayCommissionedEmployeeOneSale() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07);
        t.execute();
        Date payDate = getDate("2020-06-12"); // second Friday
        SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(payDate, 100.00, empId);
        salesReceiptTransaction.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 707.00);
    }


    @Test
    public void testPayCommissionedEmployeeTwoSales() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07);
        t.execute();
        Date payDate = getDate("2020-06-12"); // second Friday
        SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(payDate, 100.00, empId);
        salesReceiptTransaction.execute();
        Date saleDate = getDate("2020-06-11");
        SalesReceiptTransaction salesReceiptTransaction2 = new SalesReceiptTransaction(saleDate, 200.00, empId);
        salesReceiptTransaction2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 721.00);
    }

    @Test
    public void testPayCommissionedEmployeeWithSalesSpanningTwoPayPeriods() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07);
        t.execute();
        Date payDate = getDate("2020-06-12"); // second Friday
        SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(payDate, 100.00, empId);
        salesReceiptTransaction.execute();
        Date saleDate = getDate("2020-06-13");// payed in fourth friday
        SalesReceiptTransaction salesReceiptTransaction2 = new SalesReceiptTransaction(saleDate, 200.00, empId);
        salesReceiptTransaction2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 707.00);
    }

    @Test
    public void testSalariedUnionMemberDues() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();
        Date payDate = getDate("2001-11-30");
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(1000.0, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(47.1, pc.getDeductions(), .001);
        Assert.assertEquals(1000.0 - 47.1, pc.getNetPay(), .001);
    }

    @Test
    public void testHourlyUnionMemberServiceCharge() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();
        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();
        Date payDate = getDate("2001-11-9");
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();
        TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId);
        tct.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(8 * 15.24, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(9.42 + 19.42, pc.getDeductions(), .001);
        Assert.assertEquals((8 * 15.24) - (9.42 + 19.42), pc.getNetPay(), .001);
    }

    @Test
    public void testServiceChargesSpanningMultiplePayPeriods() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();
        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();
        Date payDate = getDate("2001-11-9");
        Date earlyDate = getDate("2001-11-2"); // previous Friday
        Date lateDate = getDate("2001-11-16"); // next Friday
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();
        ServiceChargeTransaction sctEarly = new ServiceChargeTransaction(memberId, earlyDate, 100.00);
        sctEarly.execute();
        ServiceChargeTransaction sctLate = new ServiceChargeTransaction(memberId, lateDate, 200.00);
        sctLate.execute();
        TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId);
        tct.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(8 * 15.24, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(9.42 + 19.42, pc.getDeductions(), .001);
        Assert.assertEquals((8 * 15.24) - (9.42 + 19.42), pc.getNetPay(), .001);
    }
}
