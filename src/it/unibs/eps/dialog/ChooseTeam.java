package it.unibs.eps.dialog;
import it.unibs.eps.FileUtils;
import it.unibs.eps.MainForm;
import it.unibs.eps.PaintPanel;
import it.unibs.eps.giocatore.Giocatore;
import it.unibs.eps.pokemon.Attacco;
import it.unibs.eps.pokemon.Bulbasaur;
import it.unibs.eps.pokemon.Charmander;
import it.unibs.eps.pokemon.Magicarp;
import it.unibs.eps.pokemon.Pikachu;
import it.unibs.eps.pokemon.Pokemon;
import it.unibs.eps.pokemon.Snorlax;
import it.unibs.eps.pokemon.Squirtle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Image;

public class ChooseTeam extends MyDialog  {
	
	public Snorlax snorlax;
	public Pikachu pikachu;
	public Bulbasaur bulbasaur;
	public Charmander charmander;
	public Squirtle squirtle;
	public Magicarp magicarp;
	public Giocatore player;
	public ArrayList<Pokemon> arrayPokemon = new ArrayList<Pokemon>();
	private String fname = new String();
	private String fpok = new String();
	private Attacco attacco;
	private PaintPanel paintpnl;
	private int numAttacchi = 5;
	private int numPokemon = 6;
	private FileUtils fu = new FileUtils();
	private JCheckBox chkbox;
	private JComboBox<Attacco> combobox;
	private HashMap<JComboBox,Pokemon> map = new HashMap<JComboBox,Pokemon>();
	private HashMap<JCheckBox,Pokemon> mapCheck = new HashMap<JCheckBox,Pokemon>();
	private Image imagePokemon;
	
	/**
	 * 
	 * Per aggiungere nuovi pokemon occorre crearne gli oggetti vuoti in questa classe, modificare numPokemon 
	 * e aggiungerne i parametri nel file di testo fpok nominato ParamPokemon e in arrayPokemon.
	 * ATTENZIONE: l'ordine con cui si inseriscono i pokemon "vuoti" nell'arrayPokemon è importante.
	 * Per aggiungere nuovi attacchi è sufficiente modificare numAttacchi e aggiungerne i parametri nel file di
	 * testo fname nominato ParamAttacchi.
	 */
	
	public ChooseTeam(Giocatore player) throws IOException {
		
		pikachu = new Pikachu();
		snorlax = new Snorlax();
		bulbasaur = new Bulbasaur();
		charmander = new Charmander();
		squirtle = new Squirtle();
		magicarp = new Magicarp();
		fname = "./text/ParamAttacchi.txt";
		fpok = "./text/ParamPokemon.txt";
		this.player = player;
		arrayPokemon.add(snorlax);
		arrayPokemon.add(pikachu);
		arrayPokemon.add(bulbasaur);
		arrayPokemon.add(charmander);
		arrayPokemon.add(squirtle);
		arrayPokemon.add(magicarp);
		
		setPokemon();
		
		setBounds(100, 100, 576, 454);
		getContentPane().setLayout(null);
		
		JPanel btnpnl = new JPanel();					//Pannello dei bottoni ok e indietro
		btnpnl.setBounds(10, 351, 540, 54);
		getContentPane().add(btnpnl);
		btnpnl.setLayout(null);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(12, 11, 69, 35);
		btnpnl.add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(player.squadra.size()==0)
				{
					JOptionPane.showMessageDialog(btnOk, "attenzione devi selezionare almeno un pokemon!");
				}
				else if(player.squadra.get(0).getAttacco() == null){
					JOptionPane.showMessageDialog(btnOk, "attenzione seleziona l'attacco utilizzando le combobox messe a disposizione");
				}else{
					MainForm form;
					try {
						form = new MainForm(player);
						form.frame.setVisible(true);
						perform();
						}catch (IOException e1) {
							e1.printStackTrace();
							}				
					}
				}
			});
		
		JButton btnCancel = new JButton("Torna Indietro");
		btnCancel.setBounds(117, 11, 167, 35);
		btnpnl.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int opzione = JOptionPane.showConfirmDialog(btnCancel, "Sei sicuro di voler uscire e tornare indietro?");
					if(opzione == 0){
						ChooseProfile cp = new ChooseProfile();
						cp.showDialog();
						perform();
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JPanel panel = new JPanel();					//Pannello generico
		panel.setBounds(10, 11, 540, 329);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblDescrizione = new JLabel("");
		lblDescrizione.setBounds(345, 167, 185, 151);
		panel.add(lblDescrizione);
		
		paintpnl = new PaintPanel();			//Pannello di disegno immagine pokemon
		paintpnl.setBounds(345, 11, 185, 145);
		panel.add(paintpnl);
		paintpnl.setLayout(null);
		
		JPanel checkpnl = new JPanel();					//Pannello dei checkbox
		checkpnl.setBounds(10, 11, 133, 307);
		panel.add(checkpnl);
		checkpnl.setLayout(null);
		
		JPanel combopnl = new JPanel();					//Pannello dei combobox
		combopnl.setBounds(153, 11, 182, 307);
		panel.add(combopnl);
		combopnl.setLayout(null);
		
		int x = 5;//7
		int y = x;
		for(Pokemon p: arrayPokemon){
			
			chkbox = new JCheckBox(p.getNome());		//Creo tanti checkbox quanti ne servono per ogni pokemon
			chkbox.setBounds(10, x, 100, 23);
			checkpnl.add(chkbox);
			mapCheck.put(chkbox,p);						//Li aggiungo nell'hashmap in corrispondenza di un pokemon
		
			x = x + 32;
			
			combobox = new JComboBox<Attacco>();		//Creo tanti combobox quanti ne servono per i pokemon
			combobox.setBounds(30, y, 135, 20);
			combopnl.add(combobox);
			additem(combobox);
			map.put(combobox,p);						//Li aggiungo nell'hashmap in corrispondenza di un pokemon
			
			y = y + 33;
		}
		
		for(Map.Entry<JComboBox,Pokemon> kv: map.entrySet()){		//Scorro tutti i combobox
			JComboBox combobox = kv.getKey();
			combobox.addActionListener(new ActionListener() {		//A tutti aggiungo un action listener
				@Override
				public void actionPerformed(ActionEvent e) {
					kv.getValue().setAttacco((Attacco) combobox.getSelectedItem());
				}
			});
		}
			
		for(Map.Entry<JCheckBox,Pokemon> kv: mapCheck.entrySet()){	//Scorro tutti i checkbox
			JCheckBox chkbox = kv.getKey();
			chkbox.addActionListener(new ActionListener() {			//A tutti aggiungo un action Listener
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(chkbox.isSelected()){									//Se è selezionato il check
						player.squadra.addElement(kv.getValue());				//Gli aggiungo quel pokemon in squadra
						lblDescrizione.setText(kv.getValue().getDescrizione());	//Rendo visibile la descrizione		
						imagePokemon = new ImageIcon(kv.getValue().getImageName()).getImage();
						disegna();												//Rendo visibile l'immagine del pokemon scelto
					}
					else if(!chkbox.isSelected()){
						player.squadra.remove(kv.getValue());
					}
				}
			});
		}
	}
	
	private void disegna(){
		paintpnl.setImg(imagePokemon, 0, 0,200);
	}
	
	private void setPokemon() throws IOException{
		ArrayList<String> lista;
		for(int i = 0; i < numPokemon; i++){
			lista = fu.readResource(fpok, i+1);

			arrayPokemon.get(i).setNome(lista.get(0));
			arrayPokemon.get(i).setImageName(lista.get(1));
			arrayPokemon.get(i).setVita(Integer.parseInt(lista.get(2)));
			arrayPokemon.get(i).setDescrizione(lista.get(3));
			arrayPokemon.get(i).setLimite(Integer.parseInt(lista.get(4)));
			arrayPokemon.get(i).setNomeMossa(lista.get(5));
		}
	}
	
	private void additem(JComboBox<Attacco> cb) throws IOException{
		ArrayList<String> lista;
		
		for(int i = 1; i <= numAttacchi; i++){
			lista = fu.readResource(fname, i);
			attacco = new Attacco(lista.get(0),Integer.parseInt(lista.get(1)),lista.get(2),Integer.parseInt(lista.get(3)));
			//Nel costruttore di attacco: nomeAttacco, potenza ed immagine
			cb.addItem(attacco);
		}
	}
}