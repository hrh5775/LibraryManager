package at.team2.client.pages.searchMedium;

import at.team2.client.pages.searchMedium.showDetail.ShowDetail;
import at.team2.common.dto.detailed.MediaDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SearchMedium extends BasePage<Void, NullType, NullType, NullType> {
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

    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(SearchMedium.class.getResource("searchMedium.fxml"));
        setCenter(parent);
        _listViewVisible.setValue(false);
        _tableView.visibleProperty().bind(_listViewVisible);
        _tableView.itemsProperty().bind(_mediaList);

        _dvdChecked.selectedProperty().bindBidirectional(_isDvdChecked);
        _bookChecked.selectedProperty().bindBidirectional(_isBookChecked);

        _isDvdChecked.addListener((observable, oldValue, newValue) -> search());
        _isBookChecked.addListener((observable, oldValue, newValue) -> search());
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
        try {
            // @todo: perhaps use a cache
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();

            List<MediaSmallDto> list = new LinkedList<>();

            if(_bookChecked.isSelected() || (_dvdChecked.isSelected() && _bookChecked.isSelected()) || (!_dvdChecked.isSelected() && !_bookChecked.isSelected())) {
                list.addAll(remoteObject.getBookRemoteObject().getBookSmallList(_searchField.getText()));
            }

            if(_dvdChecked.isSelected() || (_dvdChecked.isSelected() && _bookChecked.isSelected()) || (!_dvdChecked.isSelected() && !_bookChecked.isSelected())) {
                list.addAll(remoteObject.getDvdRemoteObject().getDvdSmallList(_searchField.getText())); //List<MediaSmallDto>)(List<?>)
            }

            _mediaList.set(new ObservableListWrapper<>(list));
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }

        _listViewVisible.setValue(true);
    }

    @FXML
    private void mediaItemClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            Object entity = _tableView.getSelectionModel().getSelectedItem();

            if(entity != null) {
                int id = -1;

                if(entity instanceof BookSmallDto) {
                    id = ((BookSmallDto) entity).getId();
                } else if(entity instanceof DvdSmallDto) {
                    id = ((DvdSmallDto) entity).getId();
                }

                showDetail(id);
            }
        }
    }

    private void showDetail(int id) {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(ShowDetail.class.getResource("showDetail.fxml"));

        try {
            // @todo: perhaps use a cache
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();
            MediaDetailedDto entity = remoteObject.getBookRemoteObject().getBookDetailedById(id);

            if(entity == null) {
                entity = remoteObject.getDvdRemoteObject().getDvdDetailedById(id);
            }

            _listViewVisible.setValue(true);
            loader.setController(new ShowDetail(entity));

            try {
                dialog.setScene(new Scene(loader.load()));
                dialog.setAlwaysOnTop(true);
                dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }
    }
}