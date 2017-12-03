package at.team2.client.pages.mediadetail;

import at.team2.client.common.AccountManager;
import at.team2.client.pages.BasePage;
import at.team2.client.pages.reservation.ReservateMedium;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.detailed.MediaDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.CreatorPersonSmallDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.client.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.lang.model.type.NullType;
import java.util.LinkedList;

public class MediaDetail extends BasePage<Void, NullType, NullType, NullType> {
    @FXML
    private Label _mediaType;
    @FXML
    private Label _index;
    @FXML
    private Label _available;
    @FXML
    private Label _title;
    @FXML
    private Label _genre;
    @FXML
    private Label _published;
    @FXML
    private TextArea _description;
    @FXML
    private TextArea _publishersPersons;
    @FXML
    private TextArea _comment;
    @FXML
    private Button _btnReservation;
    @FXML
    private TableView _memberTable;
    @FXML
    private ListProperty<MediaMemberSmallDto> _memberList;

    private MediaSmallDto _tmpMedia;
    private MediaDetailedDto _media;
    private Pane _pane;

    public MediaDetail(MediaSmallDto media, Pane pane) {
        _tmpMedia = media;
        _pane = pane;

        initialize();
        initializeDialog();
    }

    @Override
    public void initialize() {
        try {
            // @todo: perhaps use a cache
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();

            if(_tmpMedia instanceof BookSmallDto) {
                _media = remoteObject.getBookRemoteObject().getBookDetailedById(((BookSmallDto) _tmpMedia).getId());
            } else if(_tmpMedia instanceof DvdSmallDto) {
                _media = remoteObject.getDvdRemoteObject().getDvdDetailedById(((DvdSmallDto) _tmpMedia).getId());
            }

            if(_media instanceof BookDetailedDto) {
                // cast the object to the specified type
            } else if(_media instanceof DvdDetailedDto) {
                // cast the object to the specified type
            }

            if(_media != null) {
                ObservableListWrapper<MediaMemberSmallDto> list = new ObservableListWrapper<>(new LinkedList<>());
                list.addAll(remoteObject.getMediaMemberRemoteObject().getMediaMemberSmallListByMediaId(_media.getMediaId()));
                _memberList.set(list);
            }
        } catch (Exception e) {
            Platform.runLater(() -> showRmiErrorMessage(e));
        }
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(MediaDetail.class.getResource("media_detail.fxml"));
        setCenter(parent);

        _memberTable.itemsProperty().bind(_memberList);
    }

    @Override
    public void load() {
    }

    @Override
    public void reload() {
    }

    @Override
    public void exit() {
        _pane.setDisable(false);
    }

    @Override
    public void dispose() {
    }

    private void initializeDialog() {
        setViewText();
        _pane.setDisable(true);
    }

    private void setViewText() {
        _mediaType.setText(_media.getMediaType().getName());
        _index.setText(_media.getBaseIndex());
        _available.setText(_media.getAvailable() ? "true" : "false");
        _title.setText(_media.getTitle());
        _genre.setText(_media.getGenre().getName());
        _published.setText(_media.getPublishedDate().toLocalDate().toString());
        _description.setText(_media.getDescription());
        _comment.setText(_media.getComment());

        if(_media.getCreatorPersons() != null) {
            StringBuilder builder = new StringBuilder();

            for(CreatorPersonSmallDto item : _media.getCreatorPersons()) {
                builder.append(item.getFirstName() + " " + item.getLastName() + "\n");
            }

            _publishersPersons.setText(builder.toString());
        }

        _btnReservation.setVisible(checkPermissionForReservation());
        _btnReservation.setDisable(_media.getAvailable());
    }

    private boolean checkPermissionForReservation(){
        //ReservationApplicationFacade (pkg Application) checks the permission for making reservations.
        //Currently only account-roles ADMIN, BIBLIOTHEKAR and AUSLEIHE have this permission.
        //Login as <staff1/password> to be able to make reservations.
        AccountDetailedDto account = AccountManager.getInstance().getAccount();
        String role = (account == null) ? null : account.getAccountRole().getKey();

        return (account != null && (role.equals("ADMIN") || role.equals("BIBLIOTHEKAR") || role.equals("AUSLEIHE"))) ? true : false;
    }

    @FXML public void showReservation(){
        startBackgroundTask(() -> Platform.runLater(() -> {
            Stage dialog = new Stage();
            ReservateMedium page = new ReservateMedium(_tmpMedia, this);
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