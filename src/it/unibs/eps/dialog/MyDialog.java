package it.unibs.eps.dialog;

public class MyDialog extends javax.swing.JDialog {
	
	protected boolean result;
	
	public boolean showDialog(){
		setVisible(true);
		result = true;
		return result;
	}
	
	public void perform(){
		setVisible(false);
		result = false;
		dispose(); // rilascia la parte grafica
	}
}
