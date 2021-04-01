package passingConditionImpl;

import passingCondition.CajeroMonitor;

/**
 * a diferencia de usar el package concurrent 
 * las instacias ya heredan una implementacion desde la clase Object
 * la cola de procesos dormidos wait esta presente en la clase y no en las instacias y solo dispones de una
 * los methods synchronized encapsulan todo el method y no un fragmento elegible
 * 
 * para casos sencillos usando una instancia Singleton es mas que suficiente 
 * para mas control usar el package concurrent
 * 
 * posee una unica cola donde duermen los procesos con wait()
 */
public class CajeroMonitorSimple implements CajeroMonitor{
	private static CajeroMonitorSimple instance = null;
	
	private boolean cajeroLibre=true;
	private int esperando=0;

	/**
	 * methods class  --singleton pattern  construct
	 * tener en cuenta que debe estar sincronizado porque si dos procesos concurrentes quieren instanciar
	 * en el mismo instante de tiempo la clase. se puede generar dos instancias
	 * porque la variable instance esta null en el mismo instante de tiempo para los procesos concurrentes
	 */
	public synchronized static CajeroMonitorSimple getInstance() {
		
		if (instance == null) {
			System.out.println("se crea el monitor  --simple");
			instance = new CajeroMonitorSimple();
		}
		return instance;
	}

	
	public synchronized void pasar(Persona per) throws InterruptedException {
		if (!cajeroLibre) {
			System.out.println(per.getNombre()+" id:"+ per.getId()+ "\t se encola en la fila");
			esperando++;
			wait();
			
		} else {//pasa a usar el cajero
			cajeroLibre = false;
		}
	}
	
	public synchronized void salir(Persona per) {
		if (esperando > 0) {
			esperando--;
			notify(); // despierta al primero de la cola de dormidos con wait
		} else {
			cajeroLibre = true;
		}
		System.out.println(per.getNombre()+" id: "+per.getId()+"\t sale del cajero" );
	}
	
}
