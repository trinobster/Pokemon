package it.unibs.eps.pokemon;

import it.unibs.eps.giocatore.Giocatore;

public class Bulbasaur extends Pokemon {
	
	@Override
	public void mossaSpeciale() { //Berserk
		int potenza = super.getAttacco().getPotenzaAttacco() + 30; //Normalmente fa male 100, con questo fa male 120
		super.getAttacco().setPotenzaAttacco(potenza);//TODO sistemare super
	}

	@Override
	public void mossaSpeciale(Pokemon ps, Giocatore player, boolean selvatico) {
		mossaSpeciale();
	}
	
	/**
	 * Nel Model:
	 * se il limite viene superato, mossaspeciale() cambia alcune statistiche.
	 * se il limite non è superato, le statistiche tornano normali!!! //TODO
	 */

}
