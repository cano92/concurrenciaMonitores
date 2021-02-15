package partidaPorTurnos;

public class NombreJuego implements Runnable{

	boolean hayGanador=false;
	TurnosMonitor turnosMonitor;
	CanchaMonitor canchaMonitor;
	int jugadaActual;
	
	public NombreJuego( TurnosMonitor turnosMon, CanchaMonitor canchaMon) {
		turnosMonitor = turnosMon;
		canchaMonitor = canchaMon;
	}
	
	@Override
	public void run() {
		
		try {//espera a que todos los jugadores esten listos
			turnosMonitor.iniciarJuego();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("inicia el juego");
		//esperar que un jugador haga una jugada para registrarla

		while(!hayGanador) {
		
			try {
				jugadaActual = canchaMonitor.recibirJugada();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//determina si alguien gano reglas del juego
			if(jugadaActual == 7 ) {
				hayGanador=true;
				canchaMonitor.finalizar();
			}else {
				canchaMonitor.continuar();
			}
	
		}
		//podria tener datos de los juagdores un historial de juegos etc
		//informa que hay aganador y que finalizen los procesos Jugadores
		System.out.println("juego finalizado");
	}
	
	
}
