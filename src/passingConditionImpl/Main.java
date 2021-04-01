package passingConditionImpl;

import java.time.LocalDate;
import java.util.LinkedList;
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
	private LinkedList<Persona> listPersonas = new LinkedList();
	
	public void generarUsuarios(CajeroMonitor monitor) {
		//se genera las personas  (monitor, id, nombre, fechaNac )
		listPersonas.add( new Persona(monitor,0,"pepe", LocalDate.of(1992, 3, 12)) );
		listPersonas.add( new Persona(monitor,1,"juan", LocalDate.of(1992, 3, 1)) );
		listPersonas.add( new Persona(monitor,2,"jose", LocalDate.of(1959, 3, 12)) ); //mayor
		listPersonas.add( new Persona(monitor,3,"ema", LocalDate.of(2000, 3, 12)) );
		listPersonas.add( new Persona(monitor,4,"lore", LocalDate.of(1998, 3, 12)) );
		listPersonas.add( new Persona(monitor,5,"damian", LocalDate.of(1999, 3, 12)) );
		listPersonas.add( new Persona(monitor,6,"ana", LocalDate.of(1992, 3, 12)) );
		listPersonas.add( new Persona(monitor,7,"maria", LocalDate.of(1955, 3, 12)) ); //mayor
		listPersonas.add( new Persona(monitor,8,"eva", LocalDate.of(2003, 3, 12)) );
		listPersonas.add( new Persona(monitor,9,"mario", LocalDate.of(1950, 3, 12)) ); //mayor
	}
	
	/**
	 * con un pool de conexiones -hilos reutilizables para la concurrencia
	 * se mejora el tiempo de respuesta en vez de crear demaciados hilos
	 */
	private void conPoolDeConexion( CajeroMonitor monitor ) {
		this.generarUsuarios(monitor);
		
		//se crea pool con la cantidad de hilos que indica la VM Java
		//ExecutorService pool = Executors.newFixedThreadPool( this.getCantidadNucleos() );
		ExecutorService pool = Executors.newFixedThreadPool( 5 );
		
		this.listPersonas.forEach(person->pool.execute(person));
		
		this.barreraHilos(pool); 
	}

	//impide que el hilo principal siga hasta que finalizen los hilos concurrentes
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
	
	//crea una cantidad de hilos ( tareas concurrentes ) indicada Personas
	private void sinPoolDeConexion(int cantidadHilos, CajeroMonitor monitor) {

		Thread personas[] = new Thread[cantidadHilos];
		for (int i = 0; i < personas.length; i++) {
			Runnable runnable = new Persona(i, monitor);  //se crea la tarea concurrente
			personas[i] = new Thread(runnable); // se crea el hilo
			
			personas[i].start(); // se ejecuta el hilo
		}
	}
	
	public static void main(String[] args) {
		Main pruebaHilos = new Main();
	
		//CajeroMonitor cajeroMonitor = CajeroExtra.getInstance();
		//CajeroMonitor cajeroMonitor = CajeroMonitorSimple.getInstance();
		CajeroMonitor cajeroMonitor = CajeroMonitorPrioridad.getInstance(10);
		
		
		pruebaHilos.conPoolDeConexion(cajeroMonitor);
		
		System.out.println(">>> finaliza el hilo principal");
		//cajeroMonitor.ordenLlegadaFila();
	}

}
