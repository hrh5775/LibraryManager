package at.team2.client.gui;

import at.team2.client.pages.PageAction;
import javafx.application.Application;
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
import at.team2.client.singletons.HomeScreenSingleton;
import javax.lang.model.type.NullType;
import java.net.URL;
import java.util.Locale;

public class Client extends Application {
    private final static String _version = "1.0.0";
    private static Configuration _configuration;

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
        primaryStage.setTitle(_configuration.getAppName());
        primaryStage.getIcons().add(AppConfiguration.getAppIcon());

        BorderPane content = new BorderPane();

        ScrollPane mainContent = new ScrollPane();
        mainContent.setFitToHeight(true);
        mainContent.setFitToWidth(true);
        BorderPane mainContentPane = new BorderPane();
        mainContentPane.minHeightProperty().bind(mainContent.minHeightProperty());
        mainContentPane.maxHeightProperty().bind(mainContent.heightProperty());
        mainContentPane.minWidthProperty().bind(mainContent.minWidthProperty());
        mainContentPane.maxWidthProperty().bind(mainContent.widthProperty());

        // inserting the mainContentPane in a group enables the scroll pane to get the size of its main content
        mainContent.setContent(new Group(mainContentPane));

        // set the sidebar
        NavigationBar navigationBar = new NavigationBar(mainContentPane, _configuration);
        Sidebar sidebar = navigationBar.getNavigationBar();

        // set the split pane
        SplitPane resizeableMainContent = new SplitPane(sidebar, mainContent);
        sidebar.setMinWidth(80);
        sidebar.setMaxWidth(200);
        sidebar.setPrefWidth(80);
        resizeableMainContent.setOrientation(Orientation.HORIZONTAL);
        content.setCenter(resizeableMainContent);

        // set the menubar
        if (_configuration.getShowMenuBar()) {
            MenuBar menuBar = new MenuBar();

            Menu menuFile = new Menu("File");

            MenuItem menuItem;

            menuItem = new MenuItem("Home");
            menuItem.setOnAction(event -> mainContent.setContent(HomeScreenSingleton.getInstance()));
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
                        BasePage currentPage = navigationBar.getCurrentPage();

                        if(currentPage != null) {
                            return currentPage.getTransforms().setAll(value);
                        }
                    }

                    return false;
                }
            });

            navigationBar.setOnLoad(new PageAction<Void, NullType>() {
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
        primaryStage.setMaximized(true);

        primaryStage.show();
    }
}