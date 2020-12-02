package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements Chat{

	private static final long serialVersionUID = 1L;
	private ArrayList<String> listMessage = new ArrayList<String>();
	
	
	
	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void send(String pseudo, String message) throws RemoteException {
		// TODO Auto-generated method stub
		this.listMessage.add(pseudo + ": " + message);
	}

	@Override
	public String getMessage(int pos) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return listMessage.get(pos);
		}
		catch(NullPointerException e){
			return null;
		}
	}

	@Override
	public int getNbMessage() throws RemoteException {
		// TODO Auto-generated method stub
		return listMessage.size();
	}
	
	@Override
	public void connexion(String pseudo) throws RemoteException {
		// TODO Auto-generated method stub
		this.listMessage.add(pseudo + " s'est connecté !!");
	}

	@Override
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
        System.out.println("Serveur prêt!");
        
    }


		
}
