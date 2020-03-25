package prueba;
import busqueda.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;


import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Comparator;

import robocode.AdvancedRobot;
import robocode.Robot;
import robocode.util.*;


/**
 *
 */

/**
 * @date 2018-03-22
 *
 * Plantilla para la definiciï¿½n de un robot en Robocode
 *
 */
public class Bot3 extends AdvancedRobot {

	private void go(double x, double y) {
	    x = x - getX();
	    y = y - getY();

	    double goAngle = Utils.normalRelativeAngle(Math.atan2(x, y) - getHeadingRadians());

	    if (this.getVelocity() < 0.1) turnRightRadians(Math.atan(Math.tan(goAngle)));

	    ahead(Math.cos(goAngle) * Math.hypot(x, y));
	}
	

	private Stack<Estado> busquedaAnchura(Problema p, int tamCelda) {
		Stack<Estado> camino = new Stack<Estado>();
		int k = 0;
		HashMap<Estado,Info> tree = new HashMap<Estado,Info>();
		LinkedList<Info> abiertos = new LinkedList<>();
		Info s = new Info(null,new Estado(p.getiX(),p.getiY()));
		tree.put(s.getEstado(),s);
		abiertos.add(s);
		Info nodo = null;
		boolean encontrado = false;
		while (!abiertos.isEmpty() && !encontrado) {
			nodo = abiertos.getFirst();
			abiertos.removeFirst();
			if (nodo.getEstado().finalp()) {
				encontrado = true;
			} else {
				ArrayList<Estado> ramificar = nodo.getEstado().sucesores();
				k++;
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
		System.out.println("Busqueda por anchura: " + k + " nodos seleccionados para expansion.");
		return camino;
	}
	
	public class ComparadorEstado implements Comparator<Info> {

	    public int compare(Info arg0, Info arg1) {
	    	return arg0.getEstado().coste(arg0.getPadre().getEstado()) - arg1.getEstado().coste(arg1.getPadre().getEstado());
	    }
	    
	}
	
	public Stack<Estado> busquedaVoraz(Problema p, int tamCelda) {
		Stack<Estado> camino = new Stack<Estado>();
		int k = 0;

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
				k++;
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
		System.out.println("Busqueda voraz: " + k + " nodos seleccionados para expansion.");
		return camino;
	}


	//The main method in every robot
	public void run() {


		System.out.println("Iniciando ejecucion del robot");

		// Nuestro robot serï¿½ rojo
		setAllColors(Color.red);

		//DATOS QUE DEBEN COINCIDIR CON LOS DEL PROGRAMA main DE LA CLASE RouteFinder
		long semilla = 323;
		int nFil = 16;
		int nCol = 12;
		int nObst = 40;
		int tamCelda = 50;

		//Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));
		
		//A continuación nuestro robot girará un poco sobre sí mismo		
		int k = 0;
		while(k < 5){
			turnRight(90);
			k++;
		}


		// AQUï¿½ DEBE:
		//  1. GENERARSE EL PROBLEMA DE Bï¿½SQUEDA
		Problema p = new Problema(semilla, nFil, nCol, nObst);
		Estado.setProblema(p);
		
		char[][] mapa = p.getMatriz();
		
		System.out.println("/------------------------------/");
		for (int co = nCol-1; co>=0; co--) {
			for  (int fi = 0; fi<nFil; fi++) {
				System.out.print(mapa[fi][co] + " ");
			}
			System.out.println();
		}
		System.out.println("/------------------------------/");

		//  2. BUSCAR LA SOLUCIï¿½N CON UN ALGORITMO DE Bï¿½SQUEDA
		Stack<Estado> camino = busquedaAnchura(p, tamCelda);
		//Stack<Estado> camino = busquedaVoraz(p, tamCelda);
		//  3. EJECUTAR LA SOLUCIï¿½N ENCONTRADA
		Estado estado = null;
		boolean running = true;
		while (running) {
			if (!camino.isEmpty() && this.getVelocity() == 0) {
				estado = camino.pop();
				System.out.println(estado.toString());
				go((double) (estado.getX() + 1) * tamCelda - tamCelda / 2,(double) (estado.getY() + 1) * tamCelda - tamCelda / 2);
			} else if (estado != null) {
				go((double) (estado.getX() + 1) * tamCelda - tamCelda / 2,(double) (estado.getY() + 1) * tamCelda - tamCelda / 2);
				if (camino.isEmpty() && this.getVelocity() == 0) {System.out.println("¡Completado!"); running = false;}
			} 
		}
		while (true) {
			doNothing();
		}

	}


}
