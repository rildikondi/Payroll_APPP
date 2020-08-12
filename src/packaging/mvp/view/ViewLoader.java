package packaging.mvp.view;

import packaging.mvp.presenter.TransactionContainer;

public interface ViewLoader {
    void loadPayrollView();

    void loadAddEmployeeView(TransactionContainer transactionContainer);
}
