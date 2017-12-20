package at.team2.client;

import com.sun.javafx.PlatformUtil;
import at.team2.client.configuration.Configuration;
import at.team2.client.gui.AppConfiguration;
import at.team2.client.gui.Client;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

public class Bootstrapper {
    public static void main(String[] args) {
        Configuration configuration = AppConfiguration.getConfiguration();
        AppConfiguration.saveConfiguration(null, configuration);

        if(configuration.getUseEjb()) {
            try {
                String path = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
                String target = "target";
                String debugPathInfo = target + File.separator + "classes";

                int classIndex = path.lastIndexOf(debugPathInfo);
                String debugging1 = "";
                String debugging2 = "";

                if (classIndex > -1) {
                    // only for debugging and intellij support
                    path = path.substring(0, classIndex + target.length());
                    path.replace(File.separator + File.separator, File.separator);
                    path = Paths.get(path, "client-1.0.jar").toFile().toString();

                    List<String> inputArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();

                    if(inputArgs.size() > 0) {
                        debugging1 = "VMARGS=-Xdebug";
                        // debugging2 = inputArgs.get(0);
                        /*debugging2 = debugging2.replace("-agentlib:jdwp=", "");*/
                        debugging2 = "-Xrunjdwp:" + "transport=dt_socket,address=127.0.0.1:" + 5005 + ",suspend=y,server=n";
                    }
                } else {
                    // for production
                    /*String fileName = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getName();
                    path = Paths.get(path, fileName).toFile().toString();*/
                }

                // generate the appclient file path
                String appclientPath = "glassfish/bin/";

                if(PlatformUtil.isWindows()) {
                    appclientPath += "appclient.bat";
                } else {
                    appclientPath += "appclient";
                }

                File appclientFile = new File(configuration.getGlassfishDirectory(), appclientPath);

                if(!appclientFile.exists() && !appclientFile.isFile()) {
                    System.out.println("please set a valid glassfish directory in your configuration file");
                    return;
                }

                ProcessBuilder processBuilder = new ProcessBuilder(appclientFile.toString(),
                        /*"-client", "\"" + path + "\"",*/
                        // doesn't work anymore in version 3
                        // https://github.com/javaee/glassfish/issues/13126
                        /*"-mainclass", Client.class.getCanonicalName(),*/
                        "-classpath", "\"" + path + "\"", Client.class.getCanonicalName(),
                        "-targetserver", configuration.getServerURL() + ":" + configuration.getPort(),
                        debugging1,
                        debugging2);
                processBuilder.inheritIO();
                Process process = processBuilder.start();

                Thread.sleep(15000);
                closeSplashScreen();

                while (process.isAlive()) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            closeSplashScreen();
            Client.main(args);
        }
    }

    private static void closeSplashScreen() {
        SplashScreen splashScreen = SplashScreen.getSplashScreen();

        if (splashScreen != null) {
            splashScreen.close();
        }
    }
}