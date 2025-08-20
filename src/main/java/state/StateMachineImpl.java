package state;

public class StateMachineImpl implements StateMachine{

    private AbstractState currentState;
    public StateMachineImpl(){

        this.currentState=AbstractState.getInitialState();
    }
    @Override
    public void goNext(Events e) {

        switch (e){
            case LOGIN:
                this.currentState.login(this);
                break;
            case LOGOUT:
                this.currentState.logout(this);
                break;
            default:
                break;
        }
    }
    public void changeToState(AbstractState s){
        this.currentState=s;
    }
    public AbstractState getState(){
        return this.currentState;
    }

    @Override
    public String toString(){
        return String.valueOf(this.currentState);
    }

}
