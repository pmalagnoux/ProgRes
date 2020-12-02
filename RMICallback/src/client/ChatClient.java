package client;

import java.rmi.Remote;
import java.rmi.RemoteException;




public interface ChatClient extends Remote{
	public void send(String message) throws RemoteException;
	
}
