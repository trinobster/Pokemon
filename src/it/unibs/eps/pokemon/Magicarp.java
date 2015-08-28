package it.unibs.eps.pokemon;

import it.unibs.eps.giocatore.Giocatore;

public class Magicarp extends Pokemon {
	@Override
	public void mossaSpeciale(Pokemon ps, Giocatore giocatore, boolean selvatico) { //Diminuisce la vita/potenza degli altri pokemon del 50% (compresa la sua)
		for(Pokemon p: giocatore.squadra){
			int vita = p.getVita() - p.getVita()/2;
			p.setVita(vita);
			//Le statistiche rimangono così anche a fine battaglia! TODO
		}
	}
	
	public void aggiunto(Giocatore giocatore, Magicarp carp){
		giocatore.squadra.addElement(carp); // ??? TODO SOLO PER MODALITA' DIFFICILE
	}

	@Override
	public void mossaSpeciale() {
	}

}
