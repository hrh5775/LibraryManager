package at.team2.server.controller;

import at.team2.common.configuration.ConnectionInfo;
import at.team2.common.interfaces.MainRemoteObjectInf;
import at.team2.server.remote.MainRemoteObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiController {
    public static void main(String[] args) throws MalformedURLException, RemoteException, URISyntaxException {
        new Thread(() -> {
            ProcessBuilder processBuilder = new ProcessBuilder("rmiregistry", ConnectionInfo.port + "", "-J-Djava.rmi.server.useCodebaseOnly=false");
            try {
                Process process = processBuilder.start();
                process.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // wait for the process to start
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }


        String codebase = MainRemoteObjectInf.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();

        System.setProperty("java.rmi.server.hostname", ConnectionInfo.hostname);
        System.setProperty("java.rmi.server.codebase", codebase);

        Registry registry = LocateRegistry.getRegistry(ConnectionInfo.port);
        MainRemoteObject obj = new MainRemoteObject();
        registry.rebind(ConnectionInfo.url, obj);

        System.out.println("Server started successfully");
        // https://docs.oracle.com/javase/7/docs/technotes/guides/rmi/hello/hello-world.html
        // https://stackoverflow.com/questions/23794997/java-rmi-codebase-not-working
        // rmiregistry 1099 -J-Djava.rmi.server.useCodebaseOnly=false
    }
}