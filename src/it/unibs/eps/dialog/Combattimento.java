package it.unibs.eps.dialog;

import it.unibs.eps.Controller;
import it.unibs.eps.Model;
import java.awt.GridLayout;

public class Combattimento extends MyDialog{
	
	public FightPanel fightpanel;
	public FightButton btnpanel;
	
	public Combattimento(Model model, Controller controller) {
		getContentPane().setLayout(null);
		setBounds(0, 2, 652, 425);
		
		btnpanel = new FightButton(model,controller);
		btnpanel.setBounds(0, 315, 636, 72);
		getContentPane().add(btnpanel);
		btnpanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		fightpanel = new FightPanel(controller);
		fightpanel.setBounds(0, 0, 636, 315);
		getContentPane().add(fightpanel);
		fightpanel.setLayout(null);	
	}
}
