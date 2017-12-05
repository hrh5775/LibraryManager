package at.team2.client.pages.showremoteloan;

import at.team2.client.pages.BasePage;
import javafx.scene.Parent;

import javax.lang.model.type.NullType;

public class ShowRemoteLoan extends BasePage<Void,NullType,NullType,NullType> {
    @Override
    public void initialize() {
        Parent parent = loadView(ShowRemoteLoan.class.getResource("show_remote_loan.fxml"));
        setCenter(parent);
    }

    @Override
    public void initializeView() {
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
}