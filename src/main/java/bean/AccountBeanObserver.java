package bean;

import model.Account;
import observer.Observer;

public class AccountBeanObserver implements Observer {

    private String username;
    private String actualState;
    private Account subject;
    private static AccountBeanObserver beanObserver;

    public static AccountBeanObserver getObserver(){
        if(beanObserver==null){
            beanObserver=new AccountBeanObserver(Account.getInstance());
        }
        return beanObserver;
    }

    private AccountBeanObserver(Account subject){
        this.subject=subject;
        this.actualState = "OFFLINE";
        this.subject.attach(this);
    }
    @Override
    public void update() {
        this.actualState = subject.getActualState();
        this.username =subject.getUsername();
    }
    public String getActualState(){
        return this.actualState;
    }

    public String getUsername() {
        return username;
    }
}