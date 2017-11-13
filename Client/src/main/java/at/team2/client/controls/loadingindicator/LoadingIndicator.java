package at.team2.client.controls.loadingindicator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class LoadingIndicator extends Control {
    @FXML
    private ImageView _imageView;
    @FXML
    private Button _cancelButton;

    private Node _rootNode;

    public final ObjectProperty<EventHandler<ActionEvent>> onCancelProperty() { return _onCancel; }
    public final void setOnCancel(EventHandler<ActionEvent> value) { onCancelProperty().set(value); }
    public final EventHandler<ActionEvent> getOnCancel() { return onCancelProperty().get(); }
    private ObjectProperty<EventHandler<ActionEvent>> _onCancel = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override protected void invalidated() {
            setEventHandler(ActionEvent.ACTION, get());
        }

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "onCancel";
        }
    };

    public final ObjectProperty<Image> imageProperty() { return _image; }
    public final void setImage(Image value) { imageProperty().set(value); }
    public final Image getImage() { return imageProperty().get(); }
    private ObjectProperty<Image> _image = new ObjectPropertyBase<Image>() {
        @Override protected void invalidated() {
        }

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "image";
        }
    };

    public LoadingIndicator() {
        super();
        getStyleClass().add("loading-indicator");

        FXMLLoader loader = new FXMLLoader(LoadingIndicator.class.getResource("loading_indicator.fxml"));
        loader.setController(this);

        try {
            _rootNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        _imageView.imageProperty().bind(imageProperty());

        if(imageProperty().getValue() == null) {
            imageProperty().setValue(new Image("/spinner.gif"));
        }

        _cancelButton.visibleProperty().bind(imageProperty().isNotNull());
    }

    @Override
    public String getUserAgentStylesheet() {
        return LoadingIndicator.class.getResource("loading_indicator.css").toExternalForm();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LoadingIndicatorSkin(this, _rootNode);
    }

    @FXML
    public void cancel() {
        _onCancel.getValue().handle(null);
    }
}
