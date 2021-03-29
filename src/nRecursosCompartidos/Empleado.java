package nRecursosCompartidos;

import java.util.ArrayList;

public class Empleado implements Runnable {
	int id;
	String datosUsuario;
	
	BancoMonitor bancoMonitor;
	ArrayList<EscritorioMonitor> escritoriosMonitor;
	EscritorioMonitor auxEscritorio;
	
	
	public Empleado(int aId, BancoMonitor bancMonitor,ArrayList<EscritorioMonitor> escrMonitor) {
		id=aId;
		bancoMonitor=bancMonitor;
		escritoriosMonitor=escrMonitor;
	}
	
	@Override
	public void run() {
		
		//los empleados atienden indefinidamente
		while(true) {
			//solicita el prixmo cliente
			bancoMonitor.proximoCliente(id);
		
			auxEscritorio = escritoriosMonitor.get(id);
			try { //recupera datos del cliente
				datosUsuario = auxEscritorio.esperarDatos();
				
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			
			//realiza algo con los datos del cliente
			System.out.println("empleado:"+id+"\tprocesa datos:"+datosUsuario);
			
			try {
				//Thread.sleep( 2000 );
				auxEscritorio.enviarResultados("resultado enviado por Empleado"+id);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	
	
}
