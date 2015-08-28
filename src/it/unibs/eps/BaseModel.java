package it.unibs.eps;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class BaseModel {

protected EventListenerList listenerList = new EventListenerList();
	
	public void addChangeListener(ChangeListener l) { //Riceve l'oggetto che si vuole registrare ad un evento
	    listenerList.add(ChangeListener.class, l); //Aggiunge alla lista di eventi la reference dell'oggetto che si vuole registrare
	    /** LISTA DI OGGETTI CHE SONO IN GRADO DI RICEVERE DETERMINATI EVENTI
	     * add
	     * public <T extends EventListener> void add(Class<T> t, T l)
	     * Adds the listener as a listener of the specified type.
	     * Parameters:
	     * t - the type of the listener to be added
	     * l - the listener to be added
	     */
	}

	public void removeChangeListener(ChangeListener l) {
	    listenerList.remove(ChangeListener.class, l);
	}
	
	protected void fireValuesChange() {
		fireValuesChange(new ChangeEvent(this));
	}
	
	protected void fireValuesChange(ChangeEvent changeEvent) {
		/**
		 * EventListener:
		 * posizioni PARI    -> Classe oggetto
		 * posizioni DISPARI -> Oggetto stesso
		 */
	    Object[] listeners = listenerList.getListenerList();
	    for (int i = listeners.length - 2; i >= 0; i -=2 ) {
	        if (listeners[i] == ChangeListener.class) { //Se l'oggetto si è registrato per quel tipo di evento, faccio call successiva
	        	//Controlla che la classe del Listener che implementano sia quella che stiamo elaborando
	            ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
	            //Change Event contiene le info specifiche di quell'evento:
	            //se ho voli, conterrà la lista dei voli se no qualcos'altro
	            //A seconda dell'evento che il Model deve inviare, seleziona gli oggetti che sono in ascolto per quell'evento
	        }
	    }
	}
}