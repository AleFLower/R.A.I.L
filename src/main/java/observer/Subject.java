package observer;

import java.util.List;

public abstract class Subject {
    public void attach(Observer o){this.observerList.add(o);}
    public void detach(Observer o){
        int i=this.observerList.indexOf(o);
        if(i>=0) {
            this.observerList.remove(i);
        }
    }
    protected abstract void notifyObservers();
    protected List<Observer> observerList;
}
