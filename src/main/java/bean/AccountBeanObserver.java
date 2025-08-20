package bean;

import model.Account;
import observer.Observer;


//l ho fatta singleton per il fatto che i controller grafici se voglio ripassare ogni volta da una schermata all altra,
//vengono ricaricati con new, quindi condividirebbero valori diversi della bean, che registrandosi come nuovi osservatori le bean
// non ricevono l'update precedente e quindi non si aggiornano. Potrei fare che nel subject appena si iscrive un osservatore
//lancio un update per sincronizzarli?

public class AccountBeanObserver implements Observer {
    //ha un riferimento a subject, e' colui che implementa l'observer, nel diagramma uml e' come se fosse il
    //concrete observer che riceve gli update e che ha un riferimento a concrete subject, , inoltre la costruisco come singleton questa classe
    //utilizzerò per implementare observer un riferimento a subject e non ad Account, perché altrimenti violerei il principio delle bean,
    //anche se non violeresti nulla perché non è che esporti all esterno Account, non lo vede proprio l'esterno grazie all incapsulamento e a singleton
    //quindi sostanzialmente rendere beanSegnalaEntity factory di se stessa
    private String username;
    private String actualState;
    private Account subject;  //riferimento al concreteSubject

    public AccountBeanObserver(){
        //assegno alla variabile locale il type of Subject
        this.subject=Account.getInstance();
        this.actualState = "OFFLINE";  //DI DEFAULT
        //registro questo concrete observer alla lista di observer presente in subject e questa lista e' conosciuta
        //anche dal concrete subject il quale la usa per inviare gli update ai concrete observer
        this.subject.attach(this);
    }
    //viene chiamato da una concrete subject verso tutti gli observer che la guardano, viene chiamata quando accade un cambiamento
    //che richiede la notifica ai concrete observer
    @Override
    public void update() {
        //il concrete subject ha cambiato qualcosa e questo cambiamento lo ha norificato a tutti i suoi observer
        //questo observer cambia quindi lo stato delgi attributi che hanno interessato il cambiamento , e questi attributi
        //sono proprio quelli che va a vedere il controller grafico senza accesso, che usa la composizione con il
        //concrete observer (this) per comunicare all'utente in modo grafico i cambiamenti che ci sono stati
        this.actualState = subject.getActualState();
        this.username =subject.getUsername();
    }
    public String getActualState(){
        return this.actualState;
    }

    public String getUsername() {
        return username;
    }
}
