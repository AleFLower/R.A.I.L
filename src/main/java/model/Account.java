package model;
import observer.Observer;
import observer.Subject;
import state.Events;
import state.StateMachine;
import state.StateMachineImpl;

import java.util.ArrayList;

public class Account extends Subject {
    //variabile stateMachine, rappresenta la composizione che il mio client ha con l'interfaccia
    private String username;
    private String userCode;
    private static Account account;
    private Role role;  //di default è admin
    private StateMachine stateMachine;
    //nel momento della creazione di un account il suo stato corrente viene messo offline, quindi la variabile
    //current state associata a stateMachineImpl sarà offline State kind of AbstractState

    public static Account getInitialAccount(){   //forse meglio renderla synchronized?
        if(account==null){
           account=new Account();
        }
        return account;
    }
    protected Account (){   //sonar required to have protected and no private as it gaves smell, but it has to be private for singleton
        //inoltre questo account ha un riferimento a state machine
        //nel nostro diagramma uml possiamo vederlo come colui che possiede la composizione verso l'intrfaccia state
        //machine e conosce solo i metodi di quell'intrfaccia ( nell'esempio in classe era colui che conosceva il metodo
        //goNext
        stateMachine=new StateMachineImpl();
        //inizializzo l'observer list di Subject
        this.observerList=new ArrayList<>();
    }
    //quando viene fatto l'accesso passo online, passo come evento il login_event che indica proprio che
    //ho svolto il login, questo metodo lo riceverà stateMachineImpl che e' un kind of StateMachine
    public void goOnline(){
        this.stateMachine.goNext(Events.LOGIN);
        //nel momento in cui passa online notifica i suoi observer
        notifyObservers();
    }
    //quando viene fatto il logout passo offline
    public void goOffline(){

        this.stateMachine.goNext(Events.LOGOUT);
        this.userCode =null;
        this.username =null;
        notifyObservers();
    }
    //metodo che torna lo stato attuale dell'account
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
        //questo metodo viene chiamato da login e logout i quali successivamente chiamano passa online e passa offline,
        //saranno subito questi altri 2 a notificare il tutto
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
