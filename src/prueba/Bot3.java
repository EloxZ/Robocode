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
	    /* Calculate the difference bettwen the current position and the target position. */
	    x = x - getX();
	    y = y - getY();

	    /* Calculate the angle relative to the current heading. */
	    double goAngle = Utils.normalRelativeAngle(Math.atan2(x, y) - getHeadingRadians());

	    /*
	     * Apply a tangent to the turn this is a cheap way of achieving back to front turn angle as tangents period is PI.
	     * The output is very close to doing it correctly under most inputs. Applying the arctan will reverse the function
	     * back into a normal value, correcting the value. The arctan is not needed if code size is required, the error from
	     * tangent evening out over multiple turns.
	     */
	    if (this.getVelocity() < 0.1) turnRightRadians(Math.atan(Math.tan(goAngle)));

	    /*
	     * The cosine call reduces the amount moved more the more perpendicular it is to the desired angle of travel. The
	     * hypot is a quick way of calculating the distance to move as it calculates the length of the given coordinates
	     * from 0.
	     */
	    ahead(Math.cos(goAngle) * Math.hypot(x, y));
	}
	

	private Stack<Estado> busquedaAnchura(Problema p, int tamCelda) {
		Stack<Estado> camino = new Stack<Estado>();

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
		return camino;
	}
	
	public class ComparadorEstado implements Comparator<Info> {

	    public int compare(Info arg0, Info arg1) {
	    	return arg0.getEstado().coste(arg0.getPadre().getEstado()) - arg1.getEstado().coste(arg1.getPadre().getEstado());
	    }
	    
	}
	
	public Stack<Estado> busquedaVoraz(Problema p, int tamCelda) {
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
		return camino;
	}


	//The main method in every robot
	public void run() {


		System.out.println("Iniciando ejecucion del robot");

		// Nuestro robot serï¿½ rojo
		setAllColors(Color.red);

		//DATOS QUE DEBEN COINCIDIR CON LOS DEL PROGRAMA main DE LA CLASE RouteFinder
		long semilla = 69;
		int nFil = 16;
		int nCol = 12;
		int nObst = 60;
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

		//  2. BUSCAR LA SOLUCIï¿½N CON UN ALGORITMO DE Bï¿½SQUEDA
		Stack<Estado> camino = busquedaAnchura(p, tamCelda);
		//Stack<Estado> camino = busquedaVoraz(p, tamCelda);
		//  3. EJECUTAR LA SOLUCIï¿½N ENCONTRADA
		Estado estado = null;
		while (true) {
			if (!camino.isEmpty() && this.getVelocity() == 0) {
				estado = camino.pop();
				go((double) (estado.getX() + 1) * tamCelda - tamCelda / 2,(double) (estado.getY() + 1) * tamCelda - tamCelda / 2);
			} else if (estado != null) {
				go((double) (estado.getX() + 1) * tamCelda - tamCelda / 2,(double) (estado.getY() + 1) * tamCelda - tamCelda / 2);
			} 
		}

	}


}
