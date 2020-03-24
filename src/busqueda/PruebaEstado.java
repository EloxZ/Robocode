package busqueda;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

import prueba.Problema;
import prueba.Bot3.ComparadorEstado;

public class PruebaEstado {
	
	public static void main(String[] args) {
		class ComparadorEstado implements Comparator<Info> {

		    public int compare(Info arg0, Info arg1) {
		    	return arg0.getEstado().coste(arg0.getPadre().getEstado()) - arg1.getEstado().coste(arg1.getPadre().getEstado());
		    }
		    
		}
		long semilla = 23;
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
		Estado nodo1 = new Estado(15,3);
		
		ArrayList<Estado> sucesores = nodo1.sucesores();
		for (Estado x : sucesores) {
			System.out.println(x.toString());
		}
		System.out.println("Terminado");
		
		Stack<Estado> camino = new Stack<Estado>();

		HashMap<Estado,Info> tree = new HashMap<Estado,Info>();
		PriorityQueue<Info> abiertos = new PriorityQueue<>(new ComparadorEstado());
		Info s = new Info(null,new Estado(p.getiX(),p.getiY()));
		tree.put(s.getEstado(),s);
		abiertos.add(s);
		Info nodo = null;
		boolean encontrado = false;
		while (!abiertos.isEmpty() && !encontrado) {
			nodo = abiertos.remove();
	
			if (nodo.getEstado().finalp()) {
				encontrado = true;
			} else {
				ArrayList<Estado> ramificar = nodo.getEstado().sucesores();
				if (!ramificar.isEmpty()) {
					for (Estado x : ramificar) {
						if (!tree.containsKey(x)) {
							Info newNodo = new Info(nodo,x);
							tree.put(x,newNodo);
							abiertos.add(newNodo);
						}
					}
				}
			}
		}

		if (encontrado) {
			do {
				camino.push(nodo.getEstado());
				nodo = nodo.getPadre();
			} while (nodo != null);
			camino.pop();
		}
	    while (!camino.isEmpty()) {
	    	System.out.println(camino.pop().toString());
	    }

	}


}
