package tresRecursosCompartidos;

import java.util.ArrayList;

public class Persona implements Runnable{
	int id;
	BancoMonitor bancoMonitor;
	ArrayList<EscritorioMonitor> escritoriosMonitor;
	
	int idEmpleado;
	EscritorioMonitor auxEscritorio;
	
	String resultado;
	
	public Persona(int aId, BancoMonitor bancMonitor,ArrayList<EscritorioMonitor> escrMonitor) {
		id = aId;
		bancoMonitor=bancMonitor;
		escritoriosMonitor=escrMonitor;
	}
	
	@Override
	public void run() {
		
		//llega cliente espera en la fila si los empleados estan ocupados
		try {
			idEmpleado=bancoMonitor.llegaCliente();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("el Cliente:"+id+"\t es atendido por el Empleado:"+idEmpleado);
		auxEscritorio =escritoriosMonitor.get(idEmpleado);
		
		try {
			resultado= auxEscritorio.atencionCliente("datos cliente");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("finaliza cliente:"+id+"\t:"+resultado);
		
		
	}
	
	
}
