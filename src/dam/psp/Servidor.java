package dam.psp;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Servidor {
	public int PUERTO=5555;
	
	public Servidor() throws IOException{
		System.out.println("Obteniendo factoria de socket del servidor: ");
		
		SSLServerSocketFactory sockerSRVFactory= (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		
		System.out.println("Creando el socket de atención");
		//socket de atencion escucha
		SSLServerSocket socketSRV=(SSLServerSocket)sockerSRVFactory.createServerSocket(PUERTO);
		
		while(true){
			System.out.println("Aceptando conexiones...");
			SSLSocket socketAtencion=(SSLSocket)socketSRV.accept();
			System.out.println("Atendiendo nuevo cliente");
			new SrvHilo(socketAtencion).start();
		}
	}

	public static void main(String[] args) {
		//arrancar el servidor
		try {
			//almacen de claves publicas
			System.setProperty("javax.net.ssl.keyStore","./cert/AlmacenSRV");
			System.setProperty("javax.net.ssl.keyStorePassword","12345678");
			
			//almacen de clave de confianza
			System.setProperty("javax.net.ssl.trustStore","./cert/AlmacenSRV");
			System.setProperty("javax.net.ssl.trustStorePassword","12345678");
			
			new Servidor();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
