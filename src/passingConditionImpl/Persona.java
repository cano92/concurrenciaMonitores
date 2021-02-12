package passingConditionImpl;

import java.util.ArrayList;

import passingCondition.CajeroMonitor;

public class Persona implements Runnable{
	//variable cajero implementa single pattern  - al monitor
	private CajeroMonitor cajeroMonitor;
	
	private int id;
	private String nombre;

	//CONSTRUCTS..
	public Persona(int id, CajeroMonitor cajeroMonitor,String nombre) {
		this.setId(id);
		this.setNombre(nombre);
		this.setCajeroMonitor(cajeroMonitor);
	}
	
	public Persona(int id, CajeroMonitor cajeroMonitor) {
		this.setId(id);
		this.setCajeroMonitor(cajeroMonitor);
	}
	

	@Override
	public void run(){
		try { //solicita pasar al cajero -en caso de cajero libre pasa sino se encola en fila
			cajeroMonitor.pasar( this.getId() );
		} catch (InterruptedException e) {
			//el proceso persona this.getId()  no pudo formarse en la fila para entrar al acajero
			e.printStackTrace();
		}
	
		//uso del cajero
		System.out.println("usuario id: "+ this.getId() + "\t usa el cajero" );
	
		
		//solicita salir del monitor cajero -en caso de fila deja pasar al primero de la fila sino deja el cajero libre
		cajeroMonitor.salir( this.getId()); 
	}
	

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CajeroMonitor getCajeroMonitor() {
		return cajeroMonitor;
	}

	public void setCajeroMonitor(CajeroMonitor cajeroMonitor) {
		this.cajeroMonitor = cajeroMonitor;
	}

}
