package at.team2.client.gui;

//import com.teamdev.jxbrowser.chromium.Browser;
//import com.teamdev.jxbrowser.chromium.BrowserCore;
//import com.teamdev.jxbrowser.chromium.internal.Environment;
//import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;

public class Webbrowser {
    private static boolean _initialized = false;

    public static Parent getBrowser(URL initialURL, boolean useJxBrowser) {
        /*if(useJxBrowser) {
            if(!_initialized) {
                if (Environment.isMac()) {
                    BrowserCore.initialize();
                    _initialized = true;
                }
            }
        }*/

        Parent result;

        /*if(useJxBrowser) {
            Browser browser = new Browser();
            BrowserView browserView = new BrowserView(browser);

            browser.loadURL(initialURL.toExternalForm());
            result = browserView;
        } else {*/
            WebView _browserView = new WebView();
            _browserView.getEngine().load(initialURL.toExternalForm());
            addListeners(_browserView.getEngine());

            result = _browserView;
        /*}*/

        return result;
    }

    private static void addListeners(WebEngine engine) {
        // @TODO: add feature
        /*engine.setPrintHandler(printJob -> {
            PrintSettings printSettings = printJob.getPrintSettings();
            printSettings.setLandscape(true);
            printSettings.setPrintBackgrounds(false);
            //printSettings.setPrintToPDF(true);

            String homeDir = System.getProperty("user.home");
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM_dd_yyyy_HH_mm_ss");
            String dateTime = LocalDateTime.now().format(format);
            printSettings.setPDFFilePath(Paths.get(homeDir, "events_" + dateTime + ".pdf").toString());

            printJob.addPrintJobListener(event -> {
            });

            PrintDialogModel printDialogModel = new PrintDialogModel(printSettings);
            PrintDialog printDialog = new PrintDialog(null, printDialogModel); // set appropriate owner and size of the window
            printDialog.setVisible(true);



            return PrintStatus.CONTINUE;
        });*/
    }
}
