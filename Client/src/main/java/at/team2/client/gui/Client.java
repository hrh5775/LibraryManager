package at.team2.client.gui;

import at.team2.client.entities.ClientMainRemote;
import at.team2.client.helper.EjbHelper;
import at.team2.client.pages.PageAction;
import at.team2.common.configuration.ConnectionInfo;
import at.team2.common.interfaces.ejb.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import at.team2.client.configuration.Configuration;
import at.team2.client.controls.sidebar.Sidebar;
import at.team2.client.controls.slider.Slider;
import at.team2.client.pages.BasePage;

import javax.ejb.EJB;
import javax.lang.model.type.NullType;
import java.net.URL;
import java.util.Locale;

public class Client extends Application {
    private final static String _version = "1.0.0";
    private static Configuration _configuration;
    @EJB
    private static AccountRemote _accountRemote;
    @EJB
    private static BookRemote _bookRemote;
    @EJB
    private static CustomerRemote _customerRemote;
    @EJB
    private static DvdRemote _dvdRemote;
    @EJB
    private static LoanRemote _loanRemote;
    @EJB
    private static at.team2.common.interfaces.ejb.MainRemote _mainRemote;
    @EJB
    private static MediaMemberRemote _mediaMemberRemote;
    @EJB
    private static MessageRemote _messageRemote;
    @EJB
    private static ReservationRemote _reservationRemote;

    static {
        _configuration = AppConfiguration.getConfiguration();

        if (_configuration == null) {
            Common.close();
        }

        // @TODO: add i18n support and then set the correct language
        Locale.setDefault(Locale.ENGLISH);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if(_configuration.getUseEjb()) {
            // https://stackoverflow.com/questions/3195365/injecting-a-static-ejb-nonsense
            ClientMainRemote mainRemote = new ClientMainRemote(_accountRemote, _bookRemote, _customerRemote, _dvdRemote,
                    _loanRemote, _mainRemote, _mediaMemberRemote, _messageRemote, _reservationRemote);

            if(mainRemote == null) {
                System.out.println("Server not available");
                return;
            }

            EjbHelper.setSession(mainRemote);
            // System.out.println("Version: " + _mainRemote.getVersion());
        }

        primaryStage.setTitle(_configuration.getAppName());
        primaryStage.getIcons().add(AppConfiguration.getAppIcon());

        // set some settings
        ConnectionInfo.hostname = _configuration.getServerURL();
        ConnectionInfo.additionalUrlExtension = _configuration.getAdditionalUrlExtension();
        ConnectionInfo.port = _configuration.getPort();

        BorderPane content = new BorderPane();

        ScrollPane mainContent = new ScrollPane();
        mainContent.setFitToHeight(true);
        mainContent.setFitToWidth(true);
        BorderPane mainContentPane = new BorderPane();

        // inserting the mainContentPane in a group enables the scroll pane to get the size of its main content
        mainContent.setContent(new Group(mainContentPane));

        // set the sidebar
        Navigation navigation = Navigation.getInstance(mainContentPane, _configuration);
        Sidebar sidebar = navigation.getNavigationBar();

        // set the split pane
        SplitPane resizeableMainContent = new SplitPane(sidebar, mainContent);
        sidebar.setMinWidth(80);
        sidebar.setMaxWidth(200);
        sidebar.setPrefWidth(80);
        resizeableMainContent.setOrientation(Orientation.HORIZONTAL);
        content.setCenter(resizeableMainContent);

        Platform.runLater(() -> {
            // set the size of the main content
            ScrollBar verticalMainContentScrollbar = (ScrollBar) mainContent.lookup(".scroll-bar:vertical");
            ScrollBar horizontalMainContentScrollbar = (ScrollBar) mainContent.lookup(".scroll-bar:horizontal");

            mainContentPane.minHeightProperty().bind(mainContent.heightProperty().subtract(verticalMainContentScrollbar.widthProperty())); // @todo: the width is not always exact
            mainContentPane.maxHeightProperty().bind(mainContent.heightProperty().subtract(verticalMainContentScrollbar.widthProperty()));
            mainContentPane.minWidthProperty().bind(mainContent.widthProperty().subtract(horizontalMainContentScrollbar.widthProperty()));
            mainContentPane.maxWidthProperty().bind(mainContent.widthProperty().subtract(horizontalMainContentScrollbar.widthProperty()));
        });

        // set the menubar
        if (_configuration.getShowMenuBar()) {
            MenuBar menuBar = new MenuBar();

            Menu menuFile = new Menu("File");

            MenuItem menuItem;

            menuItem = new MenuItem("Home");
            menuItem.setOnAction(event -> Navigation.getInstance().loadStartPage());
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            menuItem = new MenuItem("Exit");
            menuItem.setOnAction(actionEvent -> Common.closeAppWithWarning(actionEvent, primaryStage, _configuration));
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            Menu menuHelp = new Menu("Help");
            menuItem = new MenuItem("Info");
            menuItem.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText(_configuration.getAppName());
                alert.setContentText("Version: " + _version);
                alert.show();
            });

            menuHelp.getItems().add(menuItem);

            Menu menuSettings = new Menu("Settings");
            Slider slider = new Slider();
            slider.setScaleAction(new PageAction<Boolean, Scale>() {
                @Override
                public Boolean doAction(Scale value) {
                    if(value != null) {
                        BasePage currentPage = navigation.getCurrentPage();

                        if(currentPage != null) {
                            return currentPage.getTransforms().setAll(value);
                        }
                    }

                    return false;
                }
            });

            navigation.setOnLoad(new PageAction<Void, NullType>() {
                @Override
                public Void doAction(NullType value) {
                    slider.refresh();
                    return null;
                }
            });

            Label label = new Label("Zoom");
            final URL Style = ClassLoader.getSystemResource("style/stylesheetClient.css");
            label.getStylesheets().add(Style.toString());
            VBox zoomTool = new VBox(label,slider);
            zoomTool.setSpacing(10);
            zoomTool.setAlignment(Pos.CENTER);

            CustomMenuItem sliderItem = new CustomMenuItem(zoomTool);
            sliderItem.setHideOnClick(false);
            menuSettings.getItems().addAll(new SeparatorMenuItem(), sliderItem, new SeparatorMenuItem());

            menuBar.getMenus().addAll(menuFile, menuSettings, menuHelp);
            content.setTop(menuBar);
        }

        // set window
        Scene scene = new Scene(content);

        if (_configuration.getWidth() <= 0 || _configuration.getHeight() <= 0) {
            primaryStage.setMaximized(true);
        }

        if (_configuration.getWidth() > 0) {
            primaryStage.setWidth(_configuration.getWidth());
        }

        if (_configuration.getHeight() > 0) {
            primaryStage.setHeight(_configuration.getHeight());
        }

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(t -> Common.closeAppWithWarning(t, primaryStage, _configuration));
        //primaryStage.setMaximized(true); // @todo: set this only when the size of the window at maximum

        primaryStage.show();
    }
}