package passingCondition;

/**
 * 
 * implementacion de monitor sincronico de passing the Condition concurrente
 */
public interface CajeroMonitor {
	
	public void pasar(int id) throws InterruptedException;
	
	public void salir(int id);
	
}
