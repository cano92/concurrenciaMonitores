package partidaPorTurnosTimer;

import java.util.Scanner;

public class Jugador implements Runnable{
	int id;
	//String entradaTeclado;
	boolean juegoFinalizado;
	int jugadaActual;
	CanchaMonitor canchaMonitor;
	TurnosMonitor turnosMonitor;
	
	public Jugador(int aId, CanchaMonitor canchaMon,TurnosMonitor turnosMon ) {
		id=aId;
		canchaMonitor = canchaMon;
		turnosMonitor = turnosMon;
	}
	
	@Override
	public void run() {
		try {
			turnosMonitor.esperandoJugadores();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//por si es el 2do jugador en turno y el 1ro ya gano
		juegoFinalizado = canchaMonitor.isJuegoFinalizado();
		
		while(!juegoFinalizado) {
			//activo el timer() de mi turno
			turnosMonitor.inicarTimer();
			
			System.out.println("turno del jugador id: "+id);
			try { //espero en una barrera que lo puede levantar el timer o una jugada
				canchaMonitor.esperandoJugada();
				
				// guarda el movimiento realizado o pierde el turno
				canchaMonitor.enviarJugada(); //****
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			try { //llamo al proximo y me quedo dormido
				turnosMonitor.proximoTurno();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			juegoFinalizado = canchaMonitor.isJuegoFinalizado();
		}
		//termino la partida informo mi resultado
		turnosMonitor.finalizarJuego();
		
	}

	public int realizarMovimiento() {
		System.out.println("ingrese un nro entre 0 .. 9 ");
		Scanner scanner = new Scanner(System.in);  // Create a Scanner object
	
		return scanner.nextInt();
	} 
	
}
