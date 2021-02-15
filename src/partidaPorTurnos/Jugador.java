package partidaPorTurnos;

import java.util.Scanner;

public class Jugador implements Runnable{
	int id;
	String entradaTeclado;
	boolean partidaActiva=true;
	
	public Jugador(int aId) {
		id=aId;
	}
	
	@Override
	public void run() {
		//canhcaMonitor.esperandoJugadores();
		
		while(partidaActiva) {
			//monitor.esperandoMiTurno
			
			//realizo mi jugada
			System.out.println("turno del jugador id: "+id);
			Scanner reader = new Scanner(System.in);
			entradaTeclado = reader.nextLine (); 
			
			//monitor.ProximoTurno
		}
		//termino la partida informo mi resultado
		
	}

}
