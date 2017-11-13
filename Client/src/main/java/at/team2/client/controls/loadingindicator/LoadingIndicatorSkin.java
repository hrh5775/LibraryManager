package at.team2.client.controls.loadingindicator;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Skin;

import java.util.ArrayList;
import java.util.List;

public class LoadingIndicatorSkin implements Skin<LoadingIndicator> {
    private final LoadingIndicator _loadingIndicator;
    private Group _rootNodeGroup;
    private Node _rootNode;

    public LoadingIndicatorSkin(LoadingIndicator loadingIndicator, Node rootNode) {
        _loadingIndicator = loadingIndicator;
        _rootNode = rootNode;
    }

    @Override
    public LoadingIndicator getSkinnable() {
        return _loadingIndicator;
    }

    @Override
    public Node getNode() {
        if (_rootNodeGroup == null) {
            _rootNodeGroup = new Group();
            redraw();
        }

        return _rootNode;
    }
    protected void redraw() {
        List<Node> rootChildren = new ArrayList<>();
        rootChildren.add(_rootNode);
        _rootNodeGroup.getChildren().setAll(rootChildren);
    }

    @Override
    public void dispose() {
    }
}