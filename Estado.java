package prRobots;

import java.util.*;

public class Estado {
	private int x;
	private int y;
	private static char[][] malla;
	private static int filFinal;
	private static int colFinal;
	
	public Estado(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean finalp(Estado e) {
		Estado fin = new Estado(filFinal,colFinal);
		boolean iguales = false;
		if(fin.equals(e))
			iguales = true;
		return iguales;
	}
	
	public ArrayList<Estado> sucesores(Estado posRob){
		ArrayList<Estado> lista = new ArrayList<Estado>();
		for(int i = posRob.getX()-1;i < posRob.getX()+1; i++) {
			for(int j = posRob.getY()-1; j < posRob.getY() +1; j++) {
				if( i != j && malla[i][j] != 'O' && i < malla.length && j < malla.length) {
					lista.add(new Estado(i,j));
				}
			}
		}
		return lista;
	}

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

	public static void setMalla(char[][] malla) {
		Estado.malla = malla;
	}

	public static void setFilFinal(int filFinal) {
		Estado.filFinal = filFinal;
	}

	public static void setColFinal(int colFinal) {
		Estado.colFinal = colFinal;
	}

	@Override
	public boolean equals(Object o) {
		boolean res = false;
		if(o instanceof Estado) {
			Estado e = (Estado)o;
			res = x == e.getX() && y == e.getY();
		}
		return res;
	}
	
	@Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
