package at.team2.client.controls.loadingindicator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadingIndicator extends Control {
    @FXML
    private ImageView _imageView;

    private Node _rootNode;

    public LoadingIndicator() {
        super();
        getStyleClass().add("loading-indicator");
        try {
            FXMLLoader loader = new FXMLLoader(LoadingIndicator.class.getResource("loading_indicator.fxml"));
            loader.setController(this);
            _rootNode = loader.load();
        } catch (Exception e) {
            System.out.println(e);
        }

        _imageView.setImage(new Image("/spinner.gif"));
    }

    @Override
    public String getUserAgentStylesheet() {
        return LoadingIndicator.class.getResource("loading_indicator.css").toExternalForm();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LoadingIndicatorSkin(this, _rootNode);
    }
}
