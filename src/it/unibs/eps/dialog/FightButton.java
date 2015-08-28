package it.unibs.eps.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import it.unibs.eps.Controller;
import it.unibs.eps.FightChangeEvent;
import it.unibs.eps.Model;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FightButton extends JPanel implements ChangeListener{
	
	JButton btnAttacca;
	JButton button;
	JButton btnSpeciale;
	Controller controller;
	Model model;
	int c = 0;

	
	public FightButton(Model model,Controller controller){
		
		this.controller = controller;
		this.model = model;
		
		btnAttacca = new JButton("ATTACCA");
		btnAttacca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.attacca();
			}
		});
		add(btnAttacca);
		
		button = new JButton("Scappa");
		add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.getPlayer().setPunteggio(model.getPlayer().getPunteggio() - 5);
				model.chiudiFight();
			}
		});
		
		btnSpeciale = new JButton("MOSSA SPECIALE");
		btnSpeciale.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.mossaSpeciale();
			}
		});
		add(btnSpeciale);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		if(!model.equals(e.getSource()) || !(e instanceof FightChangeEvent))
			return;
		
		btnAttacca.setText(controller.getPp().getAttacco().getNomeAttacco());
		btnSpeciale.setText(model.getPp().nomeMossa);
		
		FightChangeEvent ec = (FightChangeEvent)e;
		
		if(ec.isBtnEnabled()){						//Se è stato abilitato, posso usare la mossa speciale
			btnSpeciale.setEnabled(true);
		}
		
		if(ec.possoAttaccare() == true){
			btnAttacca.setEnabled(true);
		}
	}

}