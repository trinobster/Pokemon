package it.unibs.eps.pokemon;

import it.unibs.eps.giocatore.Giocatore;

public class Charmander extends Pokemon {

	@Override
	public void mossaSpeciale(Pokemon ps, Giocatore player, boolean selvatico) {
		if(selvatico)
			super.getAttacco().setPotenzaAttacco(super.getAttacco().getPotenzaAttacco() + 20);
		else{
			for(Pokemon p: player.squadra){
				p.getAttacco().setPotenzaAttacco(p.getAttacco().getPotenzaAttacco() + 20);
			}
		}
	}

	@Override
	public void mossaSpeciale() {
	}

}
