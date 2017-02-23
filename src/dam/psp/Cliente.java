package dam.psp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Cliente {
	
	static final String DEST="localhost";
	static final int PUERTO_DEST=5555;
	
	private void mostrarCifrado(SSLSocket socket){
		String[] protocolos=socket.getEnabledProtocols();
		System.out.println("protocoloa hailitados");
		for(int i=0;i<protocolos.length;i++){
			System.out.println(protocolos[i]);
		}
		
		String[] protocolosSop=socket.getSupportedProtocols();
		System.out.println("protocolos hailitados");
		for(int i=0;i<protocolosSop.length;i++){
			System.out.println(protocolosSop[i]);
		}
		
		String[] protocolos_molones=new String[1];
		protocolos_molones[0]="TLSv1.2";
		socket.setEnabledProtocols(protocolos_molones);
		
		String[] protocolosHab=socket.getSupportedProtocols();
		System.out.println("protocolos hailitados");
		for(int i=0;i<protocolosHab.length;i++){
			System.out.println(protocolosHab[i]);
		}
	}
	
	public Cliente(String mensaje) throws UnknownHostException, IOException{
		SSLSocketFactory socketFactory=(SSLSocketFactory)SSLSocketFactory.getDefault();
		
		System.out.println("Creando socket en el cliente");
		
		SSLSocket socketCLI=(SSLSocket)socketFactory.createSocket(DEST,PUERTO_DEST);
		
		mostrarCifrado(socketCLI);
		
		BufferedOutputStream bo= new BufferedOutputStream(socketCLI.getOutputStream());
		PrintWriter pw= new PrintWriter(bo);
		pw.println(mensaje);
		pw.flush();
		
		
		//preparacion para recobir la respuesta del servidor.
		
		InputStreamReader is= new InputStreamReader(socketCLI.getInputStream());
		BufferedReader br= new BufferedReader(is);
		
		System.out.println("mensaje devuelto desde el servidor: "+br.readLine());
		
		System.out.println("Cerrando socket");
		pw.close();
		socketCLI.close();
		System.out.println("Fin");
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		//almacen de claves publicas
		System.setProperty("javax.net.ssl.keyStore","./cert/AlmacenCLI");
		System.setProperty("javax.net.ssl.keyStorePassword","87654321");
		
		//almacen de clave de confianza
		System.setProperty("javax.net.ssl.trustStore","./cert/AlmacenCLI");
		System.setProperty("javax.net.ssl.trustStorePassword","87654321");
		
		new Cliente("Hola caracola");
	}
}
