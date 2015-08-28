package it.unibs.eps.pokemon;

import java.io.Serializable;

public class Attacco implements Serializable{
	
	private String nomeAttacco;
	private int potenzaAttacco;
	private String imageAttacco;
	private int speed;
	
	public Attacco(String nomeAttacco, int potenzaAttacco, String imageAttacco, int speed){
		this.nomeAttacco = nomeAttacco;
		this.potenzaAttacco = potenzaAttacco;
		this.imageAttacco = imageAttacco;
		this.setSpeed(speed);
	}
	

	public String getNomeAttacco() {
		return nomeAttacco;
	}
	public void setNomeAttacco(String nomeAttacco) {
		this.nomeAttacco = nomeAttacco;
	}
	public int getPotenzaAttacco() {
		return potenzaAttacco;
	}
	public void setPotenzaAttacco(int potenzaAttacco) {
		this.potenzaAttacco = potenzaAttacco;
	}
	public String getImageAttacco() {
		return imageAttacco;
	}
	public void setImageAttacco(String imageAttacco) {
		this.imageAttacco = imageAttacco;
	}
	
	public String toString(){
		return String.format("%s", nomeAttacco);
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
