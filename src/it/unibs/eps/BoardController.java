package it.unibs.eps;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class BoardController implements Serializable {
	/*Controller che trasforma coordinate*/
	int divisore = 16;

	/*InnerClass per gestione matrice 32x32*/
	public class BoardPosition{
		/*Variabili locali*/
		private int x;
		private int y;
		/*Costruttore*/
		public BoardPosition(int x , int y)
		{
			/*Controlli nel costruttore*/
			if(x < 0 )
				x = 0;
			else if (x > (divisore-1))
				x = (divisore-1);
			if(y < 0)
				y = 0;
			else if (y > (divisore-1))
				y = (divisore-1);
			this.x = x;
			this.y = y;
		}
		/*Getter & Setter delle coordinate*/
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
	};



	/*Variabili Locali (BoardController class)*/
	PaintArea area;
	public BoardController(PaintArea area)
	{
		this.area = area;
	}
	
	/*Punto in coordinate spaziali*/
	public Point2D BoardToPoint(BoardPosition pos)
	{
		int size = (area.getWidth() > area.getHeight() ? area.getHeight() : area.getWidth()) /divisore ; //determino dimensione area 
		return new Point(pos.x*size,pos.y*size);
	}
	/*Punto in coordinate della matrice*/
	public BoardPosition PointToBoard(Point2D point)
	{
		int size = (area.getWidth() > area.getHeight() ? area.getHeight() : area.getWidth()) /divisore ; //determino dimensione area
		return new BoardPosition((int)(point.getX() / size),(int)(point.getY() / size));
	}
	
}