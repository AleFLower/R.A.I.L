package state;

public interface StateMachine {
    void goNext(Events e);
}
