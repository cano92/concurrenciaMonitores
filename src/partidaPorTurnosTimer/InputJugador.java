package partidaPorTurnosTimer;

import java.util.Scanner;

//entrada de cada jugador

public class InputJugador implements Runnable{
	
	int id;
	boolean juegoFinalizado;
	CanchaMonitor canchaMonitor;
	int auxJugada;
	
	public InputJugador( CanchaMonitor canchaMon) {
	
		canchaMonitor = canchaMon;
	}
	
	@Override
	public void run() {
		
		juegoFinalizado = canchaMonitor.isJuegoFinalizado();
		
		while(!juegoFinalizado) {
			
			try { //espera a que sea su turno en una barrera
				canchaMonitor.esperaInput();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//realiza una jugada
			System.out.println("ingrese un nro entre 0 .. 9 ");
			Scanner scanner = new Scanner(System.in);  // Create a Scanner object
			auxJugada=scanner.nextInt();
			
			canchaMonitor.agregarJugada(auxJugada); //levanta la barrera del jugador para que siga
			
			//puede que el timer ya la aya levantado
			
			//pregunta si la partida sigue activa para poder finalizar
			juegoFinalizado = canchaMonitor.isJuegoFinalizado();
		}
		
	}

}
