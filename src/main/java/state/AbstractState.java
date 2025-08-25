package state;

public abstract class AbstractState {

    public static AbstractState getInitialState(){
        return new OfflineState();
    } //default = OFFLINE
    public abstract void login(StateMachineImpl contextSM);
    public abstract void logout(StateMachineImpl contextSM);

    protected void entry(){}
    protected void exit(){}
}
