package partidaPorTurnosTimer;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	/**
	 * identica a Patida por turnos
	 * donde N procesos jugadores juegan a adivinar el nro
	 * y el procesos NombreJuego marca las reglas del juego
	 * -cordinados por 2 monitores ( turnoMonitor, canchaMonitor )
	 * gana quien adivina el nro. en el ejemplo 7
	 * 
	 * agregar un nuevo proceso TImer
	 * 	para limitar el tiempo maximo para realizar una jugada
	 * 	de lo contrario pierde el turno
	 * */
	
	
	public static void main(String[] args) {
		int cantJugadores = 2;
		
		TurnosMonitor turnosMonitor = new TurnosMonitor(cantJugadores);
		CanchaMonitor canchaMonitor = new CanchaMonitor();
		
		ExecutorService pool = Executors.newFixedThreadPool( 8 );
		
		// proceso timer y proceso input
		Runnable runnable2 = new TimerTurno(1, turnosMonitor, canchaMonitor);
		pool.execute(runnable2);
		
		Runnable runnable3 = new InputJugador( canchaMonitor);
		pool.execute(runnable3);
		
		for(int i=0; i < cantJugadores ;i++) {
			Runnable runnable = new Jugador(i,canchaMonitor,turnosMonitor);
			System.out.println("se creo jugador id:"+i);
			pool.execute(runnable);
		}
		
		Runnable runnable = new NombreJuego(turnosMonitor, canchaMonitor);
		pool.execute(runnable); 
		
	
		
		pool.shutdown();   //elimina los hilos despues de que terminen
		
	}

}
