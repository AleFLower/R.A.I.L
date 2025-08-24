package state;

public class OfflineState extends AbstractState{

    @Override
    public void login(StateMachineImpl contextSM) {
        AbstractState newState= new OnlineState();
        contextSM.changeToState(newState);

    }
    @Override
    public void logout(StateMachineImpl contextSM){
        // This method does nothing, it's only implemented due to the parent contract.
    }

    @Override
    public String toString(){
        return "OFFLINE";
    }
}
