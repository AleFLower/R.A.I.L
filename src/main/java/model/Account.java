package model;
import observer.Observer;
import observer.Subject;
import state.Events;
import state.StateMachine;
import state.StateMachineImpl;

import java.util.ArrayList;


public class Account extends Subject {

    private String username;
    private String userCode;
    private static Account instance;
    private Role role;
    private StateMachine stateMachine;

    public static Account getInstance(){
        if(instance ==null){
           instance =new Account();
        }
        return instance;
    }
    protected Account (){
        stateMachine=new StateMachineImpl();
        this.observerList=new ArrayList<>();
    }

    public void goOnline(){
        this.stateMachine.goNext(Events.LOGIN);
        notifyObservers();
    }

    public void goOffline(){

        this.stateMachine.goNext(Events.LOGOUT);
        this.userCode =null;
        this.username =null;
        notifyObservers();
    }

    public String getActualState(){return this.stateMachine.toString();}

    @Override
    public void notifyObservers() {
        for(Observer o:this.observerList){
            o.update();
        }
    }
    public void registerAccount(String userName, String userCode, Role role){
        this.username =userName;
        this.userCode =userCode;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getUserCode() {
        return userCode;
    }

    public Role getRole(){return role;}

}
