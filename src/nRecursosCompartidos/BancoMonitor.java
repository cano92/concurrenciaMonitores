package nRecursosCompartidos;

import java.util.LinkedList;

public class BancoMonitor {
	private static BancoMonitor instance = null;
	int empleadosDisponibles;
	
	int cantClientesEspera=0;
	int cantEmpleadosLibres=0;
	
	int auxId;
	
	LinkedList<Integer> empleadosLibresId = new LinkedList<Integer>();
	
	//** Constructor
	public synchronized static BancoMonitor getInstance( int empleDisp) {
		if (instance == null) {
			instance = new BancoMonitor();
			instance.setEmpleadosDisponibles(empleDisp);
		}
		return instance;
	}
	
	
	//un Cliente avisa que llego
	public synchronized int llegaCliente() throws InterruptedException{
		if (cantEmpleadosLibres == 0) {
			cantClientesEspera ++;
			wait();
		}else {
			cantEmpleadosLibres--;
		}
		auxId = empleadosLibresId.getFirst();
		//return empleadosLibresId.removeFirst();
		empleadosLibresId.removeFirst();
		return auxId;
	}
	
	// un empleado llama al siguiente Cliente en la fila
	public synchronized void proximoCliente(int empleId) {
		if(cantClientesEspera > 0) {
			cantClientesEspera --;
			notify();//singal() de la variable cond implicita
		}else {
			cantEmpleadosLibres++;
		}
		empleadosLibresId.addLast(empleId);
	}

	public synchronized int getEmpleadosDisponibles() {
		return empleadosDisponibles;
	}

	public synchronized void setEmpleadosDisponibles(int empleadosDisponibles) {
		this.empleadosDisponibles = empleadosDisponibles;
	}
	
	
}
