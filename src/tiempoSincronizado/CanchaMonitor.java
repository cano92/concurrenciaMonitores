package tiempoSincronizado;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CanchaMonitor {
	int cantJugadoresEsperando=0;
	
	final Lock lock = new ReentrantLock(); 
	final Condition vcIniciarPartido = lock.newCondition(); 
	final Condition vcEsperarJugadores = lock.newCondition(); 
	
	public void esperandoJugadores(int aId) throws InterruptedException {
		lock.lock();
		try {
			cantJugadoresEsperando++;
			if(cantJugadoresEsperando == 22) {
				vcIniciarPartido.signal();
			}
			System.out.println("jugador esperando id:"+aId);
			vcEsperarJugadores.await();
		} finally {
			lock.unlock();
		}
	}
	
	
	public void iniciarTiempo() throws InterruptedException {
		//en caso de que no esten todos los jugadores espera
		lock.lock();
		try {
			if(cantJugadoresEsperando < 22) {
				System.out.println("**faltan jugadores");
				vcIniciarPartido.await();
			}
			System.out.println(">>>inicia el tiempo del partido");
			//aqui podria despertar a todos los procesos para que inicien el partido todos juntos
		}finally {
			lock.unlock();
		}
	}
	
	//el objetivo es indicar de alguna manera para que todos los procesos finalizen su funcion
	//ejemplo alguna variable para que puedan salir de un bucle
	public void finalizarPartido() {
		lock.lock();
		try { //en este caso se los libera para que los procesos finalizen
			//porque no se los desperto porque los procesos no hacen nada
			System.out.println(">>>finaliza el tiempo del partido");
			vcEsperarJugadores.signalAll();
//			for(int i=0;i<23 ;i++) {
//				vcEsperarJugadores.signal();
//			}
		}finally {
			lock.unlock();
		}
	}
	
}
