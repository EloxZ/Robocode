package busqueda;
import java.util.ArrayList;

import prueba.Problema;

public class PruebaEstado {

	public static void main(String[] args) {
		long semilla = 0;
		int nFil = 16;
		int nCol = 12;
		int nObst = 40;
		int tamCelda = 50;
		Problema p = new Problema(semilla, nFil, nCol, nObst);
	    char[][] mapa = p.getMatriz();
		for (int fi = 0; fi<nFil; fi++) {
			for (int co = 0; co<nCol; co++) {
				System.out.print(mapa[fi][co] + " ");
			}
			System.out.println();
		}
		Estado.setProblema(p);
		Estado nodo = new Estado(15,3);
		
		ArrayList<Estado> sucesores = nodo.sucesores();
		for (Estado x : sucesores) {
			System.out.println(x.toString());
		}
		System.out.println("Terminado");

	}

}
