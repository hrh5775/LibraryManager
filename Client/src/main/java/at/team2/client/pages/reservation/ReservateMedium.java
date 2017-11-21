package at.team2.client.pages.reservation;

import at.team2.client.common.AccountManager;
import at.team2.client.controls.loadingindicator.LoadingIndicator;
import at.team2.client.controls.numberfield.NumberField;
import at.team2.client.pages.BasePage;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import at.team2.common.interfaces.ReservationRemoteObjectInf;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javax.lang.model.type.NullType;
import java.sql.Date;
import java.util.Calendar;

public class ReservateMedium extends BasePage<Void, NullType, NullType, NullType> {
    @FXML
    private NumberField _txtCustomerID;
    @FXML
    private Label _lbFirstname;
    @FXML
    private Label _lbLastname;
    @FXML
    private Label _lbMediaID;
    @FXML
    private Label _lbMediaTitle;
    @FXML
    private Button _btnReservate;
    @FXML
    private LoadingIndicator _loadingIndicator;
    @FXML
    private Pane _pane;
    @FXML
    private BooleanProperty _isLoading;

    private CustomerSmallDto _customer;
    private MediaSmallDto _media;

    private Thread _reservateTask;

    public ReservateMedium( MediaSmallDto media , Pane pane ){
        _media = media;
        _customer = new CustomerSmallDto();
        _pane = pane;
        initializeMedia();
        initializeCustomer();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(ReservateMedium.class.getResource("reservate_medium.fxml"));
        setCenter(parent);

        _isLoading.setValue(false);
        _loadingIndicator.visibleProperty().bind(_isLoading);
    }

    private void initializeMedia(){
        _lbMediaID.setText("" + _media.getMediaId());
        _lbMediaTitle.setText(_media.getTitle());
        _pane.setDisable(true);
    }

    private void initializeCustomer(){
        _lbFirstname.setText("");
        _lbLastname.setText("");
        _btnReservate.setDisable(true);
        _txtCustomerID.setText("0");
    }

    @FXML
    public void doReservate(){
        ReservationDetailedDto reservation = new ReservationDetailedDto();
        reservation.setMedia(_media);
        reservation.setReservationDate(new Date(Calendar.getInstance().getTime().getTime()));
        reservation.setClosed(false);
        reservation.setCustomer(_customer);
        _isLoading.setValue(true);

        _reservateTask = startBackgroundTask(() -> {
            try {
                MainRemoteObjectInf remoteObject = RmiHelper.getSession();
                ReservationRemoteObjectInf reservatedObject = remoteObject.getReservationRemoteObject();
                //ReservationApplicationFacade (pkg Application) checks the permission for making reservation.
                //Currently only account-roles ADMIN, BIBLIOTHEKAR and AUSLEIHE have this permission
                //Login as <staff1/password> to be able to make reservations.
                AccountDetailedDto account = AccountManager.getInstance().getAccount();
                int result = reservatedObject.reserveMedia( _media , _customer, account);

                if ( result == 0) {
                    Platform.runLater(() -> showErrorMessage("This book was already reservated for ", _customer.getFirstName() + " " + _customer.getLastName()));
                } else if( result < 0 ) {
                    Platform.runLater(() -> showErrorMessage("Reservation failed for", _media.getTitle()));
                } else if( result > 0 ){
                    Platform.runLater(() -> showSuccessMessage("Reservation successful", "Reservation number: " + result));
                }
            } catch (Exception e) {
                Platform.runLater(() -> showRmiErrorMessage(e));
            } finally {
                Platform.runLater(() -> _isLoading.setValue(false));
            }
        });

        try {
            _reservateTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        initializeCustomer();
    }

    @FXML
    public void searchCustomer(){
        try {

            MainRemoteObjectInf mro = RmiHelper.getSession();
            _customer = mro.getCustomerRemoteObject().getCustomerSmallById(Integer.valueOf(_txtCustomerID.getText()));

            if(_customer != null){
                _lbFirstname.setText(_customer.getFirstName());
                _lbLastname.setText(_customer.getLastName());
                _btnReservate.setDisable(false);
            }
        } catch (Exception e) {
            Platform.runLater(() -> showRmiErrorMessage(e));
        }
    }

    @FXML
    private void cancel() {
        try {
            stopTask(_reservateTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            _isLoading.setValue(false);
        }
    }

    @Override
    public void load() {
    }

    @Override
    public void reload() {
    }

    @Override
    public void exit() {
        _pane.setDisable(false);
    }

    @Override
    public void dispose() {
    }
}