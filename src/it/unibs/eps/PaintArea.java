package it.unibs.eps;

import it.unibs.eps.giocatore.Giocatore;
import it.unibs.eps.pokemon.Pokemon;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Robot;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;



public class PaintArea extends JPanel implements MouseListener,MouseMotionListener , Serializable {
	
	private Image mondoImage;
	private Image loadingImage;
	private Color color;
	private Robot robot;
	private Mondo mondo;
	private Vector<Pokemon> arrayPokemon = new Vector<Pokemon>();
	private Giocatore player1;
	private SettaMondo settaMondo;
	public HashMap<Point,Pokemon> hmap = null;//new HashMap<Point,Pokemon>();
	private BoardController boardController;
	private BoardController.BoardPosition mouseClick = null; 
	private BoardController.BoardPosition mousePoint = null;
	private Vector<Point> puntiBuoni=  new Vector<Point>();
	private Vector<Point> puntiNonBuoni=  new Vector<Point>();
	private Point posizioneCorrente; //Coodinate righe colonne
	private Point posizioneProssima;
	public int currenntTime = 0;
	public File fileMondo;
	public int rip = 0;
	public int wait = 0;
	public int posizionati = 0;
	public int f = 0 ;
	public boolean flagPrimaPosizione = true;
	public long startTime;
	public MainForm form;
	public boolean combattimentoAttivo = false;
	private Image erbaImage;
	private Image erbaSeccaImage;
	private Image albero;

	public PaintArea(Giocatore player, MainForm form) throws IOException{
		super(); // super perché classe estesa
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.player1 = player;
		this.form = form;
		boardController = new BoardController(this);

		setLayout(null);
		this.setBounds(0, 0, 331, 319);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 488, 341);
		panel.setLayout(null);
		panel.add(this);

		mondo = new Mondo();
		
		if(form.caricato == false){
			settaMondo = new SettaMondo(player1, arrayPokemon);
		}
		try {
			robot = new Robot();

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<Point> getPuntiBuoni() {
		return puntiBuoni;
	}

	public void setPuntiBuoni(Vector<Point> puntiBuoni) {
		this.puntiBuoni = puntiBuoni;
	}
	
	public Vector<Point> getPuntiNonBuoni() {
		return puntiNonBuoni;
	}

	public void setPuntiNonBuoni(Vector<Point> puntiNonBuoni) {
		this.puntiNonBuoni = puntiNonBuoni;
	}

	/*Disegno il mondo*/
	@Override
	protected void paintComponent(Graphics g) {

		int xDelPanel= (int)this.getLocationOnScreen().getX(); // ritorna posizione finestra (angolo alto sx)
		int yDelPanel= (int)this.getLocationOnScreen().getY(); // ritorna posizione finestra (angolo alto sx)
		int divisore = 16;
		int sz = (getHeight() < getWidth() ? getHeight() : getWidth()) / divisore; // sz = dimensione del panel
		super.paintComponent(g);
		NewImages();

		g.drawImage(mondoImage,0,0,sz*divisore,sz*divisore,this); // disegno immagine di sfondo
		
		if(posizionati == 1 )
		{
			form.miopokedex.setMap(hmap);
			form.miopokedex1.setMap(hmap);
			form.miopokedex.setPuntiBuoni(puntiBuoni);
			form.miopokedex1.setPuntiBuoni(puntiBuoni);
			form.miopokedex.setPuntiNonBuoni(puntiNonBuoni);
			form.miopokedex1.setPuntiNonBuoni(puntiNonBuoni);
		
	/*		for(Map.Entry<Point, Pokemon> kv: hmap.entrySet()){
				System.out.printf("Point %s, Pokemon %s, vita %d\n",kv.getKey().getX(),kv.getValue().getNome(),kv.getValue().getVita());
			}*/
			
			g.drawImage(loadingImage, sz*divisore, sz-20,((sz/2)*divisore),(sz-2)*divisore, this);
			while(f<2){
				f++;
			}
			if(wait == 9){
				startTime = System.currentTimeMillis ();
				posizionati = 0;
			}
		}
		
		fileMondo = new File(mondo.getImageName());
		
		wait++;
		if(wait<4 && hmap == null){
			g.drawImage(loadingImage, sz*divisore, sz-20,((sz/2)*divisore),(sz-2)*divisore, this);
		}
		if(wait == 4 && hmap == null){
			hmap = new HashMap<Point, Pokemon>();
			posizionaPokemon( sz, xDelPanel, yDelPanel,g); //Riempio Hash Map
			posizionati = 1;
		}
		if(wait > 4)
		{
			form.cercaPokemon();
			combattimentoAttivo  = true;
			
			if(!puntiBuoni.isEmpty()){
				for(Point p: puntiNonBuoni){
					g.drawImage(erbaImage,p.x*sz, p.y*sz, sz, sz, this);
					g.drawImage(albero,p.x*sz, p.y*sz, sz, sz, this);
				}
			}
			if(!puntiBuoni.isEmpty()){
				for(Point p: puntiBuoni){
					g.drawImage(erbaSeccaImage, p.x*sz, p.y*sz, sz, sz, this);
				}
			}
		}

		if(mousePoint != null){
			Point2D p = boardController.BoardToPoint(mousePoint);
			int r = (int)p.getX()/ sz;
			int c = (int)p.getY()/ sz;
			color = robot.getPixelColor (r*sz+xDelPanel,c*sz+yDelPanel);
			if(color.getRed() <= 10){
				g.setColor(Color.WHITE);
			}
			else{
				g.setColor(Color.WHITE);
			}
			g.drawRect(r* sz, c*sz, sz, sz);
		}

		if(mouseClick != null){
			for(int i = 0 ; i < puntiBuoni.size() ; i ++) //Disegna quadratino rosso per indicare dove mi vorrei spostare
			{
				if(mouseClick.getX() ==  puntiBuoni.elementAt(i).getX() && mouseClick.getY() == puntiBuoni.elementAt(i).getY())
				{
					g.setColor(color.RED);
					g.fillRect(mouseClick.getX()*sz, mouseClick.getY()*sz, sz, sz);					
				}
			}
			g.drawImage(new ImageIcon(player1.profilo.getImageName()).getImage(),(int)getPosizioneCorrente().getX()*sz,(int)getPosizioneCorrente().getY()*sz,sz,sz,null); // disegno pikachu se clicco mouse
			repaint();
		}else{
			if(!puntiBuoni.isEmpty())
			{
				if(flagPrimaPosizione)
				{
					setPosizioneCorrente(puntiBuoni.get(1));
					flagPrimaPosizione = false;
				}
				g.drawImage(new ImageIcon(player1.profilo.getImageName()).getImage(), getPosizioneCorrente().x*sz,getPosizioneCorrente().y*sz, sz, sz, this);//START
			}
		}

		if(!puntiBuoni.isEmpty()){
			for(Point p: puntiBuoni){
				g.setColor(Color.WHITE);
				g.drawOval(p.x*sz, p.y*sz, sz, sz);
				
				g.setColor(Color.RED);
				for(Map.Entry<Point, Pokemon> kv: hmap.entrySet()){
					g.drawOval((int)kv.getKey().getX()*sz, (int)kv.getKey().getY()*sz, sz, sz);
				}
			}
		}
	}
	
	/*Metodo per caricare immagini*/
	public void NewImages(){
		mondoImage = new ImageIcon(mondo.getImageName()).getImage();
		loadingImage =  new ImageIcon("loading.gif").getImage();
		erbaImage = new ImageIcon("./images/Immagini Mondo/erba.jpg").getImage();
		albero = new ImageIcon("./images/Immagini Mondo/alberoCresce.gif").getImage();
		erbaSeccaImage = new ImageIcon("./images/Immagini Mondo/erbaSecca.jpg").getImage();
	}

	public void posizionaPokemon(int sz,int xDelPanel,int yDelPanel, Graphics g) {
		/** Scorre con ciclo for arrayPokemon(ormai inizializzato con setta mondo)
		 * Hash Map con chiave le coordinate Point e come valore i Pokemon dell'array
		 * Con action listener si possono gestire i movimenti del giocatore
		 * man mano si muove, se le sue coordinate coincidono con qualche chiave, allora parte lo scontro 
		 */
		for(int i = 0 ; i < 16 ; i = i + 1){
			for(int j = 0 ; j < 16; j = j + 1){
				color = robot.getPixelColor(i*sz+xDelPanel + sz/2,j*sz+yDelPanel + sz/2); 	//Scorre ogni quadrato della scacchiera
				if(color.getRed() < 10){
					//e ne controlla il colore
					Point puntoBuono = new Point(i,j);							//se va bene, allora è un punto buono e lo salvo
					puntiBuoni.add(puntoBuono);								//è un punto in cui posso posizionare i pokemon
				}
				if(color.getRed()> 230) // se il colore é bianco
				{
					Point puntoNonBuono = new Point(i,j);
					puntiNonBuoni.addElement(puntoNonBuono);
				}
			}
		}

		if(puntiBuoni.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "noob! mappa non conforme alle specifiche di gioco!");
			System.exit(3);
		}
		
		for(int value = 0; value < arrayPokemon.size(); value++){
			Random r = new Random();
			int indexKey = r.nextInt(puntiBuoni.size());		//Scelto un point casuale tra i puntiBuoni
			Point key = puntiBuoni.elementAt(indexKey);
			if(!hmap.containsKey(key)){					  	  	//Se non è già contenuta questa coodinata casuale
				hmap.put(key, arrayPokemon.elementAt(value));	//inserisco un pokemon associato a quella chiave nella mappa
			}
			else
			{
				rip ++;
				value--;
				if(rip == 10)
				{
					JOptionPane.showMessageDialog(this, "noob! mappa non conforme alle specifiche di gioco!");
					System.exit(3);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClick = boardController.PointToBoard(e.getPoint()); //coordinate righe colonne
		Timer timer = new Timer(160, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ex) {
				if(getPosizioneCorrente().getX() < mouseClick.getX() ){
					for(int i = 0 ; i < puntiBuoni.size() ; i ++)
					{
						if((getPosizioneCorrente().getX() + 1) ==  puntiBuoni.elementAt(i).getX() && getPosizioneCorrente().getY() == puntiBuoni.elementAt(i).getY())
						{
							posizioneProssima = new Point((int)(getPosizioneCorrente().getX() + 1), (int) getPosizioneCorrente().getY());
						}	
					}
					getPosizioneCorrente().x = (int) (posizioneProssima.getX());
					getPosizioneCorrente().y = (int) getPosizioneCorrente().getY();
				}
				else if(getPosizioneCorrente().getX() > mouseClick.getX()){
					for(int i = 0 ; i < puntiBuoni.size() ; i ++)
					{
						if((getPosizioneCorrente().getX() - 1) ==  puntiBuoni.elementAt(i).getX() && getPosizioneCorrente().getY() == puntiBuoni.elementAt(i).getY())
						{
							posizioneProssima = new Point((int)(getPosizioneCorrente().getX() - 1), (int) getPosizioneCorrente().getY());
						}	

					}
					getPosizioneCorrente().x = (int) (posizioneProssima.getX());
					getPosizioneCorrente().y = (int) getPosizioneCorrente().getY();
				}
				else if(getPosizioneCorrente().getX() == mouseClick.getX() && getPosizioneCorrente().getY() < mouseClick.getY()){
					for(int i = 0 ; i < puntiBuoni.size() ; i ++)
					{
						if((getPosizioneCorrente().getX() ) ==  puntiBuoni.elementAt(i).getX() && getPosizioneCorrente().getY() + 1 == puntiBuoni.elementAt(i).getY())
						{
							posizioneProssima = new Point((int)(getPosizioneCorrente().getX() ), (int) getPosizioneCorrente().getY() +1);
						}	
					}
					getPosizioneCorrente().x = (int) getPosizioneCorrente().getX();
					getPosizioneCorrente().y = (int) posizioneProssima.getY(); 

				}
				else if(getPosizioneCorrente().getX() == mouseClick.getX() && getPosizioneCorrente().getY() > mouseClick.getY()){
					for(int i = 0 ; i < puntiBuoni.size() ; i ++)
					{
						if((getPosizioneCorrente().getX() ) ==  puntiBuoni.elementAt(i).getX() && getPosizioneCorrente().getY() -1 == puntiBuoni.elementAt(i).getY())
						{
							posizioneProssima = new Point((int)(getPosizioneCorrente().getX() ),(int) getPosizioneCorrente().getY() -1);
						}	
					}
					getPosizioneCorrente().x = (int) getPosizioneCorrente().getX();
					getPosizioneCorrente().y = (int) posizioneProssima.getY();

				}
				else{
					((Timer)ex.getSource()).stop();
				}
			}
		});
		timer.start(); //Dici al timer di partire e l'immagine si dovrebbe spostare
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	Point2D mousePointColor = null;
	@Override
	public void mouseMoved(MouseEvent e) {
		mousePoint =  boardController.PointToBoard(e.getPoint());//pixel to board
		mousePointColor = e.getPoint();
		repaint();
	}
	public Point getPosizioneCorrente() {
		return posizioneCorrente;
	}
	public void setPosizioneCorrente(Point posizioneCorrente) {
		this.posizioneCorrente = posizioneCorrente;
	}
}
//	public  int getColore(int riga, int colonna) throws IOException
//	{
//		
//		BufferedImage imageMondo = ImageIO.read(fileMondo);
//		byte [] pixels = ((DataBufferByte) imageMondo.getRaster().getDataBuffer()).getData();
//		int [][]result = new int [imageMondo.getHeight()][imageMondo.getWidth()];
//		int pixelLenght = 3; // se nn cé canale alpha ogni pixel ha 8 bit {R G B}--> 16 bit
//	
//		for (int pixel = 0 , row = 0 , col = 0; pixel < pixels.length; pixel = pixel + pixelLenght )
//		{
//			int RGB = 0;
//			RGB+= ((int) pixels[pixel] & 0xff); // estraggo BLUE (che é nei 8 bit)
//			RGB+=((int) pixels[pixel + 1] & 0xff << 8); // estraggo GREEN
//			RGB+=((int) pixels [pixel + 2] & 0xff << 16); // estraggo RED
//			result[row][col] = RGB;
//			
//			col++;
//			if(col == imageMondo.getWidth())
//			{
//				col = 0;
//				row ++;
//			}
//		}
//		return result[riga][colonna];
//    }