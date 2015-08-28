package it.unibs.eps;

import it.unibs.eps.dialog.Combattimento;
import it.unibs.eps.giocatore.Giocatore;
import it.unibs.eps.pokemon.Pokemon;

public class Controller {
// APPENA INIZIA LO SCONTRO SERVE CHIAMARE Model.inizia();
	
	public Model model;
	public Giocatore player;
	public Pokemon ps;
	public Combattimento combattimento;
	
	public Controller(Model model){
		this.model = model;
	}
	
	public void disableSpeciale(){
		model.enableMossa = false;
	}
	
	public void disableAttacco(){
		model.enableAttacco = false;
	}
	
	public void getParametri(Giocatore player, Pokemon ps, Combattimento combattimento){
		this.player = player;
		this.ps = ps;
		this.combattimento = combattimento;
	}
	
	public void inizia(){
			model.inizio(player, ps, combattimento);
	}
	
	public void attacca(){
		model.click = true;
		model.attacco();
	}
	
	public void mossaSpeciale(){
		model.mossa();
	}
	
	public Pokemon getPp(){
		return model.getPp();
	}
	
	public String getStato(){
		return model.stato;
	}
	
	public Pokemon getPs(){
		return model.getPs();
	}
}
