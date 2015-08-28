package it.unibs.eps.giocatore;

import java.io.IOException;

public class Profilo2 extends ProfiloGiocatore {
	
	public Profilo2() throws IOException{
		super.nome = "Avventuriero";
		super.descrizione = "<html>Se il tuo sogno è quello di acchiappare<html><html>"
				+ " tutti i pokémon ed accrescere la tua squadra<html> durante una delle tue avventure<html> "
				+ " <html>questo è il profilo che fa per te.<html>";
		super.imageName = "./images/Immagini Profilo/avventuriero.png";
		
		//imgProfile = ImageIO.read(new File(imageName));

	}

}
