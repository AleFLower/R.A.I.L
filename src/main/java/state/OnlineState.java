package state;

public class OnlineState extends AbstractState{

    @Override
    public void login(StateMachineImpl s) {
        // This method does nothing, only required by the parent contract.
        // It ignores the state change: e.g., on a login event, the state machine stays in the same state.

    }
    @Override
    public void logout(StateMachineImpl s) {
        AbstractState newState= new OfflineState();
        s.changeToState(newState);

    }

    @Override
    public String toString(){
        return "ONLINE";
    }
}
