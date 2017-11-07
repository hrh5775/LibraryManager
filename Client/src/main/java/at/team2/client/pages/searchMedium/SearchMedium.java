package at.team2.client.pages.searchMedium;

import at.team2.client.pages.searchMedium.showDetail.ShowDetail;
import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class SearchMedium extends BasePage<Void, NullType, NullType, NullType> {
    @FXML
    private ListProperty<MediaSmallDto> _mediaList;
    @FXML
    private BooleanProperty _listViewVisible;
    @FXML
    private TableView _tableView;
    @FXML
    ObservableObjectValue<TableView.TableViewSelectionModel> _selectedItem;

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
            List<BookSmallDto> list = remoteObject.getBookRemoteObject().getBookSmallList();
            _mediaList.set(new ObservableListWrapper<>((List<MediaSmallDto>)(List<?>) list));
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
                showDetail(((MediaSmallDto) entity).getMediaId());
            }
        }
    }

    private void showDetail(int mediaId) {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(ShowDetail.class.getResource("showDetail.fxml"));

        try {
            // @todo: perhaps use a cache
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();
            BookDetailedDto entity = remoteObject.getBookRemoteObject().getBookDetailedById(mediaId);

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