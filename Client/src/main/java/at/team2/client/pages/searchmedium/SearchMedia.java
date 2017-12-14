package at.team2.client.pages.searchmedium;

import at.team2.client.controls.loadingindicator.LoadingIndicator;
import at.team2.client.entities.session.SessionWrapperObject;
import at.team2.client.helper.SessionHelper;
import at.team2.client.pages.mediadetail.MediaDetail;
import at.team2.common.dto.small.MediaSmallDto;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.LinkedList;
import java.util.List;

public class SearchMedia extends BasePage<Void, NullType, NullType, NullType> {
    @FXML
    private LoadingIndicator _loadingIndicator;
    @FXML
    private Pane _mainPanel;
    @FXML
    private Pane _tablePane;
    @FXML
    private Button _searchButton;
    @FXML
    private BooleanProperty _isLoading;
    @FXML
    private ListProperty<MediaSmallDto> _mediaList;
    @FXML
    private BooleanProperty _listViewVisible;
    @FXML
    private TableView _tableView;
    @FXML
    private TextField _searchField;
    @FXML
    private CheckBox _bookChecked;
    @FXML
    private CheckBox _dvdChecked;
    @FXML
    private SimpleBooleanProperty _isDvdChecked;
    @FXML
    private SimpleBooleanProperty _isBookChecked;

    private Thread _searchTask;

    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(SearchMedia.class.getResource("search_media.fxml"));
        setCenter(parent);

        _isLoading.setValue(false);
        _loadingIndicator.visibleProperty().bind(_isLoading);

        _listViewVisible.setValue(false);
        _tableView.visibleProperty().bind(_listViewVisible.and(_isLoading.not()));
        _tableView.itemsProperty().bind(_mediaList);

        _dvdChecked.selectedProperty().bindBidirectional(_isDvdChecked);
        _bookChecked.selectedProperty().bindBidirectional(_isBookChecked);

        _isDvdChecked.addListener((observable, oldValue, newValue) -> search());
        _isBookChecked.addListener((observable, oldValue, newValue) -> search());

        _searchButton.disableProperty().bind(_searchField.textProperty().isEmpty());

        _dvdChecked.disableProperty().bind(_searchButton.disableProperty());
        _bookChecked.disableProperty().bind(_searchButton.disableProperty());
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

    @FXML
    private void search() {
        if(_searchTask == null && !_searchField.getText().isEmpty()) {
            _isLoading.setValue(true);
            List<MediaSmallDto> list = new LinkedList<>();

            _searchTask = startBackgroundTask(() -> {
                try {
                    SessionWrapperObject remoteObject = SessionHelper.getSession();

                    if(_bookChecked.isSelected() || (!_dvdChecked.isSelected() && !_bookChecked.isSelected())) {
                        list.addAll(remoteObject.getBookRemoteObject().getBookSmallList(_searchField.getText()));
                    }

                    if(_dvdChecked.isSelected() || (!_dvdChecked.isSelected() && !_bookChecked.isSelected())) {
                        list.addAll(remoteObject.getDvdRemoteObject().getDvdSmallList(_searchField.getText())); //List<MediaSmallDto>)(List<?>)
                    }
                } catch (Exception e) {
                    Platform.runLater(() -> showRmiErrorMessage(e));
                } finally {
                    Platform.runLater(() -> _mediaList.set(new ObservableListWrapper<>(list)));;
                    _searchTask = null;

                    Platform.runLater(() -> {
                        _listViewVisible.setValue(true);
                        _isLoading.setValue(false);
                    });
                }
            });
        }
    }

    @FXML
    private void mediaItemClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            Object entity = _tableView.getSelectionModel().getSelectedItem();

            if(entity != null) {
                showDetail((MediaSmallDto) entity);
            }
        }
    }

    @FXML
    private void cancel() {
        try {
            stopTask(_searchTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            _isLoading.setValue(false);
        }
    }

    private void showDetail(MediaSmallDto media) {
        startBackgroundTask(() -> Platform.runLater(() -> {
            Stage dialog = new Stage();
            MediaDetail page = new MediaDetail(media, this);
            Scene scene = new Scene(page);
            dialog.setScene(scene);

            try {
                dialog.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }

            page.exit();
        }));
    }
}