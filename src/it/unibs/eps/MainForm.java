package it.unibs.eps;
import it.unibs.eps.dialog.Combattimento;
import it.unibs.eps.dialog.LoadOrSave;
import it.unibs.eps.dialog.MyDialog;
import it.unibs.eps.giocatore.Giocatore;
import it.unibs.eps.pokemon.Pokemon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.net.URL;

import javax.swing.JToggleButton;

//import javafx.application.Application;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.stage.Stage;

public class MainForm extends MyDialog implements Serializable{
	public JFrame frame;
	private static LoadOrSave loadSave = new LoadOrSave();
	public Giocatore player;
	public PaintArea panel;
	private JLabel labelTime;
	private JLabel lblPuneggio;
	private Timer timer;
	final private static int minutiGioco = 1;
	final private static int secondiGioco = 30;
	final private static String NOMEFILETITOLI = "pokemon.dat";
	final private static String MSG_SALVA = "SALVATAGGIO DATI";
	public Pokedex miopokedex = new Pokedex();
	public Pokedex miopokedex1 = new Pokedex();
	public static File fTitoli = new File(NOMEFILETITOLI);
	private Model model;
	private Controller controller;
	private Combattimento combattimento;
	public int combattimentoAttivo = 0;
	public int punteggio = 0;
	public JComboBox<Pokemon> equipaggia;
	public boolean caricato = false;
//	private JLabel sconfitti;
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//fTitoli	= new File(NOMEFILETITOLI);
					loadSave.setFtitoli(fTitoli);
					loadSave.showDialog();

//					MainForm window = new MainForm(null);
//					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @wbp.parser.constructor
	 */
	public MainForm(Giocatore player) throws IOException {
		this.player = player;
		initialize();
	}

	public MainForm(Giocatore player, HashMap<Point,Pokemon> mappaCaricata, Vector<Point> puntiCaricati, Vector<Point> puntiNonBuoni) throws IOException{
		this.player = player;
		caricato = true;
		panel =  new PaintArea(player, this);
		panel.hmap = mappaCaricata;
		panel.posizionati = 1;
		panel.setPuntiBuoni(puntiCaricati);
		panel.setPuntiNonBuoni(puntiNonBuoni);
		initialize();
		//Costruttore usato nel caricamento
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException  {

		frame = new JFrame();
		frame.setBounds(100, 100, 800, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(caricato == false){									//Se non c'è stato caricamento, creo paint area
			panel =  new PaintArea(player, this);
		}

		frame.getContentPane().add(panel, BorderLayout.CENTER);

		miopokedex.setGiocatore(player);
		miopokedex.punteggio = 1;
		miopokedex1.setGiocatore(player);
		miopokedex1.punteggio = 1;

		JPanel panelRisultati = new JPanel();
		panelRisultati.setBounds(300, 300, 300, 400);

		JButton save = new JButton("Save?");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(MSG_SALVA);
				JOptionPane.showMessageDialog(frame, MSG_SALVA);
				ServizioFile.salvaSingoloOggetto(fTitoli, miopokedex);
			}
		});


		panelRisultati.add(save);

		equipaggia = new JComboBox<Pokemon>();
		for (Pokemon p : player.squadra)
		{
			equipaggia.addItem(p);
		}
		equipaggia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(player.squadra.size() > 1){
					Pokemon nuovoPrimo = (Pokemon) equipaggia.getSelectedItem();
					Pokemon spostalo = player.squadra.elementAt(0);
					int index = 0;

					for(int i = 0; i < player.squadra.size(); i++){
						if(player.squadra.elementAt(i) == null)
							System.out.printf("pokemon all'indice %d NULL\n",i);
						if(nuovoPrimo.getNome().equals(player.squadra.elementAt(i).getNome())){
							index = i;
						}
					}
					player.squadra.setElementAt(nuovoPrimo, 0);
					player.squadra.setElementAt(spostalo, index);

				}
			}
		});
		panelRisultati.add(equipaggia);


	
		JPanel panelTime = new JPanel();
		panelTime.setBounds(100, 200, 300, 400);
		labelTime = new JLabel (" Time : 0:00:00.0");
		labelTime.setFont (new Font ("SansSerif", Font.BOLD, 30));
		labelTime.setHorizontalAlignment (JLabel.CENTER);
		labelTime.setForeground(Color.BLUE);
		
		lblPuneggio = new JLabel("Punti " + punteggio);
		lblPuneggio.setFont (new Font ("SansSerif", Font.BOLD, 30));
		lblPuneggio.setForeground(Color.RED);
	
		panelTime.add(lblPuneggio);
		panelTime.add(labelTime);
		Timing();
		
		frame.getContentPane().add(panelRisultati, BorderLayout.EAST);
		
//		sconfitti = new JLabel("Sconfitti:");
//		panelRisultati.add(sconfitti);
		frame.getContentPane().add(panelTime, BorderLayout.SOUTH);
//		sconfitti.setFont(new Font ("SansSerif", Font.BOLD, 15));
//		sconfitti.setForeground(Color.GREEN);

		model = new Model(this);
		controller = new Controller(model);
		combattimento = new Combattimento(model,controller);
		model.addChangeListener(combattimento.fightpanel);
		model.addChangeListener(combattimento.btnpanel);

		//	     Media media = new Media("a.mp3");
		//	     MediaPlayer mediaPlayer = new MediaPlayer(media);
		//	    mediaPlayer.play();

		//	    primaryStage.setTitle("Audio Player 1");
		//	    primaryStage.setWidth(200);
		//	    primaryStage.setHeight(200);
		//	    primaryStage.show();
	}
	
	public void resetCombattimento(Pokemon selvatico){
		Point chiave = new Point();
		combattimentoAttivo = 0;
		for(Map.Entry<Point, Pokemon> kv: panel.hmap.entrySet()){
			if(kv.getValue().equals(selvatico)){
				chiave = kv.getKey();
			}
		}
		panel.hmap.remove(chiave);
	}
	
	public void cercaPokemon(){
		if(combattimentoAttivo < 1){
			for(Map.Entry<Point, Pokemon> kv: panel.hmap.entrySet()){
				if((panel.getPosizioneCorrente().getX() == kv.getKey().getX()) && (panel.getPosizioneCorrente().getY() == kv.getKey().getY())){
					controller.getParametri(player, kv.getValue(), combattimento);
					combattimento.showDialog();
					controller.inizia();
					
					if((panel.getPosizioneCorrente().getX() == kv.getKey().getX()) && (panel.getPosizioneCorrente().getY() == kv.getKey().getY())){
						combattimentoAttivo++;
					}else{
						combattimentoAttivo = 0;
					}
				}
			}
		}
	}

	public void Timing(){
		//startTime = System.currentTimeMillis ();
		timer = new Timer (50, new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				
				//aggiorno il punteggio ogni 50 mms
				punteggio = player.getPunteggio();
				String punti = String.format ("%d",punteggio);
				lblPuneggio.setText("Score: " + punti);
				
				
				if(panel.wait > 7)
				{
					long diffTime = System.currentTimeMillis () - panel.startTime;
					int decSeconds = (int) (diffTime % 1000 / 100);
					int seconds = (int) (diffTime / 1000 % 60);
					int minutes = (int) (diffTime / 60000 % 60);
					int hours = (int) (diffTime / 3600000);
					String s = String.format (" Time : %d:%02d:%02d.%d", hours, minutes,seconds, decSeconds);			
					labelTime.setText (s);
					
			//		sconfitti.setText(player.distrutti);
					
					if(panel.hmap.isEmpty() && !player.squadra.isEmpty()){
						JOptionPane.showMessageDialog(null, "Hai affrontato tutti i pokemon!\nIl tuo punteggio è: "+player.punteggio + "\nHai sconfitto:\n" + player.distrutti);
						System.exit(EXIT_ON_CLOSE);
					}
					
					if(seconds == secondiGioco && minutes == minutiGioco)
					{
						int opzione = JOptionPane.showConfirmDialog(frame, "GAME OVER!\nHai sconfitto:\n" + player.distrutti + "\nRiprovare?");
						System.out.println(opzione);
						if (opzione == 0)
						{
							resetGioco();
						}
						else
						{
							try {
								Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=-YEG9DgRHhA").toURI());
							} catch (Exception ex) {}
							JOptionPane.showMessageDialog(frame,"Alla prossima sfida codardo!" );
							System.out.println("exit now!");
							try {
								Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=-YEG9DgRHhA").toURI());
							} catch (Exception ex) {}
							System.exit(EXIT_ON_CLOSE);
						}
					}	
				}
			}
		});
		timer.start ();
	}

	public void resetGioco(){
		frame.setVisible(false);
		combattimento.setVisible(false);
		loadSave.showDialog();		
	}
}