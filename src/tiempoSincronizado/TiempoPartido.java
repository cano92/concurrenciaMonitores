package tiempoSincronizado;

/**
 * este el tiempo del partido en comun para los 22 procesos que se
 * ejecuntan en concurrente y comparten
 * */
public class TiempoPartido implements Runnable {
	
	CanchaMonitor canchaMonitor;
	
	public TiempoPartido( CanchaMonitor canchaMon ) {
		canchaMonitor=canchaMon;
	}

	@Override
	public void run() {
		//inicia el tiempo si estan todos los jugadores o se duerme
		try {
			canchaMonitor.iniciarTiempo();
			
			Thread.sleep( 100 ); //10seg
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		canchaMonitor.finalizarPartido();
		System.out.println("finaliza cancha");
	}
	
}
