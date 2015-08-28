package it.unibs.eps.dialog;

import it.unibs.eps.Controller;
import it.unibs.eps.Model;
import it.unibs.eps.PaintPanel;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FightPanel extends JPanel implements ChangeListener{
	
	private Controller controller;
	public JLabel lblComputer;
	JLabel lblPlayer;
	PaintPanel ImmComputer;
	PaintPanel ImmPlayer;
	JProgressBar progressBarPlayer;
	JProgressBar progressBarComputer;
	public JLabel lblstatoAttacco;
	
	public FightPanel(Controller controller){
		this.controller = controller;
		
		ImmComputer = new PaintPanel();
		ImmComputer.setBounds(483, 11, 300, 200);
		add(ImmComputer);
		
		ImmPlayer = new PaintPanel();
		ImmPlayer.setBounds(10, 171, 300, 200);
		add(ImmPlayer);
		
		progressBarComputer = new JProgressBar();
		progressBarComputer.setForeground(Color.MAGENTA);
		progressBarComputer.setBounds(10, 56, 205, 34);
		add(progressBarComputer);
		progressBarComputer.setMaximum(180);
		progressBarComputer.setMinimum(0);
		
		lblComputer = new JLabel("Nome pokemon avversario");
		lblComputer.setBounds(10, 11, 205, 34);
		add(lblComputer);
		
		progressBarPlayer = new JProgressBar();
		progressBarPlayer.setForeground(Color.GREEN);
		progressBarPlayer.setBounds(378+35+10, 237+35, 205, 34);
		add(progressBarPlayer);
		progressBarPlayer.setMaximum(180);
		progressBarPlayer.setMinimum(0);
		
		lblPlayer = new JLabel("Nome pokemon player");
		lblPlayer.setBounds(378+35+10, 192+35, 205, 34);
		add(lblPlayer);
		
		lblstatoAttacco = new JLabel("New label");
		lblstatoAttacco.setBounds(228+30, 56, 138, 180);
		add(lblstatoAttacco);
	}
	
	 
	@Override
	public void stateChanged(ChangeEvent e) {			//Refresh dei nomi dei pokemon, delle immagini e delle barre
		lblComputer.setText(controller.getPs().getNome());
		lblPlayer.setText(controller.getPp().getNome());
		ImmComputer.setImg(new ImageIcon(controller.getPs().getImageName()).getImage(), 0, 0, 175);
		ImmPlayer.setImg(new ImageIcon(controller.getPp().getImageName()).getImage(), 0, 0, 175);
		progressBarComputer.setValue(controller.getPs().getVita());
		progressBarPlayer.setValue(controller.getPp().getVita());
		
		lblstatoAttacco.setText(controller.getStato());
	}

}
