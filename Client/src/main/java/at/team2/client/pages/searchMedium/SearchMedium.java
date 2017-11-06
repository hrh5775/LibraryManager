package at.team2.client.pages.searchMedium;

import at.team2.client.pages.searchMedium.showDetail.ShowDetail;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.scene.control.ImageViewButton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
        //TextField rehearsalConductor = (TextField) stage.getScene().lookup("#rehearsalConductor");
        _listViewVisible.setValue(false);
        _tableView.visibleProperty().bind(_listViewVisible);
        _tableView.itemsProperty().bind(_mediaList);

        _tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent ee) {

                MediaSmallDto msdto = (MediaSmallDto) _tableView.getSelectionModel().getSelectedItem();
                System.out.println( "BookSmallDto Index:" + msdto.getBaseIndex() );

            showDetail( msdto );

            }
        });
    }

    public void showDetail( MediaSmallDto msdto ) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = SearchMedium.class.getResource("./showDetail/showDetail.fxml");
        fxmlLoader.setLocation( url );
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Media Detail");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        ImageView imgCover = ( ImageView ) stage.getScene().lookup( "#imgCover");
        Label txtMediaType = ( Label ) stage.getScene().lookup("#txtMediaType");
        Label txtIndex = ( Label ) stage.getScene().lookup("#txtIndex");
        Label txtAvailable = ( Label ) stage.getScene().lookup("#txtAvailable");
        Label txtTitle = ( Label ) stage.getScene().lookup("#txtTitle");
        Label txtGenre = ( Label ) stage.getScene().lookup("#txtGenre");
        Label txtPublished = ( Label ) stage.getScene().lookup("#txtPublished");
        TextArea txtfldDescription = ( TextArea ) stage.getScene().lookup("#txtfldDescription");
        TextArea txtAreaPublisherPersons = ( TextArea ) stage.getScene().lookup( "#txtAreaPublisherPersons");

        //imgCover.setImage( msdto.);
        txtMediaType.setText( msdto.getMediaType().getName() );
        txtIndex.setText( msdto.getBaseIndex() );
        txtAvailable.setText( (msdto.getAvailable())? "Yes" : "No" );
        txtTitle.setText( msdto.getTitle() );
        //txtGenre.setText( msdto.g);
        txtPublished.setText( msdto.getPublishedDate().toString() );
        txtfldDescription.setText( msdto.getDescription() );
        //txtAreaPublisherPersons

        ShowDetail.stage = stage;
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

    public static void main( String[] args ){


        URL url = SearchMedium.class.getResource("./showDetail/showDetail.fxml");
        File fl = new File( url.getPath() );
        System.out.println( fl.exists() );
    }
}