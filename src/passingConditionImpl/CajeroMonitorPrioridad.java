package passingConditionImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import passingCondition.CajeroMonitor;

public class CajeroMonitorPrioridad implements CajeroMonitor{
	private static CajeroMonitorPrioridad instance = null;
	
	final Lock lock = new ReentrantLock(); 
	
	private ArrayList<Condition> listVCPersonas=new ArrayList<Condition>();
	
	// es la fila en que los procesos van a despertar aqui se marca la prioridad
	private LinkedList<Integer> filaPersonasId= new LinkedList<Integer>();
	private boolean cajeroLibre = true;
	private int esperando = 0;
	
	
	// methods class  --singleton pattern  construct
	public synchronized static CajeroMonitorPrioridad getInstance( int cantClientes) {
		if (instance == null) {
			instance = new CajeroMonitorPrioridad();
			
			//se agregan las Variables Condition para demorar los procesos clientes
			for(int i=0;i<cantClientes;i++) {
				//instance.addVariableCondicion( instance.lock.newCondition() );
				instance.listVCPersonas.add( instance.lock.newCondition() );
			}

			System.out.println("se crea el monitor --Prioridad");
			System.out.println("el orden en el que las personas llegan al cajero coincide con el orden en el que se crearon porque se ejecuto en un solo hilo");
			System.out.println("por si desea comparar el orden en el que pasaron a usar el cajero");
		}
		return instance;
	}
	
	@Override
	public void pasar(Persona per) throws InterruptedException {
		lock.lock();
		try {
			if(!cajeroLibre) {
				esperando++;
				//se agrega el id a la fila de espera
				this.addFilaPersonaId(per);
				
				//ordenLlegada.add(per);//solo reportes desde interfaz
//				System.out.println(per.getNombre()+" id:"+ per.getId()+ "\t se encola en la fila");
				//se recupera la Variable Condition con el Id del cliente para dormir el proceso				
				this.listVCPersonas.get( per.getId() ).await();
			}else {
				cajeroLibre=false;
			}
			
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void salir(Persona per) {
		lock.lock();
		try {
			if(esperando > 0) {
				esperando--;
				//se despierta al proximo de la fila de personas y se lo elimina de la fila
				this.listVCPersonas.get(this.filaPersonasId.removeFirst()).signal();
			}else {
				cajeroLibre=true;
			}
//			System.out.println(per.getNombre()+" id: "+per.getId()+"\t sale del cajero" );
		} finally {
			lock.unlock();
		}
	}

	// Agrega una nueva variable condicion para ordenar los clientes
	public void addVariableCondicion(Condition con) {
		this.listVCPersonas.add(con);
	}
	
	//se agrega por orden de prioridad
	private void addFilaPersonaId(Persona per) {
		// las personass mayores o iguales a 60 años son atendidos primero 
		if( per.getEdad() > 59 ) {
			filaPersonasId.addFirst(per.getId());
		}else {
			filaPersonasId.addLast(per.getId());
		}
	}
	

}
