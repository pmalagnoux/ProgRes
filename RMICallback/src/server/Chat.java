package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ChatClient;

public interface Chat extends Remote{
	public void send(ChatClient client, String pseudo, String message) throws RemoteException;
	public void connexion(ChatClient CB, String pseudo) throws RemoteException;
	public void deconnexion(ChatClient client, String pseudo) throws RemoteException;
}
