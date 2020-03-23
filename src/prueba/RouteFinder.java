package prueba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import busqueda.Estado;
import busqueda.Info;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSetup;
import robocode.control.RobotSpecification;



/**
 *
 * @date   2018-03-22
 *
 * Plantilla para la práctica de algoritmos de búsqueda con Robocode (G. Ing. Comp.)
 *
 *
 */

public class RouteFinder {

	public static void main(String[] args) {



		//Creamos un mapa con los datos especificados
		// Create the battlefield

		int numPixelFila= 800;
		int numPixelCol=600;
		int tamCelda = 50;       //celdas de 50 x 50

		//número de obstáculos
		////DEBERÁ COINCIDIR CON EL VALOR PROPORCIONADO AL ROBOT
		int numObstaculos = 40;

		//semilla para el generador de números aleatorios
		//DEBERÁ COINCIDIR CON EL VALOR PROPORCIONADO AL ROBOT
		long semilla = 5;

		//tamaño del mapa de obstáculos
		//DEBERÁ COINCIDIR CON EL VALOR PROPORCIONADO AL ROBOT
		int nFil = numPixelFila / tamCelda;
		int nCol = numPixelCol  / tamCelda;



		//AQUÍ SE DEBERÍA DE GENERAR EL MAPA DE OBSTÁCULOS Y LAS POSICIONES INICIAL Y FINAL DEL ROBOT
		//
		//
 		//  ...
		//
		//
		Problema problema = new Problema(semilla, nFil, nCol, numObstaculos);
	    
	    char[][] mapa = problema.getMatriz();
		for (int co = nCol-1; co>=0; co--) {
			for  (int fi = 0; fi<nFil; fi++) {
				System.out.print(mapa[fi][co] + " ");
			}
			System.out.println();
		}
	    int inicialX = problema.getiX();
	    int inicialY = problema.getiY();
	    int finalX = problema.getfX();
	    int finalY = problema.getfY();





		// Crear el RobocodeEngine desde una instalación en C:/Robocode
		RobocodeEngine engine =
				new RobocodeEngine(new java.io.File("C:/robocode"));
		// Mostrar el simulador de Robocode
		engine.setVisible(true);

	

		BattlefieldSpecification battlefield =
				new BattlefieldSpecification(numPixelFila, numPixelCol);
		// Establecer parámetros de la batalla
		int numberOfRounds = 5;
		long inactivityTime = 10000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = true;



		// En modelRobots recogemos la especificación de los robots que utilizaremos en la simulación.
		// En este caso serán un robot sittingDuck y nuestro propio robot. La referencia a nuestro robot
		// debe ser relativa al proyecto que pusimos en Options>Preferences>DevelopmentOptions en Robocode,
		// indicando el nómbre del paquete (si lo hay) y del robot.  En nuestro caso suponemos como nombre
		// prueba.Bot3*
		// Al nombre de los robots definidos por el usuario siempre hay que añadirle el carácter * al final.
		RobotSpecification[] modelRobots =
				engine.getLocalRepository
				("sample.SittingDuck,prueba.Bot3*");

		// Incluiremos un robot sittingDuck por obstáculo, más nuestro propio robot.
		RobotSpecification[] existingRobots =
				new RobotSpecification[numObstaculos+1];
		RobotSetup[] robotSetups = new RobotSetup[numObstaculos+1];

		/*
	     * Creamos primero nuestro propio robot y lo colocamos en la posición inicial del problema,
	     * que deberá estar libre de obstáculo.
		 */

		int indice = 0;

		existingRobots[indice]=modelRobots[1];


		robotSetups[indice]=new RobotSetup( (double)  (inicialX+1) * tamCelda - (tamCelda / 2)      ,        //AQUÍ DEBE FIGURAR LA FILA EN PIXELS CORRESPONDIENTE A LA POSICIÓN INICIAL DEL ROBOT
				                               (double)   (inicialY+1) * tamCelda - (tamCelda / 2),        //AQUÍ DEBE FIGURAR LA COLUMNA EN PIXELS CORRESPONDIENTE A LA POSICIÓN INICIAL DEL ROBOT
										   0.0);              //orientación inicial


		/*
	     * Creamos un robot sittingDuck por cada obstáculo, y lo colocamos en el centro de la
	     * celda correspondiente.
		 */


		indice++;
		for (int f = 0; f < nFil; f++){
			for (int c = 0; c < nCol; c++){
				if (mapa[f][c] == 'O'){ //SI HAY OBSTÁCULO EN ESA POSICIÓN DE LA MALLA

					existingRobots[indice]=modelRobots[0];   //sittingDuck


					robotSetups[indice]=  new RobotSetup( (double) (f+1) * tamCelda - (tamCelda / 2) ,   //AQUÍ DEBE FIGURAR LA FILA EN PIXELS CORRESPONDIENTE AL CENTRO DE LA CELDA DONDE HAY OBSTÁCULO
							(double) (c+1) * tamCelda  - (tamCelda / 2)  ,   //AQUÍ DEBE FIGURAR LA FILA EN PIXELS CORRESPONDIENTE AL CENTRO DE LA COLUMNA DONDE HAY OBSTÁCULO
							                             0.0);    //orientación
					indice++;

				}//if mapa
			}//for c
		}//for f

		System.out.println("Generados " + (indice -1) + " sitting ducks.");




		/*
		 * Crear y desarrollar la batalla con los robots antes definidos
		 */
		BattleSpecification battleSpec =
				new BattleSpecification(battlefield,
						numberOfRounds,
						inactivityTime,
						gunCoolingRate,
						sentryBorderSize,
						hideEnemyNames,
						existingRobots,
						robotSetups);

		// Ejecutar la simulación el tiempo especificado
		engine.runBattle(battleSpec, true);
		// Cerrar la simulación
		engine.close();
		// Asegurarse de que la MV de Java se cierra adecuadamente.
		System.exit(0);


	}

}
