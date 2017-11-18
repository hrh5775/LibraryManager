package at.team2.client.pages.reservation;

import at.team2.client.pages.BasePage;
import at.team2.client.pages.mediadetail.MediaDetail;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.MainRemoteObjectInf;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javax.lang.model.type.NullType;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservateMedium extends BasePage<Void, NullType, NullType, NullType> {

    private MediaSmallDto _media;
    @FXML
    private TextField lbCostumerID;
    @FXML
    private TextField lbFirstname;
    @FXML
    private TextField lbLastname;
    @FXML
    private TextField lbMediaID;
    @FXML
    private TextField lbMediaTitle;
    @FXML
    private Button btnReservate;

    private CustomerSmallDto _customer = null;
    private Pane _pane;


    public ReservateMedium( MediaSmallDto media , Pane pane ){
        _media = media;
        _customer = new CustomerSmallDto();
        _pane = pane;
//        initializeView();
//        initializeDialog();

    }
    @Override
    public void initialize() {

        System.out.println( "initialize() called ");
    }

    @Override
    public void initializeView() {

        System.out.println( "initializeView() called ");

        Parent parent = loadView(ReservateMedium.class.getResource("reservateMedium.fxml"));
        setCenter(parent);


    }

    public void initializeDialog(){
        lbFirstname.setText( "" );
        lbLastname.setText( "" );
        lbMediaID.setText( String.valueOf( _media.getMediaId() ) );
        lbMediaTitle.setText( _media.getTitle() );
        btnReservate.setDisable( true );
        _pane.setDisable( true );
    }

    @FXML
    public void doReservate(){

        ReservationDetailedDto reservation = new ReservationDetailedDto();
        reservation.setMedia( _media );
        reservation.setReservationDate( Date.valueOf( LocalDate.now() ) );
        reservation.setClosed( Byte.valueOf( (byte)0 ));
        reservation.setCustomer( _customer );

    }

    @FXML
    public void searchCostumer(){

        try {
            MainRemoteObjectInf mro = RmiHelper.getSession();
            _customer = mro.getCustomerRemoteObject().getCustomerSmallById( Integer.valueOf( lbCostumerID.getText() ) );

            if( _customer != null ){
                lbFirstname.setText( _customer.getFirstName() );
                lbLastname.setText( _customer.getLastName() );
                btnReservate.setDisable( false );
            }

        } catch (RemoteException e) {
            showRmiErrorMessage(e);
        } catch (NotBoundException e) {
            showRmiErrorMessage(e);
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

        _pane.setDisable( false );
    }

    @Override
    public void dispose() {

    }
}
