public class ChangeDirectTransaction extends ChangeMethodTransaction {
    private final String bank;
    private final String account;

    public ChangeDirectTransaction(int empId, String bank , String account) {
        super(empId);
        this.bank = bank;
        this.account = account;
    }

    @Override
    protected PaymentMethod getMethod() {
        return new DirectMethod(bank, account);
    }
}
