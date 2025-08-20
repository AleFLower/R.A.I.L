package applicationcontroller;

import model.Account;

public class AccountController {

    //classe che gestisce le interazioni con l account, visto che metterla nell utility accesso tale responsabilità
    //non era tanto corretto
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
