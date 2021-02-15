package tiempoSincronizado;

public class Persona implements Runnable{

	int id;
	CanchaMonitor canchaMonitor;
	
	public Persona(int aId, CanchaMonitor canchaMon) {
		id=aId;
		canchaMonitor= canchaMon;
	}
	
	@Override
	public void run() {
		//indica que llego a la cancha para que cuando se completen los jugadores empieze el tiempo
		try {
			//una vez completado los jugadores comienza tiempo
			// y todos los procesos son despertados al finalizar
			canchaMonitor.esperandoJugadores(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("jugador finalizo id:"+id);
		
	}
	
}
