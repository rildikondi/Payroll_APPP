public class DirectMethod implements PaymentMethod {

    private final String bank;
    private final String account;

    public DirectMethod(String bank, String account) {
        this.bank = bank;
        this.account = account;
    }

    public String getBank() {
        return bank;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public void pay(Paycheck paycheck) {

    }
}
