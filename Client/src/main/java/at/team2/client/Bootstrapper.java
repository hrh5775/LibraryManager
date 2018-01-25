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
import java.util.LinkedList;
import java.util.List;

public class Bootstrapper {
    public static void main(String[] args) {
        Configuration configuration = AppConfiguration.getConfiguration();

        File lockFile = Paths.get(AppConfiguration.getPath().toString(), ".lock").toFile();
        boolean isAppRunning = false;

        if (lockFile.exists()) {
            isAppRunning = true;
        } else {
            try {
                lockFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AppConfiguration.saveConfiguration(null, configuration);
        }

        // delete the lockfile on vm termination
        lockFile.deleteOnExit();

        if(configuration.getUseEjb() && !isAppRunning) {
            try {
                List<String> processArgs = new LinkedList<>();

                String path = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
                String target = "target";
                String debugPathInfo = target + File.separator + "classes";

                int classIndex = path.lastIndexOf(debugPathInfo);
                String debugging1 = "";
                String debugging2 = "";


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


                if (classIndex > -1) {
                    // only for debugging and intellij support
                    path = path.substring(0, classIndex + target.length());
                    path.replace(File.separator + File.separator, File.separator);
                    path = Paths.get(path, "client-1.0.jar").toFile().toString();

                    List<String> inputArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();

                    if(inputArgs.size() > 0) {
                        debugging1 = "VMARGS=-Xdebug";
                        // debugging2 = inputArgs.get(0);
                        // debugging2 = debugging2.replace("-agentlib:jdwp=", "");
                        debugging2 = "-Xrunjdwp:" + "transport=dt_socket,address=127.0.0.1:" + 5005 + ",suspend=y,server=n";
                    }

                    processArgs.add(appclientFile.toString());
                    processArgs.add("-client");
                    processArgs.add("\"" + path + "\"");
                    processArgs.add("-targetserver");
                    processArgs.add(configuration.getServerURL() + ":" + configuration.getPort());
                    processArgs.add(debugging1);
                    processArgs.add(debugging2);
                } else {
                    // for production

                    processArgs.add(appclientFile.toString());
                    processArgs.add("-client");
                    processArgs.add("\"" + path + "\"");
                    processArgs.add("-targetserver");
                    processArgs.add(configuration.getServerURL() + ":" + configuration.getPort());
                }

                ProcessBuilder processBuilder = new ProcessBuilder(processArgs);
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