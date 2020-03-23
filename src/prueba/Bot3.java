package prueba;
import busqueda.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;


import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;

import robocode.AdvancedRobot;
import robocode.Robot;
import robocode.util.*;


/**
 * 
 */

/**
 * @date 2018-03-22
 * 
 * Plantilla para la definición de un robot en Robocode
 *
 */
public class Bot3 extends AdvancedRobot {
	
	private void goTo(double x, double y) {
		/* Transform our coordinates into a vector */
		x -= getX();
		y -= getY();
	 
		/* Calculate the angle to the target position */
		double angleToTarget = Math.atan2(x, y);
	 
		/* Calculate the turn required get there */
		double targetAngle = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());
	 
		/* 
		 * The Java Hypot method is a quick way of getting the length
		 * of a vector. Which in this case is also the distance between
		 * our robot and the target location.
		 */
		double distance = Math.hypot(x, y);
	 
		/* This is a simple method of performing set front as back */
		double turnAngle = Math.atan(Math.tan(targetAngle));
		setTurnRightRadians(turnAngle);
		if(targetAngle == turnAngle) {
			setAhead(distance);
		} else {
			setBack(distance);
		}
	}

	private void busquedaAnchura(Problema p, int tamCelda) {
		LinkedList<Estado> camino = new LinkedList<Estado>();
		
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
				camino.add(nodo.getEstado());
				nodo = nodo.getPadre();
			} while (nodo != null);
			for (Estado e : camino) {
				goTo((double)  (e.getX()+1) * tamCelda - (tamCelda / 2), (double)  (e.getY()+1) * tamCelda - (tamCelda / 2));
			}
		} 
	}

	//The main method in every robot
	public void run() {
		

		System.out.println("Iniciando ejecución del robot");
		
		// Nuestro robot será rojo
		setAllColors(Color.red);

		//DATOS QUE DEBEN COINCIDIR CON LOS DEL PROGRAMA main DE LA CLASE RouteFinder
		long semilla = 0;
		int nFil = 16;
		int nCol = 12;
		int nObst = 40;
		int tamCelda = 50;
		
		//Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));
		
		//A continuación nuestro robot girará un poco sobre sí mismo		
		int k = 0;
		while(k < 20){
			turnRight(90);
			k++;
		}
		
		// AQUÍ DEBE:
		//  1. GENERARSE EL PROBLEMA DE BÚSQUEDA
		Problema p = new Problema(semilla, nFil, nCol, nObst);
		Estado.setProblema(p);
		
		//  2. BUSCAR LA SOLUCIÓN CON UN ALGORITMO DE BÚSQUEDA
		busquedaAnchura(p, tamCelda);
		//  3. EJECUTAR LA SOLUCIÓN ENCONTRADA
			
	}
	
	
}