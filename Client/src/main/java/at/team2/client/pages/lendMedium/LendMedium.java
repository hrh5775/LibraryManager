package at.team2.client.pages.lendMedium;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.lang.model.type.NullType;

import at.team2.client.controls.numberfield.NumberField;
import at.team2.client.pages.BasePage;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
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
    @FXML
    private NumberField _mediaIdNumberField;
    @FXML
    private Label _lendTitellbl;
    @FXML
    private ListProperty<MediaMemberSmallDto> _mediaList;
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
    private MediaMemberSmallDto _currentMedia;
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

        ObservableList<MediaMemberSmallDto> list = FXCollections.observableArrayList();
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
    private void findMediaMemberById() {
        try {
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();
            String tmp = _mediaIdNumberField.getText();

            if(tmp != null) {
                MediaMemberSmallDto entity = remoteObject.getMediaMemberRemoteObject().getMediaMemberSmallById(Integer.parseInt(tmp));

                if (entity != null) {
                    _lendTitellbl.setText(entity.getMedia().getTitle());
                    _currentMedia = entity;
                    _mediaIdNumberField.setText("0");
                } else {
                    showErrorMessage("Medium not found", "");
                }
            }
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }

        activateLendAdd();
    }

    @FXML
    private void addMediaMemberToList() {
        if (_currentMedia != null) {
            ObservableList<MediaMemberSmallDto> list = _mediaList.get();

            for(MediaMemberSmallDto item : list) {
                if(item.getId() == _currentMedia.getId()) {
                    return;
                }
            }

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
    private void getCustomerById() {
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
                    showErrorMessage("Customer not found", "");
                }
            }
        } catch(Exception e) {
            showRmiErrorMessage(e);
        }

        activateLendConfirm();
    }

    @FXML
    private void lendMediaMembersToCustomer() throws RemoteException, NotBoundException {
        if(_mediaList.size() > 0 && _currentCustomer != null) {
            try {
                MainRemoteObjectInf remoteObject = RmiHelper.getSession();
                LoanRemoteObjectInf loanRemoteObject = remoteObject.getLoanRemoteObject();

                int count = 0;

                for(MediaMemberSmallDto item : _mediaList.get()) {
                    if(loanRemoteObject.loanMediaMember(item, _currentCustomer) <= 0) {
                        showErrorMessage("Loan failed for", item.getMedia().getTitle());
                    } else {
                        count++;
                    }
                }

                ObservableList list = _mediaList.get();
                list.clear();
                _mediaList.set(list);
                this.reload();

                if(count > 0) {
                    showSuccessMessage("All loans added", "");
                }
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