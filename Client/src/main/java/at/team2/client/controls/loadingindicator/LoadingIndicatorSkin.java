package at.team2.client.controls.loadingindicator;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Skin;

import java.util.ArrayList;
import java.util.List;

public class LoadingIndicatorSkin implements Skin<LoadingIndicator> {
    private final LoadingIndicator _loadingIndicator;
    private Group rootNode;

    public LoadingIndicatorSkin(LoadingIndicator loadingIndicator) {
        _loadingIndicator = loadingIndicator;
    }

    @Override
    public LoadingIndicator getSkinnable() {
        return _loadingIndicator;
    }

    @Override
    public Node getNode() {
        if (this.rootNode == null) {
            this.rootNode = new Group();
            redraw();
        }

        return this.rootNode;
    }
    protected void redraw() {
        List<Node> rootChildren = new ArrayList<>();
        rootChildren.add(_loadingIndicator._root);


        this.rootNode.getChildren().setAll(rootChildren);
    }

    @Override
    public void dispose() {
    }
}
