package it.unibs.eps.giocatore;

import it.unibs.eps.pokemon.Pokemon;

import java.io.Serializable;
import java.util.Vector;

public class Giocatore implements Serializable{
	public String nome;
	public ProfiloGiocatore profilo;
	public Vector<Pokemon> squadra = new Vector<Pokemon>();
	public String difficolta;
	public int punteggio;
	public Vector<Pokemon> sconfitti = new Vector<Pokemon>();
	public String distrutti = " ";
	
	public Giocatore(){
		punteggio = 0;
	}

	public int getPunteggio() {
		return punteggio;
	}
	
	public void setPunteggio(int punteggio){
		this.punteggio = punteggio;
	}
}
