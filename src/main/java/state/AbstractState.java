package state;

public abstract class AbstractState {
    //classe astratta, trova i suoi figli in OnlineState e offlineState, La classe state machine imp
    //è collegata da una composizione con questa classe e tramite questo collegamento( che e' segnato da un attributo)
    //la classeStateMachineImpl riesce a mantenere in quella variabile lo stato corrente in cui l'utente si trova
    public static AbstractState getInitialState(){
        return new OfflineState();
    } //di base è offline
    public abstract void login(StateMachineImpl s);
    public abstract void logout(StateMachineImpl s);

    //preparazione per estensioni future del pattern, per ora non prevedo una azione da effettuare nella entry
    //state o nella exit state, le metto qui per un futuro uso
    protected void entry(StateMachineImpl contextSM){}
    protected void exit(StateMachineImpl contextSM){}
}
