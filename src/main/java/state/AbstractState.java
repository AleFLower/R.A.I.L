package state;

public abstract class AbstractState {

    public static AbstractState getInitialState(){
        return new OfflineState();
    } //di base è offline
    public abstract void login(StateMachineImpl s);
    public abstract void logout(StateMachineImpl s);

    protected void entry(StateMachineImpl contextSM){}
    protected void exit(StateMachineImpl contextSM){}
}
