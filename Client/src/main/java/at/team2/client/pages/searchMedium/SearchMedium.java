package at.team2.client.pages.searchMedium;

import at.team2.client.pages.searchMedium.showDetail.ShowDetail;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.scene.control.ImageViewButton;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(SearchMedium.class.getResource("searchMedium.fxml"));
        setCenter(parent);
        _tableView.visibleProperty().bind(_listViewVisible);
        _tableView.itemsProperty().bind(_mediaList);



        _tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent ee) {
                MediaSmallDto msdto = (MediaSmallDto) _tableView.getSelectionModel().getSelectedItem();
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

        //TODO Image in und aus der DB bringen
        //imgCover.setImage( msdto.);
        try {
            URI coverURI = ClassLoader.getSystemResource( "Buecherwurm.jpg").toURI();
            Image img = new Image( coverURI.toString() );
            imgCover.setImage( img );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

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
            List<BookSmallDto> list = remoteObject.getBookRemoteObject().getBookSmallList(_searchField.getText());
            _mediaList.set(new ObservableListWrapper<>((List<MediaSmallDto>)(List<?>) list));
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }

        _listViewVisible.setValue(true);
    }




    public static void main( String[] args ) throws MalformedURLException, URISyntaxException {

        URL url = SearchMedium.class.getResource("./showDetail/showDetail.fxml");
        URL url1 = SearchMedium.class.getResource("../../../resources/Buecherwurm.jpg");

        URI url2 = ClassLoader.getSystemResource( "Buecherwurm.jpg").toURI();
        File fl = new File( url2.getPath() );
        System.out.println( fl.exists() + "url: " + url2.toString());
    }

}