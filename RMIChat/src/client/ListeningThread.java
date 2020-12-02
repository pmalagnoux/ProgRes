package client;


import java.io.IOException;
import java.rmi.RemoteException;




public class ListeningThread extends Thread{
	private int nbMess = 0;
	private Client chatClient;
	
	public ListeningThread(Client chatClient) throws IOException {
			this.chatClient = chatClient;
			}
	
	
	
	
	public void run(){
		while(true) {
			
			try {
				int nbMessageList = chatClient.Server.getNbMessage();
				if(nbMessageList!= this.nbMess) {
					System.out.println(chatClient.Server.getMessage(nbMess));
					nbMess++;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur de connexion");
			}
		}
	}

}
