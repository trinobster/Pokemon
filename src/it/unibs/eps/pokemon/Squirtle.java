package it.unibs.eps.pokemon;

import it.unibs.eps.giocatore.Giocatore;

public class Squirtle extends Pokemon {
	//limite = quantità di vita rimasta
	String nomeMossa = "Scudo";
	Pokemon avversario;
	
	@Override
	public void mossaSpeciale(Pokemon ps, Giocatore player, boolean selvatico) {//confusione
		if(selvatico)
			avversario = player.squadra.elementAt(0);
		else
			avversario = ps;
		mossaSpeciale();
	}
	@Override
	public void mossaSpeciale() {//resistenza aumentata se in pericolo di vita
		int potenza = avversario.getAttacco().getPotenzaAttacco() - avversario.getAttacco().getPotenzaAttacco()/2; //l'attacco nemico è diminuito di 1/4
		avversario.getAttacco().setPotenzaAttacco(potenza);
	}

}
