package observer;

import java.util.List;

public abstract class Subject {
    public abstract void attach(Observer o);
    public abstract void detach(Observer o);
    protected abstract void notifyObservers();
    protected List<Observer> observerList;
}
