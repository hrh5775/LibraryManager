package at.team2.common.helper;

import at.team2.common.configuration.ConnectionInfo;
import at.team2.common.interfaces.MainRemoteObjectInf;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiHelper {
    public static MainRemoteObjectInf getSession() throws RemoteException, NotBoundException {
        System.setProperty("java.rmi.server.hostname", ConnectionInfo.hostname);
        Registry registry = LocateRegistry.getRegistry(ConnectionInfo.hostname, ConnectionInfo.port);
        MainRemoteObjectInf obj = (MainRemoteObjectInf) registry.lookup(ConnectionInfo.url);

        return obj;
    }
}