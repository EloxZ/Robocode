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
 * Plantilla para la definici�n de un robot en Robocode
 *
 */
public class Bot3 extends Robot {

	//The main method in every robot
	public void run() {
		

		System.out.println("Iniciando ejecuci�n del robot");
		
		// Nuestro robot ser� rojo
		setAllColors(Color.red);

		//DATOS QUE DEBEN COINCIDIR CON LOS DEL PROGRAMA main DE LA CLASE RouteFinder
		long semilla = 0;
		int nFil = 16;
		int nCol = 12;
		int nObst = 40;
		int tamCelda = 50;
		
		//Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));
		
		//A continuaci�n nuestro robot girar� un poco sobre s� mismo		
		int k = 0;
		while(k < 20){
			turnRight(90);
			k++;
		}
		
		
		// AQU� DEBE:
		//  1. GENERARSE EL PROBLEMA DE B�SQUEDA
		//  2. BUSCAR LA SOLUCI�N CON UN ALGORITMO DE B�SQUEDA
		//  3. EJECUTAR LA SOLUCI�N ENCONTRADA
			
	}
	
	
}