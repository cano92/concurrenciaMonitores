package passingConditionImpl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import passingCondition.CajeroMonitor;

public class CajeroExtra implements CajeroMonitor{
	private static CajeroExtra instance = null;

	//se pueden generar multiples lock para exclusion mutua y mejorar la concurrencia
	final Lock lock = new ReentrantLock(); 
	
	//se pueden generar multiples  Condition para dormir procesos, y generar distintos prioridades o ordenes
	final Condition filaParaUsarCajero = lock.newCondition(); 
	
	private boolean cajeroLibre = true;
	private int esperando = 0;
	
	// methods class  --singleton pattern  construct
	public synchronized static CajeroExtra getInstance() {
		if (instance == null) {
			instance = new CajeroExtra();
			System.out.println("se crea el monitor --Concurrent");
		}
		return instance;
	}

	public void pasar(Persona per) throws InterruptedException {
		lock.lock();
		try {
			if(!cajeroLibre) {
				esperando++;
				System.out.println(per.getNombre()+" id:"+ per.getId()+ "\t se encola en la fila");
				filaParaUsarCajero.await();				
			}else {
				cajeroLibre=false;
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void salir(Persona per) {
		lock.lock();
		try {
			if(esperando > 0) {
				esperando--;
				filaParaUsarCajero.signal();				
			}else {
				cajeroLibre=true;
			}
			System.out.println(per.getNombre()+" id: "+per.getId()+"\t sale del cajero" );
		} finally {
			lock.unlock();
		}
	}

}
