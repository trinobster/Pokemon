package it.unibs.eps;

import it.unibs.eps.giocatore.Giocatore;
import it.unibs.eps.pokemon.Pokemon;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

public class Pokedex  implements Serializable{
	public Giocatore giocatore;
	public HashMap<Point,Pokemon> hmap; //è serializzabile
	public int punteggio;
	public Vector<Point> puntiBuoni;
	public Vector<Point> puntiNonBuoni;
	
	public Vector<Point> getPuntiNonBuoni() {
		return puntiNonBuoni;
	}

	public void setPuntiNonBuoni(Vector<Point> puntiNonBuoni) {
		this.puntiNonBuoni = puntiNonBuoni;
	}

	public Vector<Point> getPuntiBuoni() {
		return puntiBuoni;
	}

	public void setPuntiBuoni(Vector<Point> puntiBuoni) {
		this.puntiBuoni = puntiBuoni;
	}

	public Giocatore getGiocatore() {
		return giocatore;
	}

	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}
	
	public HashMap<Point,Pokemon> getMap(){
		return hmap;
	}
	
	public void setMap(HashMap<Point,Pokemon> hmap){
		this.hmap = hmap;
	} 
}



