package tiempoSincronizado;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ejercicio ejemplo de sincronizacion 
 * 22 procesos jugador -llegan y se sincromizan para jugar un partido de 30min
 * y luego finalizan
 * por cuestion de ejemplo solo seran 5seg de juego
 * */

public class Main {

	public static void main(String[] args) {
		System.out.println("concurrencia partido futbol con cronometro");
		
		int cantJugadores=22;
		CanchaMonitor cancha=new CanchaMonitor();
		
		//se crea pool con la cantidad de hilos de nuestro procesador
		Runtime runtime = Runtime.getRuntime();
		ExecutorService pool = Executors.newFixedThreadPool(23);
		//ExecutorService pool = Executors.newFixedThreadPool( runtime.availableProcessors() );
		
		Runnable runnable = new TiempoPartido(cancha); 
		pool.execute(runnable);
		
		for(int i=0; i < cantJugadores  ;i++) {
			Runnable runnable2 = new Persona(i,cancha); 
			pool.execute(runnable2);
		}
		
		System.out.println("finalizo main");
		
	}

}
