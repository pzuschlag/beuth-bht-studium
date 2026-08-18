package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ServerInterface extends Remote {

	CopyOnWriteArrayList<String[]> search(String str, String num) throws RemoteException;

	void shutdown() throws RemoteException;

}