package it.unibs.eps;

import java.util.Random;

import javax.swing.JOptionPane;
import it.unibs.eps.dialog.Combattimento;
import it.unibs.eps.dialog.LoadOrSave;
import it.unibs.eps.giocatore.Giocatore;
import it.unibs.eps.pokemon.*;

public class Model extends BaseModel{
	private  LoadOrSave loadSave = new LoadOrSave();
	private Pokemon ps; 									// pokemon_selvatico trovato nella mappa dal giocatore
	private int indice = 0;
	private Pokemon pp; 									// pokemon_player
	public boolean enableMossa = false;
	public boolean enableAttacco = false;
	public Combattimento combattimento;
	public String stato = "";
	private Giocatore player;
	private MainForm foorm;
	boolean click = false;
	boolean carp = false;
	boolean speciale = false;

	public Giocatore getPlayer() {
		return player;
	}

	public void setPlayer(Giocatore player) {
		this.player = player;
	}
	
	public Pokemon getPs() {
		return ps;
	}

	public void setPs(Pokemon ps) {
		this.ps = ps;
	}

	public Pokemon getPp() {
		return pp;
	}

	public void setPp(Pokemon pp) {
		this.pp = pp;
	}

	public Model(MainForm mainform){
		this.foorm = mainform;
	}

	public void inizio(Giocatore player, Pokemon ps, Combattimento combattimento){
		player.setPunteggio(player.getPunteggio() + 10); //se trovo pokemon + 10 in punteggio
		//questo metodo "sostituisce" il costruttore, che appena inizia il combattimento setta i pokemon protagonisti
		//metodo da invocare non appena si apre la Dialog del combattimento: in base alle velocità dei pokemon, viene deciso chi attacca per primo
		this.player = player;
		this.ps = ps;
	/*	if(!player.squadra.isEmpty())*/
			this.pp = player.squadra.elementAt(indice);
	/*	else{
			combattimento.setVisible(false);
			foorm.frame.setVisible(false);
			foorm.dispose();
			JOptionPane.showMessageDialog(combattimento,"C'è stato un problema. Ci scusiamo per il disagio!" );
			System.out.println("exit!");
			System.exit(3);
		}*/

		this.combattimento = combattimento;

		stato = "<html>Fai del tuo meglio allenatore <html>"+player.nome+"!<html> Buon combattimento!";
		fireValuesChange();
		speed();
	}

	public void speed(){		//Metodo che decide chi attacca per primo
		if(ps.getAttacco().getSpeed() >= pp.getAttacco().getSpeed())
			mossaSelvatico();
		else{
			mossaPlayer();
		}
	}

	public void mossaPlayer(){
		if(pp.getVita() > 0){			// Se la vita del mio pokemon è >0 quella del'avversario cala in base alla potenza dell'attacco
			enableAttacco = true;
			enableMossa = false;
			if(pp.getVita() < pp.getLimite()){
				enableMossa = true;
				speciale = true;
			}
			fireValuesChange(enableMossa, enableAttacco);
		}
		else
			vittoriaSelvatico();
	}

	public void attacco(){
		if(click == true){
			ps.setVita(ps.getVita() - pp.getAttacco().getPotenzaAttacco());
			enableAttacco = false;
			click = false;
			fireValuesChange();
			System.out.printf("\nmioAtt = %s HO ATTACCATO: %s vita %s limite %s attacco %s- %s vita %s limite %s attacco %s\n", enableAttacco,pp.getNome(),pp.getVita(),pp.getLimite(),pp.getAttacco().getPotenzaAttacco(),ps.getNome(),ps.getVita(),ps.getLimite(),ps.getAttacco().getPotenzaAttacco());

			mossaSelvatico();
		}

	}

	public void mossa(){
		if(speciale == true){
			if(pp instanceof Magicarp)
				carp = true;
			
			pp.mossaSpeciale(ps,player,false);
			enableMossa = false;
			speciale = false;
			fireValuesChange();
			System.out.printf("\nHO MOSSA SPECIALE: %s vita %s limite %s attacco %s- %s vita %s limite %s attacco %s\n", pp.getNome(),pp.getVita(),pp.getLimite(),pp.getAttacco().getPotenzaAttacco(),ps.getNome(),ps.getVita(),ps.getLimite(),ps.getAttacco().getPotenzaAttacco());

			mossaSelvatico();
		}
	}

	public void mossaSelvatico(){
		if(ps.getVita() > 0){								//Se l'avversario è vivo può attaccare il giocatore
			Random r = new Random();
			int random = r.nextInt(2);	
			
			if(ps.getVita() > ps.getLimite()){								//Altrimenti attacca normalmente
				pp.setVita(pp.getVita() - ps.getAttacco().getPotenzaAttacco());
				System.out.printf("\nNORMALE NEMICO %s vita %s limite %s attacco %s- %s vita %s limite %s attacco %s\n",pp.getNome(),pp.getVita(),pp.getLimite(),pp.getAttacco().getPotenzaAttacco(),ps.getNome(),ps.getVita(),ps.getLimite(),ps.getAttacco().getPotenzaAttacco());
				stato = "<html>Ahhhh! diamine...<html>"+ps.getNome()+"<html> ha usato "+ps.getAttacco().getNomeAttacco();
			}
			if(ps.getVita() < ps.getLimite()){	//Se il limiteCorrente di vita è sotto il limite dell'avversario		
				if(random == 1){
					ps.mossaSpeciale(ps,player,true);			//C'è un 50% di probabilità che attacchi con la mossa speciale
					stato = "<html>" + ps.getNome()+" ha usato <html> "+ ps.getNomeMossa();
					System.out.printf("\nSPECIALE NEMICO %s vita %s limite %s attacco %s- %s vita %s limite %s attacco %s\n", pp.getNome(),pp.getVita(),pp.getLimite(),pp.getAttacco().getPotenzaAttacco(),ps.getNome(),ps.getVita(),ps.getLimite(),ps.getAttacco().getPotenzaAttacco());
				}else{
					pp.setVita(pp.getVita() - ps.getAttacco().getPotenzaAttacco());
					System.out.printf("\nNORMALE NEMICO %s vita %s limite %s attacco %s- %s vita %s limite %s attacco %s\n",pp.getNome(),pp.getVita(),pp.getLimite(),pp.getAttacco().getPotenzaAttacco(),ps.getNome(),ps.getVita(),ps.getLimite(),ps.getAttacco().getPotenzaAttacco());
					stato = "<html>Ahhhh! diamine...<html>"+ps.getNome()+"<html> ha usato "+ps.getAttacco().getNomeAttacco();
				}
			}
			if(carp){
				stato = "<html>OH NO!!! Magicarp ha dimezzato <html> la vita della squadra! <html>";
				carp = false;
			}
			fireValuesChange();
			mossaPlayer();
		}else
			vittoriaGiocatore();
	}

	public void vittoriaGiocatore(){
		player.setPunteggio(player.getPunteggio() + 5); // se vinco + 5 di punteggio
		player.sconfitti.addElement(ps);
		player.distrutti = player.distrutti + "\n" + ps.getNome() + " con attacco " + ps.getAttacco();
		JOptionPane.showMessageDialog(combattimento, "Ottimo lavoro allenatore! Hai sconfitto "+ ps.getNome()+"!");
		System.out.println(player.squadra);
		combattimento.perform();
		foorm.resetCombattimento(ps);
		System.out.println(foorm.panel.hmap);
	}

	public void vittoriaSelvatico(){

		if(player.squadra.size()>0)
		{
			Pokemon morto = player.squadra.elementAt(0);
			player.setPunteggio(player.getPunteggio() - 5);
			foorm.equipaggia.removeItem(morto); // rimuovo il pokemon dalla squadra
			player.squadra.remove(morto);
			JOptionPane.showMessageDialog(combattimento, "Ahahah pollo! Ti sei fatto sconfigere da "+ps.getNome());
			fireValuesChange();
			combattimento.perform();
			foorm.resetCombattimento(ps);
			System.out.println(foorm.panel.hmap);

			if(player.squadra.size()==0)
			{
				int opzione = JOptionPane.showConfirmDialog(combattimento, "GAME OVER!\nHai sconfitto:\n" + player.distrutti + "\nRiprovare?");

				System.out.println(opzione);
				if (opzione == 0)
				{
					chiudiFight();
					resetGioco();
				}
				else
				{
					JOptionPane.showMessageDialog(combattimento,"Alla prossima sfida codardo!" );
					System.out.println("exit now!");
					System.exit(3);
				}
			}
	
		}

	}

	protected void fireValuesChange(){
		fireValuesChange(false, false);
	}

	protected void fireValuesChange(boolean enableMossa, boolean enableAttacco) {
		fireValuesChange(new FightChangeEvent(this, enableMossa, enableAttacco));
		/**
		 * Chiama la classe FightChangeEvent passandogli il model ed un valore true
		 */
	}
	
	public void chiudiFight(){
		combattimento.setVisible(false);
		foorm.resetCombattimento(ps);
	}
	
	public void resetGioco(){
//		combattimento.setVisible(false);
//		foorm.resetCombattimento(ps);
		foorm.frame.setVisible(false);
		loadSave.showDialog();		
	}

}