package busqueda;
import java.util.ArrayList;

import prueba.Problema;

public class Estado {
	private static char[][] mapa;
	private static int finalX, finalY;
	private int x, y;
	private int coste;
	
	public Estado(int fi, int co) {
		x = fi;
		y = co;
		coste = 0;
	}
	
	public static void setProblema(Problema p) {
		mapa = p.getMatriz();
		finalX = p.getfX();
		finalY = p.getfY();
	}
	
	public boolean finalp() {
		return x == finalX && y == finalY;
	}
	
	public ArrayList<Estado> sucesores() {
		ArrayList<Estado> sucesores = new ArrayList<Estado>();
		ArrayList<Estado> bloqueados = new ArrayList<Estado>();
		for (int i = x-1; i<x+2; i++) {
			for (int j = y-1; j<y+2; j++) {
				if (j>= 0 && i >= 0 && j<mapa[0].length && i<mapa.length) {
					if (mapa[i][j] != 'O') {
						if (!(i == x && j == y) && !bloqueados.contains(new Estado(i,j))) sucesores.add(new Estado(i,j));
					} else {
						if (i == x-1 && j == y) {
							sucesores.remove(new Estado(x-1,y-1));
							bloqueados.add(new Estado(x-1,y+1));
						}
						if (i == x && j == y-1) {
							sucesores.remove(new Estado(x-1,y-1));
							bloqueados.add(new Estado(x+1,y-1));
						}
						if (i == x && j == y+1) {
							sucesores.remove(new Estado(x-1,y+1));
							bloqueados.add(new Estado(x+1,y+1));
						}
						if (i == x+1 && j == y) {
							sucesores.remove(new Estado(x+1,y-1));
							bloqueados.add(new Estado(x+1,y+1));
						}
					}
				}
			}
		}

		return sucesores;
	}
	
	public int calculoHeuristico() {
		int min = Math.min(Math.abs(x-finalX), Math.abs(y-finalY));
		int max = Math.max(Math.abs(x-finalX), Math.abs(y-finalY));
        int res = (142 * min) + 100 * (max-min);
        return res;
    }
	
	public int coste(Estado e2) //suponiendo siempre que ningun estado sea barrera ni que no sean adyacentes
	{
		int c = 1000;
		if (e2 != null) {
			if (x != e2.x && y != e2.y) {
				c = 141;
			} else {
				c = 100;
			}
		}
		return c;
	}
	
	public int getCoste() {
		return coste;
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

	public boolean equals(Object o) {
		return (o instanceof Estado) && x == ((Estado)o).x && y == ((Estado)o).y;
	}
	
	public String toString() {
		return "["+x+","+y+"]";
	}
	
	public int hashCode() {
		return finalX + finalY + x + y;
	}
	
	public void addCoste(int s) {
		coste += s;
	}

	public int f() {
		return coste + calculoHeuristico();
	}
}
