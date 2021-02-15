package partidaPorTurnos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CanchaMonitor {

	boolean juegoFinalizado=false;
	boolean jugadaLista=false;
	int jugadaActual;
	
	final Lock lock = new ReentrantLock(); 
	
	final Condition vcJuego = lock.newCondition();
	final Condition vcJugador = lock.newCondition();
	
	
	public void enviarJugada( int aJugada) throws InterruptedException {
		lock.lock();
		try {
			jugadaActual=aJugada;
			vcJuego.signal();
			jugadaLista=true;
			vcJugador.await();
			//espera a que el juego guarde la jugada
			//y actualize el estado de juego		
		}finally {
			lock.unlock();
		}
	}
	
	public int recibirJugada() throws InterruptedException {
		lock.lock();
		try {
			if(!jugadaLista) {
				vcJuego.await();
			}//cambia eñ estado para que otro jugador agregue nuevo movimiento
			jugadaLista=false;
		}finally {
			lock.unlock();
		}
		System.out.println("saca jugada:"+jugadaActual);
		return jugadaActual;
	}
	
	public void continuar() {
		lock.lock();
		try { //deja continuar al jugador sin finalizar el juego
			vcJugador.signal();	
		}finally {
			lock.unlock();
		}
	}
	
	public void finalizar() {
		lock.lock();
		try {
			juegoFinalizado=true;
			vcJugador.signal();
		}finally {
			lock.unlock();
		}
	}
	
	//***** tener cuidado con la falta de  exclucion mutua de la funcion--sincronizacion
	public boolean isJuegoFinalizado() {
		return juegoFinalizado;
	}
}
