package it.unibs.eps;

import javax.swing.event.ChangeEvent;

public class FightChangeEvent extends ChangeEvent {

protected boolean enableMossa;
protected boolean enableAttacco;
	
	public FightChangeEvent(Object source, boolean enableMossa, boolean enableAttacco) {
		super(source);
		this.enableMossa = enableMossa;
		this.enableAttacco = enableAttacco;
	}

	public boolean isBtnEnabled() {
		return enableMossa;
	}
	
	public boolean possoAttaccare(){
		return enableAttacco;
	}
}
