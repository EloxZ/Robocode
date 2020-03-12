package prueba;

import static robocode.util.Utils.normalRelativeAngleDegrees;


import java.awt.Color;

import robocode.Robot;

/**
 * 
 */

/**
 * @date 2018-03-22
 * 
 * Plantilla para la definición de un robot en Robocode
 *
 */
public class Bot3 extends Robot {

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
		//  2. BUSCAR LA SOLUCIÓN CON UN ALGORITMO DE BÚSQUEDA
		//  3. EJECUTAR LA SOLUCIÓN ENCONTRADA
			
	}
	
	
}