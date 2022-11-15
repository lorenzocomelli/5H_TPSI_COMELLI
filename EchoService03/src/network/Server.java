package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		
		int severPort=8765;
		String clientMsg = "";
		int len=0, voc=0, con=0;
		
		try {			 
			// Creazione del socket sul server e ascolto sulla porta
			ServerSocket serverSocket = new ServerSocket(severPort);
			System.out.println("Server: in ascolto sulla porta " + severPort);

			// Attesa della connessione con il client
			Socket clientSocket = serverSocket.accept();
			
			// Create input and output streams to read/write data
			DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());	

			// Scambio di dati tra client e server
			while(!clientMsg.equals("quit")) {
				voc=0; con=0;
				//Lettura dato da stream di rete
				clientMsg = inStream.readUTF();
				System.out.println("Server: ricevuto messaggio " + clientMsg );
				//Lunghezza messaggio
				len=clientMsg.length();
				clientMsg.toLowerCase();
				for(int i=0;i<len;i++) {
					if(clientMsg.charAt(i)=='a' || clientMsg.charAt(i)=='e' || clientMsg.charAt(i)=='i' || clientMsg.charAt(i)=='o' || clientMsg.charAt(i)=='u') {
						voc++;
					}
					else {
						con++;
					}
				}
				
				//Invio dati su stream di rete
				outStream.writeUTF("Echo from server : "+ clientMsg +"\nLunghezza parola :"+ len +"\nVocali : "+ voc +"\nConsonanti : "+ con);
				
				System.out.println("Server: invio messaggio "    + clientMsg );
				System.out.println("Server: Lunghezza parola "    + len );
				System.out.println("Server: Vocali "    + voc );
				System.out.println("Server: Consonanti  "    + con );
				outStream.flush();
			}

			// Close resources
			serverSocket.close();
			clientSocket.close();
			inStream.close();
			outStream.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}