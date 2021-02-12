package cocurrentes.tresrecursosCompartidos;
	/*
	 * ejemplo 3 empleados recibiendo n -(100) clientes en un banco
	 * los clientes llegan y forma en una fila hasta que algun empleado se libera
	 * cuando un empleado se libera solicita que pase el siguiente a ser atendido
	 * los clientes reciben el nro del escritorio (caja a la que deben acudir 
	 * -- se debe maximizar la concurrencia)*/

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String[] args) {
		System.out.println("concurrencia 3recursos compartidos");
		
		int cantEmpleados=3;
		int cantClientes=10;
		
		//se crea el monitor banco con tres empleados disponibles
		BancoMonitor banco = BancoMonitor.getInstance(cantEmpleados);
		
		//agregar la misma cantidad de empleados
		ArrayList<EscritorioMonitor> escritorios=new ArrayList();
		for(int i=0; i < cantEmpleados ;i++) {
			escritorios.add( new EscritorioMonitor() );
		}
		
		//se crea pool con la cantidad de hilos de nuestro procesador
		Runtime runtime = Runtime.getRuntime();
		ExecutorService pool = Executors.newFixedThreadPool( 15 );
		//ExecutorService pool = Executors.newFixedThreadPool( runtime.availableProcessors() );
		
		for(int i = 0; i < cantEmpleados ;i++) {
			Runnable runnable = new Empleado(i, banco, escritorios); 
			System.out.println("se creo empleado id:"+i);
			pool.execute(runnable);
		}
		
		for(int i=0;i < cantClientes;i++) {
			//se crean las tareas concurrentes Clientes
			Runnable runnable = new Persona(i, banco, escritorios); 
			System.out.println("se creo Cliente id:"+i);
			pool.execute(runnable);
		}
		

		
	}
}
