package at.team2.client.pages.lendMedium;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import javax.lang.model.type.NullType;

import at.team2.client.pages.BasePage;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.LoanSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.LoanRemoteObjectInf;
import at.team2.common.interfaces.MainRemoteObjectInf;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LendMedium extends BasePage<Void, NullType, NullType, NullType> {
    //todo: Add some input validation to all of this
    //todo: Test and make sure this actually works
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
    private void findBookByID() {
        try {
            MainRemoteObjectInf mainremote = RmiHelper.getSession();
            BookSmallDto foundbook = mainremote.getBookRemoteObject().getBookSmallById(Integer.parseInt(_lendIDfld.getText()));

            if (foundbook != null) {
                _lendTitellbl.setText(foundbook.getTitle());
                _currentMedia = foundbook;
                _lendIDfld.setText("");
            } else {
                _lendTitellbl.setText("Medium not found");
            }
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }
    }

    @FXML
    private void addBookToList() {
        if (_currentMedia != null) {
            _mediaList.add(_currentMedia);
            _currentMedia = null;
        }

        _lenditemsTbl.refresh();
    }

    @FXML
    private void getCustomerByID() {
        try {
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();
            int customerId = Integer.parseInt(_lendCustIdfld.getText());
            CustomerSmallDto foundCustomer = remoteObject.getCustomerRemoteObject().getCustomerSmallById(customerId);

            if (foundCustomer != null) {
                _lendFirstNamelbl.setText(foundCustomer.getFirstName());
                _lendNamelbl.setText(foundCustomer.getLastName());
                _currentCustomer = foundCustomer;
                _lendCustIdfld.setText("");
            } else {
                _lendFirstNamelbl.setText("Customer not found");
            }
        } catch(Exception e) {
            showRmiErrorMessage(e);
        }
    }

    @FXML
    private void lendBooksToCustomer() throws RemoteException, NotBoundException {
        try {
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();
            LoanRemoteObjectInf loanRemoteObjectInf = remoteObject.getLoanRemoteObject();

            LoanSmallDto loanSmallDto = new LoanSmallDto();
            loanSmallDto.setCustomerid(_currentCustomer.getId());
            LocalDate currentlocaldate = LocalDate.now();
            //todo: Get this data from the database instead of hardcoding it
            loanSmallDto.setStart(Date.valueOf(currentlocaldate));
            loanSmallDto.setEnd(Date.valueOf(currentlocaldate.plusWeeks(2)));
            loanRemoteObjectInf.addLoan(loanSmallDto);
            this.reload();
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }
    }
}