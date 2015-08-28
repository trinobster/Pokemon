package it.unibs.eps;

public class Mondo {
	private String imageName = "./images/Immagini Mondo/mondo4.png";
	
	public String getImageName() {
		return imageName;
	}
	
	public void setImageName(String imageName) { // usare quando cambio mondo (se raggiungo contorno carico un altra immagine)
		this.imageName = imageName;
	}
	

}
