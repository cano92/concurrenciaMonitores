package tresRecursosCompartidos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EscritorioMonitor {
	
	final Lock lock = new ReentrantLock(); 
	final Condition vcEmpleado = lock.newCondition(); 
	final Condition vcCliente = lock.newCondition(); 
	
	boolean datosClienteListos=false;
	String datosCliente;
	String resultadoCliente;
	
	
	//barrera que evita el busy waiting del para empleado
	public String esperarDatos() throws InterruptedException{
		
		lock.lock();
		try {
			//si los datos del cliente aun no fueron entregados espera
			if(!datosClienteListos) {
				//System.out.println("el Empleado espera datos");
				vcEmpleado.await();
			}
		} finally {
			lock.unlock();
		}
		return datosCliente;
	}
	
	public String atencionCliente( String datos ) throws InterruptedException {
		lock.lock();
		try {
			//cliente entrega datos
			datosCliente= datos;
			
			datosClienteListos=true;
			
			vcEmpleado.signal(); //despierta empleado
			//System.out.println("se desperto a empleado");
			vcCliente.await();
			
			//el empleado escribe los resultados mientras el cliente espera en wait
			
			vcEmpleado.signal();
		} finally {
			lock.unlock();
		}	
		return resultadoCliente;
	}
	
	public synchronized void enviarResultados(String result) throws InterruptedException {
		lock.lock();
		try {
			resultadoCliente=result;
			
			//despierta cliente y se espera a que tome sus resultados
			vcCliente.signal();
			vcEmpleado.await();
			
			//reinicia estado para recibir otro cliente
			datosClienteListos=false;
		} finally {
			lock.unlock();
		}	
	}
	
}
