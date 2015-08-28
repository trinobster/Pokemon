package it.unibs.eps.pokemon;

import it.unibs.eps.giocatore.Giocatore;

public class Pikachu extends Pokemon{
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public void setVita(int vita){ // usare quando inizializzi vita del pokemon e quando combatti e la vita diminuisce
		this.vita = vita;
	}

	@Override
	public void mossaSpeciale() { //aumento la velocità
		int speed = super.getAttacco().getSpeed() + 3; //Velocità settata al massimo
		super.getAttacco().setSpeed(speed);
	}

	@Override
	public void mossaSpeciale(Pokemon ps, Giocatore player, boolean selvatico) {
		mossaSpeciale();
	}
	
}
