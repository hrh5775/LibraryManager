package at.team2.client.pages.searchMedium.showDetail;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class ShowDetail {

    @FXML
    private Label txtMediaType;
    @FXML
    private Label txtIndex;
    @FXML
    private Label txtAvailable;
    @FXML
    private Label txtTitle;
    @FXML
    private Label txtGenre;
    @FXML
    private Label txtPublished;
    @FXML
    private TextArea txtfldDescription;
    @FXML
    private TextArea txtAreaPublisherPersons;

    public static Stage stage;

    @FXML public void initialize() {
        stage = new Stage();

    }
}
