package it.unibs.eps.pokemon;

import it.unibs.eps.giocatore.Giocatore;


public class Snorlax extends Pokemon{
	
	@Override
	public void mossaSpeciale() { //Cura
		int vita = super.getVita() + 30;
		super.setVita(vita);
	}

	@Override
	public void mossaSpeciale(Pokemon ps, Giocatore player, boolean selvatico) {
		mossaSpeciale();
	}
	
}
