package bean;

import model.Account;
import observer.Observer;


public class AccountBeanObserver implements Observer {

    private String username;
    private String actualState;
    private Account subject;  //riferimento al concreteSubject

    public AccountBeanObserver(){

        this.subject=Account.getInstance();
        this.actualState = "OFFLINE";  //DI DEFAULT

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
