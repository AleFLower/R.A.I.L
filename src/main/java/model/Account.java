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
    private static Account account;
    private Role role;  //di default è admin
    private StateMachine stateMachine;

    public static Account getInstance(){   //forse meglio renderla synchronized?
        if(account==null){
           account=new Account();
        }
        return account;
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
    public void attach(Observer o) {
        this.observerList.add(o);
    }

    @Override
    public void detach(Observer o) {
        int i=this.observerList.indexOf(o);
        if(i>=0) {
            this.observerList.remove(i);
        }
    }
    @Override
    public void notifyObservers() {
        for(Observer o:this.observerList){
            o.update();
        }
    }
    public void setCredentials(String userName, String userCode, Role role){

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
