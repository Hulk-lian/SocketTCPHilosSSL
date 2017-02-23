package dam.psp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLSocket;//la libreria pollua para que sea seguro los sockets

public class SrvHilo extends Thread{
	SSLSocket miSocket;
	
	public SrvHilo(SSLSocket socket) {
		this.miSocket=socket;
	}
	
	@Override
	public void run(){		
		try {
			InputStreamReader  is= new InputStreamReader(miSocket.getInputStream());
			BufferedReader br=new BufferedReader(is);
			String mensajeRecibido= br.readLine();
			
			System.out.println("Mensaje recibido "+ mensajeRecibido);
			
			//enviamos una respuesta hacia el cliente
			BufferedOutputStream bo= new BufferedOutputStream(miSocket.getOutputStream());
			PrintWriter pr= new PrintWriter(bo);
			
			byte[] mensSerializado= mensajeRecibido.getBytes("utf-8");
			MessageDigest sha= MessageDigest.getInstance("SHA1");
			
			pr.println(sha.digest(mensSerializado));
			pr.flush();
			pr.close();
			
			System.out.println("Cerrando el hilo cliente");
			miSocket.close();
			
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
