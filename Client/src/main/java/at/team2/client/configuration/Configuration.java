package at.team2.client.configuration;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Configuration implements IConfigurable {
    // config symbols
    protected static final double _version = 1.1;
    protected static final String VERSION = "Version";
    protected static final String ROOT_DIR = "Root_Dir";
    protected static final String ROOT_URI = "ROOT_URI";
    protected static final String APP_NAME = "App_Name";
    protected static final String HEIGHT = "Height";
    protected static final String WIDTH = "Width";
    protected static final String SHOW_CLOSE_WARNING = "Show_Close_Warning";
    protected static final String SHOW_MENU_BAR = "Show_Menu_Bar";
    protected static final String OPEN_IN_WEBBROWSER = "Open_In_Webbrowser";
    protected static final String USE_JX_BROWSER = "Use_Jx_Browser";
    protected static final String USE_WEB_CLIENT = "Use_Web_Client";

    private HashMap<String, Object> _keyValues;

    public Configuration() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        _keyValues = new HashMap<>();
        setVersion(_version);
        setRootDir("./");
        setRootURI("http://localhost:8080");
        setAppName("LibraryManager");
        setHeight(-1);
        setWidth(-1);
        setShowCloseWarning(true);
        setShowMenuBar(true);
        setOpenInWebbrowser(false);
        setUseJxBrowser(false);
        setUseWebClient(false);
    }

    public String getRootDir() {
        return (String) _keyValues.get(ROOT_DIR);
    }

    public String getRootURI() {
        return (String) _keyValues.get(ROOT_URI);
    }

    public String getAppName() {
        return (String) _keyValues.get(APP_NAME);
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

    public boolean getOpenInWebbrowser() {
        return (boolean) _keyValues.get(OPEN_IN_WEBBROWSER);
    }

    public boolean getUseJxBrowser() {
        return (boolean) _keyValues.get(USE_JX_BROWSER);
    }

    public double getVersion() {
        return (double) _keyValues.get(VERSION);
    }

    public boolean getUseWebClient() {
        return (boolean) _keyValues.get(USE_WEB_CLIENT);
    }

    public void setRootDir(String rootDir) {
        _keyValues.put(ROOT_DIR, rootDir);
    }

    public void setRootURI(String rootURI) {
        _keyValues.put(ROOT_URI, rootURI);
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

    public void setOpenInWebbrowser(boolean openInWebbrowser) {
        _keyValues.put(OPEN_IN_WEBBROWSER, openInWebbrowser);
    }

    public void setUseJxBrowser(boolean useJxBrowser) {
        _keyValues.put(USE_JX_BROWSER, useJxBrowser);
    }

    public void setVersion(double version) {
        _keyValues.put(VERSION, version);
    }

    public void setUseWebClient(boolean useWebClient) {
        _keyValues.put(USE_WEB_CLIENT, useWebClient);
    }

    @Override
    public Set<Map.Entry<String, Object>> getList() {
        return  _keyValues.entrySet();
    }
}
