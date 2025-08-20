package applicationcontroller;

import model.Account;

public class AccountController {

    private Account account;

    public AccountController(){
        this.account = Account.getInstance();
    }
    public Account getAccount(){
        return this.account;
    }

    public String getAccountState(){
        return this.account.getActualState();
    }

    public void goOffline(){
        this.account.goOffline();
    }
}
