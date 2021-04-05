package passingConditionImpl;

import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import passingCondition.CajeroMonitor;

public class CajeroMonitorEdad implements CajeroMonitor{
	private static CajeroMonitorEdad instance = null;

	final Lock lock = new ReentrantLock();
	private ArrayList<Condition> listVCPersonas=new ArrayList<Condition>();

	// es la fila en que los procesos van a despertar. el orden indicado por el lambda
	private TreeSet<Persona> filaPersonasId= new TreeSet<Persona>( (x,y)->y.getEdad()-x.getEdad() );
	private boolean cajeroLibre = true;
	private int esperando = 0;

	// methods class --singleton pattern construct
	public synchronized static CajeroMonitorEdad getInstance(int cantClientes) {
		if (instance == null) {
			instance = new CajeroMonitorEdad();

			// se agregan las Variables Condition para demorar los procesos clientes
			for (int i = 0; i < cantClientes; i++) {
				instance.listVCPersonas.add( instance.lock.newCondition() );
			}
			System.out.println("se crea el monitor --Edad");
			System.out.println(
					"el orden en el que las personas llegan al cajero coincide con el orden en el que se crearon porque se ejecuto en un solo hilo");
			System.out.println("por si desea comparar el orden en el que pasaron a usar el cajero");
		}
		return instance;
	}

	@Override
	public void pasar(Persona per) throws InterruptedException {
		lock.lock();
		try {
			if (!cajeroLibre) {
				esperando++;
				//se agrega a fila. la estructura es un arbol ordenado
				filaPersonasId.add(per);
				System.out.println("se encola en fila: "+per.getNombre());
				this.listVCPersonas.get( per.getId() ).await();
			} else {
				cajeroLibre = false;
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void salir(Persona persona) {
		lock.lock();
		try {
			if(esperando > 0) {
				esperando--;
				//se despierta al proximo de la fila de personas y se lo elimina de la fila
				this.listVCPersonas.get(filaPersonasId.pollFirst().getId()).signal();
				
			}else {
				cajeroLibre=true;
			}
//			System.out.println(per.getNombre()+" id: "+per.getId()+"\t sale del cajero" );
		} finally {
			lock.unlock();
		}
		
	}

}
