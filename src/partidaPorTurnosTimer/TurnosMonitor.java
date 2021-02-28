package partidaPorTurnosTimer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TurnosMonitor {
	
	int cantJugadoresNecesarios;
	int cantJugadoresActivos=0;
	
	boolean jugadoresListos=false;
	
	final Lock lock = new ReentrantLock(); 
	
	final Condition filaJugadores = lock.newCondition();
	final Condition vcJuego = lock.newCondition();
	final Condition vcTimerJuego = lock.newCondition();
	
	//constructor
	public TurnosMonitor( int cantJugadoresEsper ) {
		cantJugadoresNecesarios=cantJugadoresEsper;
	}
	
	public void esperandoJugadores() throws InterruptedException {
		lock.lock();
		try {
			cantJugadoresActivos++;
			if( cantJugadoresActivos == cantJugadoresNecesarios ) {
				jugadoresListos=true;
				vcJuego.signal();
				//System.out.println("despierta cordinador");
			}
			//System.out.println("duerme jugador");
			filaJugadores.await();
		}finally {
			lock.unlock();
		}
	}
	
	public void proximoTurno() throws InterruptedException {
		lock.lock();
		try {
			//despierto al siguiente jugador en turno
			filaJugadores.signal(); 
			
			//y me quedo dormido esperando mi siguiente turno
			filaJugadores.await();
		}finally {
			lock.unlock();
		}
	}
	
	public void iniciarJuego() throws InterruptedException {
		lock.lock();
		try {
			if( !jugadoresListos ) {
				vcJuego.await(); //espera a jugadores	
			}
			//despierta al jugador que inicia
			filaJugadores.signal();
		}finally {
			lock.unlock();
		}
	}
	
	public void finalizarJuego() {
		lock.lock();
		try {
			filaJugadores.signalAll();
		}finally {
			lock.unlock();
		}
	}
	
	public void inicarTimer() {
		lock.lock();
		try {
			//levanta la barrera del timer
			System.out.println("el timer comienza");
			vcTimerJuego.signal();
		}finally {
			lock.unlock();
		}
	}
	
	public void esperarTurno() throws InterruptedException {
		lock.lock();
		try {
			vcTimerJuego.await();
		}finally {
			lock.unlock();
		}
	}
	
}
