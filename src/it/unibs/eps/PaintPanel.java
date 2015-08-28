package it.unibs.eps;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;


public class PaintPanel extends JPanel {
	public Image img;
	private int sz;// = 90;
	private int posizioneX;
	private int posizioneY;

	public void setImg(Image img, int x, int y, int size){
		this.img = img;
		this.posizioneX = x;
		this.posizioneY = y;
		this.sz = size;
	}
	
	public void removeImg(Image img, int x, int y){
		this.img = null;
		this.posizioneX = x;
		this.posizioneY = y;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, posizioneX, posizioneY, sz, sz, null);
		repaint();
	}

}// end PaintPanel
