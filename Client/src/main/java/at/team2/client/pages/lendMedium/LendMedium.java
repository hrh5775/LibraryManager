package at.team2.client.pages.lendMedium;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.lang.model.type.NullType;

import at.team2.client.controls.numberfield.NumberField;
import at.team2.client.pages.BasePage;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.LoanRemoteObjectInf;
import at.team2.common.interfaces.MainRemoteObjectInf;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class LendMedium extends BasePage<Void, NullType, NullType, NullType> {
    //todo: Add some input validation to all of this
    //todo: Test and make sure this actually works
    @FXML
    private NumberField _mediaIdNumberField;
    @FXML
    private Label _lendTitellbl;
    @FXML
    private ListProperty<MediaSmallDto> _mediaList;
    @FXML
    private TableView _lenditemsTbl;
    @FXML
    private NumberField _customerIdNumberField;
    @FXML
    private Label _lendFirstNamelbl;
    @FXML
    private Label _lendNamelbl;
    @FXML
    private Button _lendConfirmButton;
    @FXML
    private SimpleBooleanProperty _lendConfirmDisabled;
    @FXML
    private Button _lendAddButton;
    @FXML
    private SimpleBooleanProperty _lendAddButtonDisabled;
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
        _lendConfirmButton.disableProperty().bind(_lendConfirmDisabled);
        _lendAddButton.disableProperty().bind(_lendAddButtonDisabled);

        ObservableList<MediaSmallDto> list = FXCollections.observableArrayList();
        _mediaList.set(list);
        activateLendConfirm();
        activateLendAdd();
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
    private void findBookByID() {
        try {
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();
            String tmp = _mediaIdNumberField.getText();

            if(tmp != null) {
                BookSmallDto entity = remoteObject.getBookRemoteObject().getBookSmallById(Integer.parseInt(tmp));

                if (entity != null) {
                    _lendTitellbl.setText(entity.getTitle());
                    _currentMedia = entity;
                    _mediaIdNumberField.setText("0");
                } else {
                    _lendTitellbl.setText("Medium not found");
                }
            }
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }

        activateLendAdd();
    }

    @FXML
    private void addBookToList() {
        if (_currentMedia != null && !_mediaList.contains(_currentMedia)) {
            ObservableList list = _mediaList.get();
            list.add(_currentMedia);
            _mediaList.set(list);
            _currentMedia = null;
            _lendTitellbl.setText("");

            _lenditemsTbl.refresh();
            activateLendConfirm();
            activateLendAdd();
        }
    }

    @FXML
    private void getCustomerByID() {
        try {
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();
            String tmp = _customerIdNumberField.getText();

            if(tmp != null) {
                int customerId = Integer.parseInt(tmp);
                CustomerSmallDto foundCustomer = remoteObject.getCustomerRemoteObject().getCustomerSmallById(customerId);

                if (foundCustomer != null) {
                    _lendFirstNamelbl.setText(foundCustomer.getFirstName());
                    _lendNamelbl.setText(foundCustomer.getLastName());
                    _currentCustomer = foundCustomer;
                    _customerIdNumberField.setText("0");
                } else {
                    _lendFirstNamelbl.setText("Customer not found");
                }
            }
        } catch(Exception e) {
            showRmiErrorMessage(e);
        }

        activateLendConfirm();
    }

    @FXML
    private void lendBooksToCustomer() throws RemoteException, NotBoundException {
        if(_mediaList.size() > 0 && _currentCustomer != null) {
            try {
                MainRemoteObjectInf remoteObject = RmiHelper.getSession();
                LoanRemoteObjectInf loanRemoteObject = remoteObject.getLoanRemoteObject();

                for(MediaSmallDto item : _mediaList.get()) {
                    if(loanRemoteObject.loanMedia(item, _currentCustomer) <= 0) {
                        showErrorMessage("Loan failed for", item.getTitle());
                    }
                }

                this.reload();

                showSuccessMessage("All loans added", "");
            } catch (Exception e) {
                showRmiErrorMessage(e);
            }
        }
    }

    private void activateLendConfirm() {
        _lendConfirmDisabled.setValue(_mediaList.size() == 0 || _currentCustomer == null);
    }

    private void activateLendAdd() {
        _lendAddButtonDisabled.setValue(_currentMedia == null);
    }
}