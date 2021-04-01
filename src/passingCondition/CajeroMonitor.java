package passingCondition;

import java.util.LinkedList;

import passingConditionImpl.Persona;

/**
 * 
 * implementacion de monitor sincronico de passing the Condition concurrente
 */
public interface CajeroMonitor {
	
//	public LinkedList<Persona> ordenLlegada= new LinkedList();
	
	public void pasar(Persona persona) throws InterruptedException;
	
	public void salir(Persona persona);
	
//	default public void ordenLlegadaFila() {
//		System.out.println("orden en el que se encolaron");
//		ordenLlegada.forEach(per->System.out.println(per.getNombre()));
//	}
}
