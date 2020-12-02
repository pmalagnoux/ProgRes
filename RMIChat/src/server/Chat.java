package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote{
	public void send(String pseudo, String message) throws RemoteException;
	public String getMessage(int pos) throws RemoteException;
	public int getNbMessage() throws RemoteException;
	public void connexion(String pseudo) throws RemoteException;
	public void deconnexion(String pseudo) throws RemoteException;
}
