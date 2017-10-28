package at.team2.client.pages.searchMedium;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class SearchMedium extends BasePage<Void, NullType, NullType, NullType> {
    @FXML
    private ListView<String> listView;
    @FXML
    private Button searchButton;


    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(SearchMedium.class.getResource("searchMedium.fxml"));
        setCenter(parent);
        listView.setVisible(false);

        searchButton.setOnAction((event) -> {
            // Button was clicked, do something...
            listView.setVisible(true);
        });
    }

    @Override
    public void load() {
    }

    @Override
    public void reload() {

    }

    @Override
    public void exit() {
    }

    @Override
    public void dispose() {
    }
}