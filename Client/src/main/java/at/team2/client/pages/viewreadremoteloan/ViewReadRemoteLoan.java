package at.team2.client.pages.viewreadremoteloan;

import javax.lang.model.type.NullType;

import at.team2.client.pages.BasePage;
import javafx.scene.Parent;

public class ViewReadRemoteLoan extends BasePage<Void,NullType,NullType,NullType>
{
    @Override
    public void initialize()
    {
        Parent parent = loadView(ViewReadRemoteLoan.class.getResource("viewreadremoteloan.fxml"));
        setCenter(parent);
    }

    @Override
    public void initializeView()
    {

    }

    @Override
    public void load()
    {

    }

    @Override
    public void reload()
    {

    }

    @Override
    public void exit()
    {

    }

    @Override
    public void dispose()
    {

    }
}
