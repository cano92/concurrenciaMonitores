package passingConditionImpl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import passingCondition.CajeroMonitor;

public class CajeroExtra implements CajeroMonitor{
	private static CajeroExtra instance = null;

	//se pueden generar multiples lock para exclusion mutua y mejorar la concurrencia
	//aunque esto romperia la idea de monitor de que cada methods se ejecuta con exclusion mutua
	final Lock lock = new ReentrantLock(); 
	//se pueden generar multiples -colas Condition para dormir procesos segun sea necesario y no solo uno
	final Condition filaParaUsarCajero = lock.newCondition(); 

	//se pude crear colleciones estaticas y dinamicas para generar diferentes ordenes o prioridades para 
	//dormir o despertar procesos segun prioridad o necesidad   ejemplo
	//Condition[] arrayConditions = new Condition[10];
	
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

	public void pasar(int id) throws InterruptedException {
		lock.lock();
		try {
			if(!cajeroLibre) {
				esperando++;
				System.out.println("el proceso id: "+id+ "\t se duerme");
				filaParaUsarCajero.await();				
			}else {
				cajeroLibre=false;
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void salir(int id) {
		lock.lock();
		try {
			if(esperando > 0) {
				esperando--;
				filaParaUsarCajero.signal();				
			}else {
				cajeroLibre=true;
			}
			System.out.println("usuario id: "+id  +"\tsale del cajero" );
		} finally {
			lock.unlock();
		}
	}

}
