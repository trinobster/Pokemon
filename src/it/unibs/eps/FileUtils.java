package it.unibs.eps;

import java.io.*;
import java.util.ArrayList;

public class FileUtils {
	
	private StringBuilder sb;

	public ArrayList<String> readResource(String fname, int indiceLinea) throws IOException{
	    
		/**
		 * Questo metodo, dati in input la stringa che indica il file e
		 * la riga del file che si vuole leggere, ritorna un arraylist
		 * di stringhe, in cui in ogni posizione è contenuto un parametro
		 * che si trovava nella riga
		 */
		
		BufferedReader in = null;
	    int counter = 0;
	    char[] storedline;
	    char separator = ';';
	    ArrayList<String> list = new ArrayList<String>();
	    sb = new StringBuilder();
	    
	    try {
	        in = new BufferedReader(new FileReader(fname));
	        while(true){
	        	counter++;
	            String line = in.readLine();
	            
	            if(line == null)
	            	break;
	            if(counter == indiceLinea){
	            	storedline = line.toCharArray();
	            	//Questa riga diventa null alla fine!!!
	            	//Gestire questo
	            	for(int i = 0; i < storedline.length; i++){
	            		if(storedline[i] == separator){
	            			list.add(sb.toString());
	            			sb = sb.delete(0, sb.length());
	            		}
	            		else{
	            			sb.append(storedline[i]);
	            		}
	            	}
	                break;
	            }
	            //process line
	            //System.out.println(line);
	        }
	    } finally {
	        in.close();
	    }
	    return list;
	}
}