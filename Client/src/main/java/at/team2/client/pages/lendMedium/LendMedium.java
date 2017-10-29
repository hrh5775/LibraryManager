package at.team2.client.pages.lendMedium;

import javax.lang.model.type.NullType;

import at.team2.client.pages.BasePage;
import javafx.scene.Parent;

public class LendMedium extends BasePage<Void,NullType,NullType,NullType>
{
    @Override
    public void initialize()
    {

    }

    @Override
    public void initializeView()
    {
        Parent parent = loadView(LendMedium.class.getResource("lendMedium.fxml"));
        setCenter(parent);
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
