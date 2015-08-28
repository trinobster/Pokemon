package it.unibs.eps.dialog;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import it.unibs.eps.Mondo;
import it.unibs.eps.PaintPanel;
import it.unibs.eps.giocatore.Giocatore;
import it.unibs.eps.giocatore.Profilo1;
import it.unibs.eps.giocatore.Profilo2;
import it.unibs.eps.giocatore.ProfiloGiocatore;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class ChooseProfile extends MyDialog implements Serializable {

	private final JPanel contentPanel = new JPanel();
	private static LoadOrSave loadSave = new LoadOrSave();
	private JTextField textField;
	private JComboBox<ProfiloGiocatore> comboBox;
	private Profilo1 profiloTR = new Profilo1();
	private Profilo2 profiloH = new Profilo2();
	public Giocatore player;
	private JLabel lblProfile;
	private JLabel lbldescr;
	private PaintPanel pnlDescription;
	

	/**
	 * Launch the application.
	 */

/*	public static void main(String[] args) {

		try {
			//	ChooseProfile dialog = new ChooseProfile();
			//	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			//	dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 * @throws IOException 
	 */

	public ChooseProfile()  throws IOException  {

		setBackground(UIManager.getColor("control"));
		player = new Giocatore();
		setBounds(100, 100, 604, 416);
		getContentPane().setLayout(null);
		
		contentPanel.setBounds(0, 0, 489, 297);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNome = new JLabel("Nome:");
			lblNome.setBounds(10, 11, 64, 27);
			contentPanel.add(lblNome);
		}

		textField = new JTextField();						
		textField.setBounds(10, 49, 162, 34);
		contentPanel.add(textField);
		textField.setColumns(10);

		lblProfile = new JLabel("Scegli il tuo profilo:");
		lblProfile.setBounds(10, 89, 125, 27);
		contentPanel.add(lblProfile);

		comboBox = new JComboBox<ProfiloGiocatore>();
		comboBox.setBounds(10, 124, 162, 38);
		comboBox.addItem(profiloTR);
		comboBox.addItem(profiloH);
		contentPanel.add(comboBox);

		pnlDescription = new PaintPanel();
		pnlDescription.setBounds(182, 11, 285, 288);
		contentPanel.add(pnlDescription);
		pnlDescription.setLayout(null);
		
		lbldescr = new JLabel(" ");
		lbldescr.setBounds(12, 13, 257, 266);
		pnlDescription.add(lbldescr);
		lbldescr.setVerticalAlignment(SwingConstants.TOP);
		lbldescr.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lbldifficolta = new JLabel("Scegli la difficoltà");
		lbldifficolta.setBounds(10, 183, 162, 14);
		contentPanel.add(lbldifficolta);

		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player.difficolta = (String) comboBox_1.getSelectedItem();
			}
		});
		comboBox_1.setBounds(10, 208, 162, 38);
		comboBox_1.addItem("Facile");
		comboBox_1.addItem("Medio");
		comboBox_1.addItem("Difficile");
		contentPanel.add(comboBox_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 293, 574, 77);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ChooseTeam ct;
						try {
							if(player.profilo == null)
								player.profilo = new Profilo1();		// Di default è Team Rocket il profilo	
							ct = new ChooseTeam(player);				//Se OK, avanti con la creazione
							ct.showDialog();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						perform();
					}
				});
				okButton.setBounds(12, 24, 65, 33);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}

			{
				JButton cancelButton = new JButton("Exit");		//Se NO, Esce e rende la dialog invisibile
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int opzione = JOptionPane.showConfirmDialog(cancelButton, "Sei sicuro di voler uscire?");
						if(opzione == 0)
							perform();
					}
				});
				cancelButton.setBounds(86, 24, 77, 33);
				cancelButton.setActionCommand("Exit");
				buttonPane.add(cancelButton);
			}

			JButton btnChoosemap = new JButton("Choose Map Img");
			btnChoosemap.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChoser = new JFileChooser();
					//dir = "";
					fileChoser.setCurrentDirectory(null);
					fileChoser.setDialogTitle("Scegli la mappa");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"); // metto filtro solo ad immagini
					fileChoser.setFileFilter(filter);
					
					int n = fileChoser.showOpenDialog(ChooseProfile.this);
					File mondoScelto = fileChoser.getSelectedFile();
					Mondo mondo = new Mondo();
					if(mondoScelto != null)
					{
						BufferedImage bufferedImage = null;
						try {
							bufferedImage = ImageIO.read(mondoScelto);			            
							ImageIO.write(bufferedImage, "png",new File(mondo.getImageName()/*"mondo4.png"*/));// sovrascrivo immagine scelta se la scelgo
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			});
			btnChoosemap.setBounds(175, 5, 152, 25);
			buttonPane.add(btnChoosemap);

			JButton button = new JButton("CaptureMap (paint demo)");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(contentPanel, "Divertiti a disegnare il tuo mondo usando Paint,\nSalva poi L'immagine creata nel tuo FileSystem\n e poi utilizza 'Choose Map Img' per caricarla nel gioco!");
					try {
						Runtime.getRuntime().exec("C:\\Windows\\System32\\mspaint.exe", null, new File("C:\\Windows\\System32\\"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			button.setBounds(357, 5, 205, 25);
			buttonPane.add(button);

			JButton button_1 = new JButton("CaptureProfile (online demo)");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(contentPanel, "Sorridi! e scattati una foto su pixect.com,\nSalvala nel tuo FileSystem\n e poi utilizza 'Choose Prof Img' per caricarla nel gioco!");
					try {
						Desktop.getDesktop().browse(new URL("http://www.pixect.com/it/").toURI());
					} catch (Exception ex) {}
				}
			});
			button_1.setBounds(357, 43, 205, 25);
			buttonPane.add(button_1);


			// LISTENERS:
			
			textField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					player.nome = textField.getText(); 
				}
			});
			
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProfiloGiocatore profilo  = (ProfiloGiocatore) comboBox.getSelectedItem();
					player.profilo = profilo;

					int posizioneX = 134/2;
					int posizioneY = 100;
					pnlDescription.setImg(new ImageIcon(profilo.getImageName()).getImage(),posizioneX,posizioneY,100);

					try {
						setIconImage(ImageIO.read(new File(player.profilo.getImageName())));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					lbldescr.setText(profilo.getDescrizione());
				}
			});

			JButton button_2 = new JButton("Choose Prof Img");
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChoser = new JFileChooser();
					fileChoser.setCurrentDirectory(null);
					fileChoser.setDialogTitle("Scegli immagine di profilo");

					FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"); // metto filtro solo ad immagini
					fileChoser.setFileFilter(filter);
					int n = fileChoser.showOpenDialog(ChooseProfile.this);
					File profiloScelto = fileChoser.getSelectedFile();
					System.out.println(comboBox.getSelectedItem());

					if(profiloScelto != null && player.profilo != null)
					{
						System.out.println(player.profilo.getName());  // se ho scelto profilo1 sovrascivo immagine di profilo1 con quella scelta
							/// sovrascrivo immagine scelta se la scelgo
							BufferedImage bufferedImage = null;
							try {
								bufferedImage = ImageIO.read(profiloScelto);			            
								ImageIO.write(bufferedImage, "png",new File(player.profilo.getImageName()));
								System.out.printf("HO SOVRASCRITTO IMG PROFILE?\n");
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						JOptionPane.showMessageDialog(buttonPane, "Ottima scelta!\nIl gioco dovrá essere riavviato per rendere attive le modifiche!");
						resetGioco();
					}
					else{
						JOptionPane.showMessageDialog(contentPanel, "Se certo..\nMagari seleziona prima Il profilo che vuoi modificare!");
					}
				}
			});
			button_2.setBounds(175, 43, 152, 25);
			buttonPane.add(button_2);
		} // end costruttore
	}
	public void resetGioco() {
		System.exit(EXIT_ON_CLOSE);
	}
}