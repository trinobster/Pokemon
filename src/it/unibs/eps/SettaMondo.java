package it.unibs.eps;

import it.unibs.eps.giocatore.Giocatore;
import it.unibs.eps.pokemon.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class SettaMondo {
	private Giocatore player;
	private Vector<Pokemon> arrayPokemon;
	private Snorlax snorlax = new Snorlax();
	private Pikachu pikachu = new Pikachu();
	private Bulbasaur bulbasaur = new Bulbasaur();
	private Charmander charmander = new Charmander();
	private Squirtle squirtle = new Squirtle();
	FileUtils fu = new FileUtils();
	String fname = "./text/ParamAttacchi.txt";
	String fpok = "./text/ParamPokemon.txt";
	int numAvversari = 1;								//Cambia in base alla difficoltà
	int numAttacchi = 4;
	
	/**
	 * Se voglio aggiungere nuovi pokemon devo crearne gli oggetti vuoti, aggiungerli in arrayPokemon
	 * e modificare il file di testo fpok ParamPokemon inserendovi i parametri come: nome, gif, velocità.
	 * La vita viene selezionata in questa classe in base alla difficoltà scelta in modo random,
	 * L'attacco del pokemon selvatico e la sua potenza vengono scelti in modo random in base alla difficoltà, mentre
	 * i parametri quali nome e immagineAttacco vengono rispettivamente lette da file.
	 */
	
	public SettaMondo(Giocatore player, Vector<Pokemon> arrayPokemon) throws IOException{
		this.player = player;
		this.arrayPokemon = arrayPokemon;
		
		settaDifficolta();
	}
	
	public void settaDifficolta() throws IOException{
		
		// IN BASE ALLA DIFFICOLTA' VIENE ASSOCIATO UN NUMERO DA 0 A 2
		
		System.out.printf("\nGiocatore settaMondo: \n%s\n%s\n%s",player.nome,player.profilo,player.difficolta);
		
		int d = 2;
		if(player.difficolta.equals("Facile"))
			d = 0;
		else if (player.difficolta.equals("Medio")) 
			d = 1;
		settaArray(d);
	}
	
	public void settaArray(int d) throws IOException {
		
		// INIZIALIZZAZIONE DI arrayPokemon CON N° OGGETTI PARI ALLA DIFFICOLTA'
		
		switch (d) {
		case 0:
			numAvversari = 1;
			arrayPokemon.add(snorlax);
			break;
		case 1:
			numAvversari = 3;
			arrayPokemon.add(snorlax);
			arrayPokemon.add(pikachu);
			arrayPokemon.add(bulbasaur);
			break;
		case 2:
			this.arrayPokemon.add(snorlax);
			this.arrayPokemon.add(pikachu);
			this.arrayPokemon.add(bulbasaur);
			this.arrayPokemon.add(charmander);
			this.arrayPokemon.add(squirtle);
			numAvversari = arrayPokemon.size(); //Tutti i pokemon possibili!
			break;

		default:
			break;
		}
		settaParametri(d);
	}
	
	public void settaParametri(int d) throws IOException{
		
		for(int i = 0; i < numAvversari; i++){
			
			Random r = new Random();
			int attacco = r.nextInt(numAttacchi); //Estremo superiore escluso
			int rangeAttacco = 0;
			int rangeVita = 0;
			Pokemon p = arrayPokemon.elementAt(i);
			
			//SETTAGGIO NOME, IMMAGINE E VELOCITA' POKEMON SELVATICI da FILE:
			
			ArrayList<String> listaPok = fu.readResource(fpok, i+1);
			p.setNome(listaPok.get(0));
			p.setImageName(listaPok.get(1));
			p.setLimite(Integer.parseInt(listaPok.get(4)));
			p.setNomeMossa(listaPok.get(5));
			
			// SETTAGGIO RANDOMICO VITA
			
			switch (d){
			case 0:
				rangeVita = r.nextInt(50) + 10;
				rangeAttacco = r.nextInt(10) + 10;
				break;
			case 1:
				rangeVita = r.nextInt(100) + 10;
				rangeAttacco = r.nextInt(15) + 10;
				break;
			case 2:
				rangeVita = r.nextInt(150) + 30;
				rangeAttacco = r.nextInt(20) + 10;
				break;

			default:
				break;
			}
			
			// SETTAGGIO RANDOMICO DELL'ATTACCO (NOME e IMMAGINE) da FILE:
			
			ArrayList<String> lista = fu.readResource(fname, attacco+1);		//Legge l'attacco random(nome e immagine) in base alla riga(attacco)
			Attacco attack = new Attacco(lista.get(0),rangeAttacco,lista.get(2),Integer.parseInt(lista.get(3)));
			
			p.setAttacco(attack);
			p.setVita(rangeVita);			
		}
	}
}
