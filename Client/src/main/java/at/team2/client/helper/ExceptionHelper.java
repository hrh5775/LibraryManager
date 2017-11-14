package at.team2.client.helper;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHelper {
    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}