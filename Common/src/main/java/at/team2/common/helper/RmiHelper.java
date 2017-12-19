package at.team2.common.helper;

import at.team2.common.configuration.ConnectionInfo;
import at.team2.common.interfaces.rmi.MainRemoteObjectInf;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiHelper {
    public static Registry getRegistry() throws RemoteException {
        System.setProperty("java.rmi.server.hostname", ConnectionInfo.hostname);
        return LocateRegistry.getRegistry(ConnectionInfo.hostname, ConnectionInfo.port);
    }

    public static MainRemoteObjectInf getSession(Registry registry) throws RemoteException, NotBoundException {
        return (MainRemoteObjectInf) registry.lookup(ConnectionInfo.getUrl());
    }
}