package server;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.ChatClient;


public class Server extends UnicastRemoteObject implements Chat{
	private static final long serialVersionUID = 1L;
	private ArrayList<String> listMessage = new ArrayList<String>();
	ArrayList<ChatClient> Clients = new ArrayList<ChatClient>();
	
	
	
	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized void send(ChatClient client, String pseudo, String message) throws RemoteException {
		// TODO Auto-generated method stub
		this.listMessage.add(pseudo + ": " + message);
		for (int i = 0; i<this.Clients.size(); i++) { 
			if(!(this.Clients.get(i).equals(client))) {//On envoie sauf à l'emetteur
				try {
					this.Clients.get(i).send(pseudo + ": " + message);
				}catch(RemoteException e) {
					System.out.println("Problème de l'envoie du message");
					this.Clients.remove(i);//On retire l'élément problèmatique de la liste
				}
			}
		}
			
	}

	public String getMessage(int pos) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return this.listMessage.get(pos);
		}
		catch(NullPointerException e){
			return null;
		}
	}

	
	public int getNbMessage() throws RemoteException {
		// TODO Auto-generated method stub
		return this.listMessage.size();
	}
	
	@Override
	public synchronized void connexion(ChatClient CB ,String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		
		Clients.add(CB);
		for(int i = 0;i<this.getNbMessage();i++) {//Envoi de l'historique des messages
			try {
				this.Clients.get(this.Clients.size()-1).send(this.getMessage(i));
			}catch(RemoteException e) {
				System.out.println("Problème de l'envoie du message");
				this.Clients.remove(this.Clients.size()-1);
			}
		}
		for (int i = 0; i<this.Clients.size(); i++) 
			try {
				this.Clients.get(i).send(pseudo + " s'est connecté !!");
			}catch(RemoteException e) {
				System.out.println("Problème de l'envoie du message");
				this.Clients.remove(i);//On retire l'élément problèmatique de la liste
			}
			
		this.listMessage.add(pseudo + " s'est connecté !!");
	}

	
	public synchronized void deconnexion(ChatClient client, String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		this.listMessage.add(pseudo + " s'est déconnecté !!");
		for (int i = 0; i<this.Clients.size(); i++) {
			try {
				this.Clients.get(i).send(pseudo + " s'est déconnecté !!");
			} catch(RemoteException e) {
				System.out.println("Problème de l'envoie du message");
				this.Clients.remove(i);//On retire l'élément problèmatique de la liste
			}
		}
		this.Clients.remove(client);//On retire le client voulant se déconnecter de la liste
		
	}
	
	
	
	
	
	public static void main(String args[]) throws Exception {
        try { 
            LocateRegistry.createRegistry(1099); 
        } catch (RemoteException e) {
        	System.out.println("Problème de Création");
        }
        Server chatServer = new Server();
        Naming.rebind("//localhost/RmiServer", chatServer);
        System.out.println("Serveur callback prêt!");
        
   }	
          
		
}
