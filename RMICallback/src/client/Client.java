package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import server.Chat;

public class Client extends UnicastRemoteObject implements ChatClient{
	private static final long serialVersionUID = 1L;
	Chat Server;
	private String pseudo = "";
	
	public Client(String pseudo) throws MalformedURLException, RemoteException, NotBoundException {
		Server = (Chat)Naming.lookup("//localhost/RmiServer");

			 this.pseudo = pseudo;
			 
		}
	
	public static void main(String args[]) throws Exception {
		Scanner s = new Scanner(System.in);
		String message = "";
		System.out.print("Entrez un pseudo :");
		message = s.nextLine();
		try {
			Client chatClient = new Client(message);       
			
			chatClient.Server.connexion((ChatClient)chatClient, chatClient.pseudo);
	        while(!(message.equals("/quit"))) {
	        	
	        	message = s.nextLine();
	        	
	        	if(!(message.equals("/quit"))){
	        		
	        		 try {
	        			 chatClient.Server.send((ChatClient)chatClient, chatClient.pseudo, message);
	        		 }
	        		 catch(RemoteException e) {
	        			 System.out.println("Erreur de connexion");
	        		 }
	        	}
	        	else {
	        		try {
	        			 chatClient.Server.deconnexion((ChatClient)chatClient,chatClient.pseudo);
	        		 }
	        		 catch(RemoteException e) {
	        			 System.out.println("Erreur de connexion");
	        			 
	        		 }
	        	}
	        }
        }
		catch(Exception e) {
			System.out.println("Pas de serveur en ligne !");
		}
		
        s.close();
	}

	@Override
	public void send(String message) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(message);
	}
}
