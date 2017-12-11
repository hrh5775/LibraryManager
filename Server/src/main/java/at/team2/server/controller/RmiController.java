package at.team2.server.controller;

import at.team2.common.configuration.ConnectionInfo;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.rmi.MainRemoteObjectInf;
import at.team2.server.remote.rmi.MainRemoteObject;
import at.team2.server.tasks.RemoveInvalidReservationsTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class RmiController {
    public static void main(String[] args) throws MalformedURLException, RemoteException, URISyntaxException, NotBoundException {
        String serverURL = null;
        String additionalServerURLExtension = null;
        int port = -1;


        // print help
        System.out.println("# The following options are available:");
        System.out.println("server_url=\"" + ConnectionInfo.hostname + "\"");
        System.out.println("additional_server_url_extension=\"" + ConnectionInfo.additionalUrlExtension + "\"");
        System.out.println("port=\"" + ConnectionInfo.port + "\"");


        String[] argSplit;

        for(String item : args) {
            argSplit = item.split("=");

            switch (argSplit[0].toLowerCase()) {
                case "server_url":
                    if(argSplit.length > 1) {
                        serverURL = argSplit[1];
                    }
                    break;
                case "additional_server_url_extension":
                    if(argSplit.length > 1) {
                        additionalServerURLExtension = argSplit[1];
                    }
                    break;
                case "port":
                    if(argSplit.length > 1) {
                        port = Integer.parseInt(argSplit[1]);
                    }
                    break;
            }
        }

        if(serverURL != null) {
            ConnectionInfo.hostname = serverURL;
        }

        if(additionalServerURLExtension != null) {
            ConnectionInfo.additionalUrlExtension = additionalServerURLExtension;
        }

        if(port > -1) {
            ConnectionInfo.port = port;
        }

        // print configuration
        System.out.println();
        System.out.println("# Current configuration:");
        System.out.println("+ server_url:                       " + "\"" + ConnectionInfo.hostname + "\"");
        System.out.println("+ additional_server_url_extension:  " + "\"" + ConnectionInfo.additionalUrlExtension + "\"");
        System.out.println("+ full url:                         " + "\"" + ConnectionInfo.url + "\"");
        System.out.println("+ port:                             " + "\"" + ConnectionInfo.port + "\"");
        System.out.println();


        // this command can also be used from a command line
        new Thread(() -> {
            ProcessBuilder processBuilder = new ProcessBuilder("rmiregistry", ConnectionInfo.port + "", "-J-Djava.rmi.server.useCodebaseOnly=false");
            try {
                Process process = processBuilder.start();
                process.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // wait for the process to start
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }


        String codebase = MainRemoteObjectInf.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();

        System.setProperty("java.rmi.server.codebase", codebase);
        Registry registry = RmiHelper.getRegistry();
        MainRemoteObject obj = new MainRemoteObject();
        registry.rebind(ConnectionInfo.url, obj);

        System.out.println("Start background tasks ...");
        System.out.print("\t* Start 'Remove Invalid Reservation Task' ...");
        new Thread(new RemoveInvalidReservationsTask()).start();
        System.out.println("\tSuccessful");

        System.out.println("\nServer has started successfully");
        // https://docs.oracle.com/javase/7/docs/technotes/guides/rmi/hello/hello-world.html
        // https://stackoverflow.com/questions/23794997/java-rmi-codebase-not-working
        // rmiregistry 1099 -J-Djava.rmi.server.useCodebaseOnly=false
    }
}