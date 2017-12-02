package at.team2.common.configuration;

public class ConnectionInfo {
    public static String hostname = "localhost";
    public static String additionalUrlExtension = "LibraryManager";
    public static final String url = hostname + "/" + additionalUrlExtension;
    public static int port = 1099;
    public static final int version = 1;
}