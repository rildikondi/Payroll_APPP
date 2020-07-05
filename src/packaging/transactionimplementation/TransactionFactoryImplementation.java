package packaging.transactionimplementation;

import packaging.transactionapplication.Transaction;
import packaging.transactionfactory.TransactionFactory;

import java.util.Date;

public class TransactionFactoryImplementation extends TransactionFactory {
    @Override
    public Transaction makeAddSalariedEmployee(int id, String name, String address, double salary) {
        return new AddSalariedEmployee(id, name, address, salary);
    }

    @Override
    public Transaction makeAddHourlyEmployee(int id, String name, String address, double hourlyRate) {
        return new AddHourlyEmployee(id, name, address, hourlyRate);
    }

    @Override
    public Transaction makeAddCommissionedEmployee(int empId, String name, String address, double salary, double commissionRate) {
        return new AddCommissionedEmployee(empId, name, address, salary, commissionRate);
    }

    @Override
    public Transaction makeDeleteEmployeeTransaction(int empId) {
        return new DeleteEmployeeTransaction(empId);
    }

    @Override
    public Transaction makePaydayTransaction(Date payDate) {
        return new PaydayTransaction(payDate);
    }

    @Override
    public Transaction makeTimeCardTransaction(Date date, double hours, int empId) {
        return new TimeCardTransaction(date, hours, empId);
    }

    @Override
    public Transaction makeSalesReceiptTransaction(Date date, double amount, int empId) {
        return new SalesReceiptTransaction(date, amount, empId);
    }

    @Override
    public Transaction makeChangeNameTransaction(int empId, String newName) {
        return new ChangeNameTransaction(empId, newName);
    }

    @Override
    public Transaction makeChangeHourlyTransaction(int id, double hourlyRate) {
        return new ChangeHourlyTransaction(id, hourlyRate);
    }

    @Override
    public Transaction makeChangeCommissionedTransaction(int empId, double salary, double commissionRate) {
        return new ChangeCommissionedTransaction(empId, salary, commissionRate);
    }

    @Override
    public Transaction makeChangeSalariedTransaction(int empId, double salary) {
        return new ChangeSalariedTransaction( empId,  salary);
    }

    @Override
    public Transaction makeChangeMailTransaction(int empId, String address) {
        return new ChangeMailTransaction( empId,  address);
    }

    @Override
    public Transaction makeChangeDirectTransaction(int empId, String bank , String account) {
        return new ChangeDirectTransaction(empId, bank , account);
    }

    @Override
    public Transaction makeChangeHoldTransaction(int empId) {
        return new ChangeHoldTransaction(empId);
    }

    @Override
    public Transaction makeChangeMemberTransaction(int empId, int memberId, double dues) {
        return new ChangeMemberTransaction( empId,  memberId,  dues);
    }

    @Override
    public Transaction makeChangeUnaffiliatedTransaction(int empId) {
        return new ChangeUnaffiliatedTransaction(empId);
    }

    @Override
    public Transaction makeServiceChargeTransaction(int memberId, Date date, double charge) {
        return new ServiceChargeTransaction(memberId, date, charge);
    }
}
