package state;

public abstract class AbstractState {

    public static AbstractState getInitialState(){
        return new OfflineState();
    } //di base è offline
    public abstract void login(StateMachineImpl contextSM);
    public abstract void logout(StateMachineImpl contextSM);

    public void entry(){}
    public void exit(){}
}
