package partidaPorTurnosTimer;

public class TimerTurno implements Runnable {
	
	int tiempoTurno;
	boolean juegoFinalizado;
	
	TurnosMonitor turnosMonitor;
	CanchaMonitor canchaMonitor;
	
	public TimerTurno(int tiempoTurnSeg, TurnosMonitor turnosMon, CanchaMonitor canchaMon) {
		tiempoTurno = tiempoTurnSeg;
		turnosMonitor = turnosMon;
		canchaMonitor = canchaMon;
	}


	@Override
	public void run() {
		
		juegoFinalizado = canchaMonitor.isJuegoFinalizado();
		
		while( !juegoFinalizado ) {
			
			try { //esperando turno
				turnosMonitor.esperarTurno();
				
				//contar tiempo
				Thread.sleep( 10000 ); //10seg
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//alertar que el tiempo del turno finalizo
			System.out.println("tiempo terminado");
			
			//preguntar si la partida esta activa
			juegoFinalizado = canchaMonitor.isJuegoFinalizado();
			
		}
	
	}

}
