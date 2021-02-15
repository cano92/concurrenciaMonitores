package partidaPorTurnos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CanchaMonitor {
	
	int cantJugadoresNecesarios;
	int cantJugadoresActivos=0;
	
	final Lock lock = new ReentrantLock(); 
	final Condition turnoJugadores = lock.newCondition();
	final Condition esperandoJugadores = lock.newCondition();
	
	//constructor
	public CanchaMonitor( int cantJugadoresEsper ) {
		cantJugadoresNecesarios=cantJugadoresEsper;
	}
	
	public void esperandoJugadores() throws InterruptedException {
		lock.lock();
		try {
			if( cantJugadoresActivos == cantJugadoresNecesarios ) {
				esperandoJugadores.signalAll();
			}else {
				esperandoJugadores.await();
			}
		}finally {
			lock.unlock();
		}
	}
	
	public void proximoTurno() throws InterruptedException {
		lock.lock();
		try {
			//despierto al siguiente jugador en turno
			turnoJugadores.signal(); 
			
			//y me quedo dormido esperando mi siguiente turno
			turnoJugadores.await();
		}finally {
			lock.unlock();
		}
	}
	
	
}
