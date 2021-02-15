package partidaPorTurnos;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tresRecursosCompartidos.Empleado;

/**
 * tecnica passing the condition  -Monitor turnos
 * 	para determinar turnos entre procesos jugadores
 * 	barrera -para esperar a los jugadores
 * 
 * tecnica productores consumidores -Monitor cancha
 * 	para indicar 1 productor(jugador), 1 consumidor (juego) de jugadas filtrada por turnos
 * 	tamaño de buffer 1
 * 
 * @author berceo
 */


public class Main {

	public static void main(String[] args) {
		int cantJugadores=2;
		
		TurnosMonitor turnosMonitor = new TurnosMonitor(cantJugadores);
		CanchaMonitor canchaMonitor = new CanchaMonitor();
			
		ExecutorService pool = Executors.newFixedThreadPool( 5 );
		
		for(int i=0; i < cantJugadores ;i++) {
			Runnable runnable = new Jugador(i,canchaMonitor,turnosMonitor);
			System.out.println("se creo jugador id:"+i);
			pool.execute(runnable);
		}
		
		Runnable runnable2 = new NombreJuego(turnosMonitor, canchaMonitor);
		System.out.println("se creo el juego");
		pool.execute(runnable2);
		
		pool.shutdown();   //elimina los hilos despues de que terminen
		//while(!pool.isTerminated() ); //barrera que espera que todos los hilos terminaron
	}

}
