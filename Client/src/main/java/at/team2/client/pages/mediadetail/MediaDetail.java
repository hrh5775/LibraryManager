package at.team2.client.pages.mediadetail;

import at.team2.client.pages.BasePage;
import at.team2.client.pages.reservation.ReservateMedium;
import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.detailed.MediaDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.CreatorPersonSmallDto;
import at.team2.common.dto.small.DvdSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.lang.model.type.NullType;

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
        } catch (Exception e) {
            Platform.runLater(() -> showRmiErrorMessage(e));
        }
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(MediaDetail.class.getResource("media_detail.fxml"));
        setCenter(parent);
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

    public void initializeDialog() {
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
    }

    @FXML public void showReservation(){
        System.out.println( "Reservation clicked");

        startBackgroundTask(() -> Platform.runLater(() -> {
            Stage dialog = new Stage();
            ReservateMedium page = new ReservateMedium( _tmpMedia, this);
            Scene scene = new Scene( page );
            dialog.setScene( scene );
            try {
                dialog.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            page.exit();
        }));
    }
}