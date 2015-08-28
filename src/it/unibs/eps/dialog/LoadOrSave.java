package it.unibs.eps.dialog;

import it.unibs.eps.MainForm;
import it.unibs.eps.Pokedex;
import it.unibs.eps.ServizioFile;
import it.unibs.eps.giocatore.Giocatore;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class LoadOrSave extends MyDialog {
	
	private final JPanel contentPanel = new JPanel();
	final private static String MSG_NO_CAST = "ATTENZIONE PROBLEMI CON IL CAST";
	final private static String MSG_OK_FILE = "CARICAMENTO DA FILE EFFETTUATO";
	final private static String MSG_NO_FILE = "NON POSSO CARICARE DA FILE: ESEGUI UNA NUOVA PARTITA";
	final private static String NOMEFILETITOLI = "pokemon.dat";
	File fTitoli;
//	PaintArea panel;
	MainForm form;
	
	 Pokedex miopokedex ;
	 Pokedex miopokedex1;
	 
	 static Giocatore g = new Giocatore();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoadOrSave dialog = new LoadOrSave();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			System.out.println(g.squadra);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LoadOrSave() {
		setBounds(100, 100, 318, 153);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 302, 115);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			JButton NGbtn = new JButton("Nuova partita");		//ACTION PERFORMED: NEW GAME CREATION
			NGbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ChooseProfile cp;							//CREA LA JDIALOG PER LA SCELTA DEL PROFILO GIOCATORE
					try {
						cp = new ChooseProfile();
						cp.showDialog();
						cp.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						perform();
					} catch (IOException e1) {
						e1.printStackTrace();
					}		
				}
			});
			NGbtn.setBounds(29, 56, 119, 48);
			contentPanel.setLayout(null);
			contentPanel.add(NGbtn);
			NGbtn.setActionCommand("Nuova partita");
			getRootPane().setDefaultButton(NGbtn);
		}
		{
			JLabel lbl = new JLabel("Vuoi caricare una partita o crearne una nuova?");
			lbl.setBounds(10, 2, 292, 43);
			contentPanel.add(lbl);
		}
		{
			JButton LGbtn = new JButton("Carica partita");
			LGbtn.setBounds(158, 56, 119, 48);
			LGbtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					////
					
				//	File fTitoli = new File(NOMEFILETITOLI);
					boolean caricamentoRiuscito = false;
					
					miopokedex = new Pokedex();
					miopokedex1 = new Pokedex();
					
					if(fTitoli == null){
						JOptionPane.showMessageDialog(contentPanel, "Qualcosa è andato storto... riprova a caricare. Ci scusiamo per il disagio\n");
						perform();
					}
					else if( fTitoli.exists() )
					{
						try 
						{
							miopokedex = (Pokedex) ServizioFile.caricaSingoloOggetto(fTitoli);
						}
						catch (ClassCastException ex)
						{
							System.out.println(MSG_NO_CAST);
							JOptionPane.showMessageDialog(contentPanel, MSG_NO_CAST);
						}
						finally
						{
							if ((miopokedex != null) )
							{
								System.out.println(MSG_OK_FILE);
							JOptionPane.showMessageDialog(contentPanel, MSG_OK_FILE);
								try {
									form = new MainForm(miopokedex.getGiocatore(), miopokedex.getMap(), miopokedex.getPuntiBuoni(), miopokedex.getPuntiNonBuoni());
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								form.frame.setVisible(true);

								caricamentoRiuscito = true;
								System.out.printf("\n CARICOOOO punti %s %s-%s-%s-%s-%d-%s", miopokedex.getGiocatore().getPunteggio(),miopokedex.getGiocatore().nome,miopokedex.getGiocatore().profilo, miopokedex.getGiocatore().squadra.elementAt(0).getNome(),miopokedex.getGiocatore().squadra.elementAt(0).getAttacco(),miopokedex.punteggio,miopokedex.getMap().toString());
								setVisible(false); //TODO: controllare
							}
						}
					}
					if (!caricamentoRiuscito)
					{
						System.out.println(MSG_NO_FILE);
						JOptionPane.showMessageDialog(contentPanel, MSG_NO_FILE);

						miopokedex = miopokedex1;

					}
					
					//////
					
				}
			});
			
			contentPanel.add(LGbtn);
			LGbtn.setActionCommand("Carica partita");
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 239, 434, 23);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
		}
	} // end costruttore

	public File getFtitoli() {
		return fTitoli;
	}

	public void setFtitoli(File fTitoli) {
		this.fTitoli = fTitoli;
	}
	
	
}
