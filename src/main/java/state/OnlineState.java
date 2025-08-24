package state;

public class OnlineState extends AbstractState{

    private long entryTime;
    private long totalOnlineTime = 0;

    @Override
    public void login(StateMachineImpl contextSM) {
        // This method does nothing, only required by the parent contract.
    }
    @Override
    public void logout(StateMachineImpl contextSM) {
        AbstractState newState= new OfflineState();
        contextSM.changeToState(newState);
    }

    @Override
    public void entry() {
        entryTime = System.currentTimeMillis();
    }

    @Override
    public void exit() {
        long durationMs = System.currentTimeMillis() - entryTime;
        long durationSec = durationMs / 1000;
        totalOnlineTime += durationSec;
    }

    @Override
    public String toString(){
        return "ONLINE";
    }


    // Tracks the total time the user spends online, useful for future internal metrics, analytics, or session-related logic
    public long getTotalOnlineTime() {
        return totalOnlineTime;
    }

}
