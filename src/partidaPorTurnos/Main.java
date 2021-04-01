package partidaPorTurnos;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * plantilla base Concurrentes Monitores
 * donde N procesos jugadores juegan a adivinar el nro
	 * y el procesos NombreJuego marca las reglas del juego
	 * -cordinados por 2 monitores ( turnoMonitor, canchaMonitor )
	 * gana quien adivina el nro. en el ejemplo 7
	 * 
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
		int cantJugadores=3;
		
		TurnosMonitor turnosMonitor = new TurnosMonitor(cantJugadores);
		CanchaMonitor canchaMonitor = new CanchaMonitor();
			
		ExecutorService pool = Executors.newFixedThreadPool( 6 );
		
		for(int i=0; i < cantJugadores ;i++) {
			Runnable runnable = new Jugador(i,canchaMonitor,turnosMonitor);
			System.out.println("se creo jugador id:"+i);
			pool.execute(runnable);
		}
		
		Runnable runnable2 = new NombreJuego(turnosMonitor, canchaMonitor);
		pool.execute(runnable2); 
		
		pool.shutdown();   //elimina los hilos despues de que terminen
		//while(!pool.isTerminated() ); //barrera que espera que todos los hilos terminaron
	}

}
