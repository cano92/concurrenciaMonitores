package passingConditionImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import passingCondition.CajeroMonitor;

/**
 * ejemplo de concurrencia Monitores
 *
 * varias personas acceden a un cajero compartdo administrado por un monitor con
 * la tecnica passing de condition, las personas son atendidas por orden de
 * llegada
 */

public class Main {

	//crea una cantidad de hilos ( tareas concurrentes ) indicada Personas
	private void sinPoolDeConexion(int cantidadHilos, CajeroMonitor monitor) {

		Thread personas[] = new Thread[cantidadHilos];

		for (int i = 0; i < personas.length; i++) {
			Runnable runnable = new Persona(i, monitor);  //se crea la tarea concurrente
			personas[i] = new Thread(runnable); // se crea el hilo
			
			personas[i].start(); // se ejecuta el hilo
		}
		
		// en caso de necesitar una barrera para que los procesos concurrentes terminen antes de el 
		//hilo principal(el programa) - generar una barrera con join para los procesos concurrentes
	}

	/**
	 * con un pool de conexiones -hilos reutilizables para la concurrencia
	 * se mejora el tiempo de respuesta en vez de crear demaciados hilos
	 */
	private void conPoolDeConexion( int cantidadHilos, CajeroMonitor monitor ) {
	
		//se crea pool con la cantidad de hilos a reutilizar
		ExecutorService pool = Executors.newFixedThreadPool( this.getCantidadNucleos() );
		
		//crea la cantidad de hilos indicada con el monitor para la sincronizacion
		this.ejecutarHilosPersona(pool, cantidadHilos, monitor);
		
		//detiene el hilo principal hasta que todos los hilosConcurrentes finalizen
		this.barreraHilos(pool); 
		
	}
	
	//crea la cantidad de hilos indicada con el monitor para la sincronizacion y los ejecuto sobre el pool
	private void ejecutarHilosPersona( ExecutorService pool, int cantidadHilos, CajeroMonitor monitor) {
		for(int i =0; i< cantidadHilos ;i++) {
			
			Runnable runnable = new Persona(i, monitor); //se crea el tarea concurrente
			
			pool.execute(runnable); // ejecuta la tarea concurrente en un hilo del pool
		}
	}

	//impide que el hilo siga hasta que finalizen los hilos concurrentes
	private void barreraHilos( ExecutorService pool ) {
		pool.shutdown();
		while(!pool.isTerminated() );
	}
	
	//realiza una consulta a la maquina virtual java y recupera la cantidad de hilos del procesador 
	private int getCantidadNucleos() {
		Runtime runtime = Runtime.getRuntime();
		//System.out.println("hilos de mi procesador: "+runtime.availableProcessors() );
		return runtime.availableProcessors();
	}

	public static void main(String[] args) {
		Main pruebaHilos = new Main();
		
		//con pool de conexion --10 procesos Personas concurrentes, monitorDe sincronizacion
		//pruebaHilos.conPoolDeConexion(10, CajeroMonitorSimple.getInstance() );
		pruebaHilos.conPoolDeConexion(10, CajeroExtra.getInstance() );
		
		System.out.println(">>> finaliza el hilo principal");
	}

}
