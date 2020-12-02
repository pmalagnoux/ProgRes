package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.ChatClient;


public class Server extends UnicastRemoteObject implements Chat{
	//ChatClient Clients;
	//private int nbMessageEnv = 0;
	private static final long serialVersionUID = 1L;
	private ArrayList<String> listMessage = new ArrayList<String>();
	ArrayList<ChatClient> Clients = new ArrayList<ChatClient>();
	
	
	
	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void send(String pseudo, String message) throws RemoteException {
		// TODO Auto-generated method stub
		this.listMessage.add(pseudo + ": " + message);
		//System.out.println(pseudo + ": " + message);// a deleate
		//System.out.println(this.getNbMessage());
		for (int i = 0; i<this.Clients.size(); i++)
			this.Clients.get(i).send(pseudo + ": " + message);
	}

	public String getMessage(int pos) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return listMessage.get(pos);
		}
		catch(NullPointerException e){
			return null;
		}
	}

	
	public int getNbMessage() throws RemoteException {
		// TODO Auto-generated method stub
		return listMessage.size();
	}
	
	@Override
	public void connexion(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		this.listMessage.add(pseudo + " s'est connecté !!");
		try {
			Clients.add((ChatClient)Naming.lookup("//localhost/RmiClient"));
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			System.out.println("oie");
		}
	}

	
	public void deconnexion(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		this.listMessage.add(pseudo + " s'est déconnecté !!");
	}
	
	
	
	
	public static void main(String args[]) throws Exception {
        try { 
            LocateRegistry.createRegistry(1099); 
        } catch (RemoteException e) {
        }
        Server chatServer = new Server();
        Naming.rebind("//localhost/RmiServer", chatServer);
        System.out.println("Serveur callback prêt!");
        
        while(true) {
        	try {	
        		}
        	
        	
  
        	catch(Exception e) {
        		System.out.println("hy");
        	}
        }
	}	
}
