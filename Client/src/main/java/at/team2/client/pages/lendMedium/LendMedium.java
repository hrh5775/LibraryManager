package at.team2.client.pages.lendMedium;

import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LendMedium extends BasePage<Void,NullType,NullType,NullType> {

    @FXML
    private TextField _lendIDfld;
    @FXML
    private Label _lendTitellbl;
    @FXML
    private ListProperty<BookSmallDto> _mediaList;
    @FXML
    private TableView _lenditemsTbl;
    @FXML
    private TextField _lendCustIdfld;
    @FXML
    private Label _lendFirstNamelbl;
    @FXML
    private Label _lendNamelbl;
    private BookSmallDto _currentMedia;
    private CustomerSmallDto _currentCustomer;
    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(LendMedium.class.getResource("lendMedium.fxml"));
        setCenter(parent);
        _lenditemsTbl.itemsProperty().bind(_mediaList);
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
    private void findBookByID()
    {
        try
        {
            MainRemoteObjectInf mainremote = RmiHelper.getSession();
            BookSmallDto foundbook = mainremote.getBookRemoteObject().getBookSmallById(Integer.parseInt(_lendIDfld.getText()));
            if(foundbook != null)
            {
                _lendTitellbl.setText(foundbook.getTitle());
                _currentMedia = foundbook;
                _lendIDfld.setText("");
            }
            else
            {
                _lendTitellbl.setText("Medium not found");
            }
        } catch (Exception e)
        {
            showRmiErrorMessage(e);
        }

    }
    @FXML
    private void addBookToList()
    {
        if(_currentMedia != null)
        {
            _mediaList.add(_currentMedia);
            _currentMedia = null;
        }

    }
    @FXML
    private void getCustomerByID()
    {
        try
        {
            MainRemoteObjectInf mainremote = RmiHelper.getSession();
            CustomerSmallDto foundcustomer = mainremote.getCustomerRemoteObject().getCustomerSmallbyId(Integer.parseInt(_lendCustIdfld.getText()));
            if(foundcustomer != null)
            {
                _lendFirstNamelbl.setText(foundcustomer.get_firstName());
                _lendNamelbl.setText(foundcustomer.get_lastName());
                _currentCustomer = foundcustomer;
                _lendCustIdfld.setText("");
            }
            else
            {
                _lendFirstNamelbl.setText("Customer not found");
            }
        } catch (Exception e)
        {
            showRmiErrorMessage(e);
        }
    }
}