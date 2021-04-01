package partidaPorTurnos;

import java.util.Scanner;

public class Jugador implements Runnable{
	int id;
	//String entradaTeclado;
	boolean juegoFinalizado;
	int jugadaActual;
	CanchaMonitor canchaMonitor;
	TurnosMonitor turnosMonitor;
	
	public Jugador(int aId, CanchaMonitor canchaMon,TurnosMonitor turnosMon ) {
		this.id=aId;
		this.canchaMonitor = canchaMon;
		this.turnosMonitor = turnosMon;
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
			
			jugadaActual=this.realizarMovimiento();
			
			try {
				canchaMonitor.enviarJugada(jugadaActual); //****
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
		System.out.println("turno del jugador id: "+id);
		System.out.println("ingrese un nro entre 0 .. 9 ");
		Scanner scanner = new Scanner(System.in);  // Create a Scanner object
	
		return scanner.nextInt();
	} 
	
}
