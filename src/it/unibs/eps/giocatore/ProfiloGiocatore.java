package it.unibs.eps.giocatore;

import java.io.Serializable;


public class ProfiloGiocatore implements Serializable{
	
	protected String nome;
	protected String descrizione;
	protected String imageName;
	protected int id;
	
	public String getName(){
		return nome;
	}
	
	public void setName(String nome){
		this.nome = nome;
	}
	
	public String getImageName(){
		return imageName;
	}
	
	public void setImageName(String name){
		imageName = name;
	}
	
	public String toString(){
		return String.format("%s", nome);
	}
	
	public String getDescrizione(){
		return descrizione;
	}

}
