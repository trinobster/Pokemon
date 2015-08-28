package it.unibs.eps.pokemon;

import it.unibs.eps.giocatore.Giocatore;
import java.io.Serializable;

public abstract class Pokemon implements Serializable{
	
	protected String nome;
	protected int vita;
	protected String imageName;
	private Attacco attacco;
	protected String descrizione;
	protected int limite;
	public String nomeMossa;
	
	public String getNomeMossa() {
		return nomeMossa;
	}

	public void setNomeMossa(String nomeMossa) {
		this.nomeMossa = nomeMossa;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public abstract void mossaSpeciale();

	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public void setDescrizione(String descrizione){
		this.descrizione = descrizione;
	}
	
	public String getDescrizione(){
		return descrizione;
	}

	public String getNome(){
		return nome;
	}
	
	public int getVita(){
		return vita;
	}
	
	public void setVita(int vita){
		this.vita = vita;
	}
	
	public Attacco getAttacco(){
		return attacco;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public void setAttacco(Attacco attacco) {
		this.attacco = attacco;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
		
	}
	
	public String toString(){
		return String.format("%s", nome);
	}

	public abstract void mossaSpeciale(Pokemon ps, Giocatore player, boolean selvatico);
	
}
