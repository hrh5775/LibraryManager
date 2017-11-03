package at.team2.client.pages.searchMedium;

import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.dto.small.PublisherSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;
import javafx.scene.control.TableView;
import java.util.List;

public class SearchMedium extends BasePage<Void, NullType, NullType, NullType> {
    @FXML
    private ListProperty<MediaSmallDto> _mediaList;
    @FXML
    private BooleanProperty _listViewVisible;
    @FXML
    private TableView _tableView;

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
        try {
            // @todo: perhaps use a cache
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();
            List<BookSmallDto> list = remoteObject.getBookRemoteObject().getBookSmallList();
            _mediaList.set(new ObservableListWrapper<>((List<MediaSmallDto>)(List<?>) list));
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }
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
        _listViewVisible.setValue(true);
    }
}