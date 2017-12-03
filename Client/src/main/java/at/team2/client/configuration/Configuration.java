package at.team2.client.configuration;

import at.team2.common.configuration.ConnectionInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Configuration implements IConfigurable {
    // config symbols
    protected static final double _version = 1.1;
    protected static final String VERSION = "Version";
    protected static final String ROOT_DIR = "Root_Dir";
    protected static final String APP_NAME = "App_Name";
    protected static final String HEIGHT = "Height";
    protected static final String WIDTH = "Width";
    protected static final String SHOW_CLOSE_WARNING = "Show_Close_Warning";
    protected static final String SHOW_MENU_BAR = "Show_Menu_Bar";
    protected static final String SERVER_URL = "Server_URL";
    protected static final String ADDITIONAL_SERVER_URL_EXTENSION = "Additional_Server_URL_Extension";
    protected static final String PORT = "Port";
    protected static final String GLASSFISH_DIRECTORY = "Glassfish_Directory";
    protected static final String USE_EJB = "Use_EJB";
    /*protected static final String OPEN_IN_WEBBROWSER = "Open_In_Webbrowser";
    protected static final String USE_JX_BROWSER = "Use_Jx_Browser";
    protected static final String USE_WEB_CLIENT = "Use_Web_Client";*/

    private HashMap<String, Object> _keyValues;

    public Configuration() {
        _keyValues = new HashMap<>();
        setVersion(_version);
        setRootDir("./");
        setAppName("Library Manager");
        setHeight(-1);
        setWidth(-1);
        setShowCloseWarning(true);
        setShowMenuBar(true);
        setServerURL(ConnectionInfo.hostname);
        setAdditionalServerUrlExtension(ConnectionInfo.additionalUrlExtension);
        setPort(ConnectionInfo.port);
        setGlassfishDirectory("./");
        setUseEJB(true);
        /*setOpenInWebbrowser(false);
        setUseJxBrowser(false);
        setUseWebClient(false);*/
    }

    public double getVersion() {
        return (double) _keyValues.get(VERSION);
    }

    public String getRootDir() {
        return (String) _keyValues.get(ROOT_DIR);
    }

    public int getHeight() {
        return (int) _keyValues.get(HEIGHT);
    }

    public int getWidth() {
        return (int) _keyValues.get(WIDTH);
    }

    public boolean getShowCloseWarning() {
        return (boolean) _keyValues.get(SHOW_CLOSE_WARNING);
    }

    public boolean getShowMenuBar() {
        return (boolean) _keyValues.get(SHOW_MENU_BAR);
    }

    public String getAppName() {
        return (String) _keyValues.get(APP_NAME);
    }

    public String getServerURL() {
        return (String) _keyValues.get(SERVER_URL);
    }

    public String getAdditionalUrlExtension() {
        return (String) _keyValues.get(ADDITIONAL_SERVER_URL_EXTENSION);
    }

    public int getPort() {
        return (int) _keyValues.get(PORT);
    }

    public String getGlassfishDirectory() {
        return (String) _keyValues.get(GLASSFISH_DIRECTORY);
    }

    public boolean getUseEjb() {
        return (boolean) _keyValues.get(USE_EJB);
    }

    /*public boolean getOpenInWebbrowser() {
        return (boolean) _keyValues.get(OPEN_IN_WEBBROWSER);
    }

    public boolean getUseJxBrowser() {
        return (boolean) _keyValues.get(USE_JX_BROWSER);
    }

    public boolean getUseWebClient() {
        return (boolean) _keyValues.get(USE_WEB_CLIENT);
    }*/

    public void setVersion(double version) {
        _keyValues.put(VERSION, version);
    }

    public void setRootDir(String rootDir) {
        _keyValues.put(ROOT_DIR, rootDir);
    }

    public void setAppName(String appName) {
        _keyValues.put(APP_NAME, appName);
    }

    public void setHeight(int height) {
        _keyValues.put(HEIGHT, height);
    }

    public void setWidth(int width) {
        _keyValues.put(WIDTH, width);
    }

    public void setShowCloseWarning(boolean showCloseWarning) {
        _keyValues.put(SHOW_CLOSE_WARNING, showCloseWarning);
    }

    public void setShowMenuBar(boolean showMenuBar) {
        _keyValues.put(SHOW_MENU_BAR, showMenuBar);
    }

    public void setServerURL(String hostname) {
        _keyValues.put(SERVER_URL, hostname);
    }

    public void setAdditionalServerUrlExtension(String additionalUrlExtension) {
        _keyValues.put(ADDITIONAL_SERVER_URL_EXTENSION, additionalUrlExtension);
    }

    public void setPort(int port) {
        _keyValues.put(PORT, port);
    }

    public void setGlassfishDirectory(String glassfishDirectory) {
        _keyValues.put(GLASSFISH_DIRECTORY, glassfishDirectory);
    }

    public void setUseEJB(boolean useEjb) {
        _keyValues.put(USE_EJB, useEjb);
    }

    /*public void setOpenInWebbrowser(boolean openInWebbrowser) {
        _keyValues.put(OPEN_IN_WEBBROWSER, openInWebbrowser);
    }

    public void setUseJxBrowser(boolean useJxBrowser) {
        _keyValues.put(USE_JX_BROWSER, useJxBrowser);
    }

    public void setUseWebClient(boolean useWebClient) {
        _keyValues.put(USE_WEB_CLIENT, useWebClient);
    }*/

    @Override
    public Set<Map.Entry<String, Object>> getList() {
        return  _keyValues.entrySet();
    }
}