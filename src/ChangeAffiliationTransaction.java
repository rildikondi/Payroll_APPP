public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction{
    
    public ChangeAffiliationTransaction(int empId) {
        super(empId);
    }

    @Override
    protected void change(Employee e) {
        recordMembership(e);
        e.setAffiliation(getAffiliation());
    }

    protected abstract void recordMembership(Employee e);

    protected abstract Affiliation getAffiliation();
}
