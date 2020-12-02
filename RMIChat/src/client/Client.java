package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import server.Chat;

public class Client {
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
		
		try {
			 chatClient.Server.connexion(chatClient.pseudo);
		 }
		 catch(RemoteException e) {
			 System.out.println("Erreur de connexion");
		 }
        new ListeningThread(chatClient).start();
        while(!(message.equals("/quit"))) {
	        	
	        	message = s.nextLine();
	        	
	        	if(!(message.equals("/quit"))){
	        		
	        		 try {
	        			 chatClient.Server.send(chatClient.pseudo, message);
	        		 }
	        		 catch(RemoteException e) {
	        			 System.out.println("Erreur de connexion");
	        			 break;
	        		 }
	        	}
	        	else {
	        		try {
	        			 chatClient.Server.deconnexion(chatClient.pseudo);
	        		 }
	        		 catch(RemoteException e) {
	        			 System.out.println("Erreur de connexion");
	        			 break;
	        		 }
	        	}
	        	
        }
		}
		catch(Exception e) {
			System.out.println("Pas de serveur en ligne !");
		}
		
        s.close();
    }
	
}
