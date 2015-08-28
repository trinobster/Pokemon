package it.unibs.eps.giocatore;


import java.io.IOException;

public class Profilo1 extends ProfiloGiocatore{
	
	public Profilo1() throws IOException{
		super.nome = "Team Rocket";
		super.descrizione = "<html>Il Team Rocket trama di catturare<html><html> tutti i pokémon "
				+ "per poi venderli a scrupolosi collezionisti.<html>"
				+ "<html>Se scegli di entrare nel Team Rocket come recluta,<html>"
				+ "<html>potrai essere ricco e spietato.<html>";
		super.imageName = "./images/Immagini Profilo/recluta.png";
		
	//	imgProfile = ImageIO.read(new File(imageName));
	//	imgProfile = (ImageSer) new ImageIcon(imageName).getImage();

	}

}
