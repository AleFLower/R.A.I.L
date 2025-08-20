package state;

public class OfflineState extends AbstractState{

    @Override
    public void login(StateMachineImpl s) {
        AbstractState newState= new OnlineState();
        s.changeToState(newState);

    }
    @Override
    public void logout(StateMachineImpl s){
        // This method does nothing, it's only implemented due to the parent contract.
        // It simply ignores the state change (e.g., on login event, the state machine remains unchanged).
    }

    @Override
    public String toString(){
        return "OFFLINE";
    }
}
