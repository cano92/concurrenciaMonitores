package partidaPorTurnosTimer;

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
	final Condition vcEsperandoJugada = lock.newCondition();
	final Condition vcInput = lock.newCondition();
	
	public void enviarJugada( ) throws InterruptedException {
		lock.lock();
		try {
			//la jugada ya fue agregada por el input
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
		//System.out.println("saca jugada:"+jugadaActual);
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
			vcJugador.signalAll();
		}finally {
			lock.unlock();
		}
	}
	
	public void esperandoJugada() throws InterruptedException {
		lock.lock();
		try {//libera el input para ingresar jugada
			vcInput.signal();
			
			vcEsperandoJugada.await();
		}finally {
			lock.unlock();
		}
	}

	public void esperaInput() throws InterruptedException {
		lock.lock();
		try {//esperandoTurno
			vcInput.await();
		}finally {
			lock.unlock();
		}
	}
	
	public void agregarJugada(int aJugada) {
		lock.lock();
		try {
			jugadaActual=aJugada;
			
			//levantar barrera jugador
			vcEsperandoJugada.signal();
		}finally {
			lock.unlock();
		}
	}
	
	//***** tener cuidado con la falta de  exclucion mutua de la funcion--sincronizacion
	public boolean isJuegoFinalizado() {
		return juegoFinalizado;
	}
}
