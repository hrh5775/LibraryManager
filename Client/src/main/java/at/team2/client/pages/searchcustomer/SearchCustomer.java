package at.team2.client.pages.searchcustomer;

import at.team2.client.common.AccountManager;
import at.team2.client.controls.loadingindicator.LoadingIndicator;
import at.team2.client.pages.BasePage;
import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.helper.RmiHelper;
import at.team2.common.interfaces.LoanRemoteObjectInf;
import at.team2.common.interfaces.MainRemoteObjectInf;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.lang.model.type.NullType;
import java.util.LinkedList;
import java.util.List;

public class SearchCustomer extends BasePage<Void, NullType, NullType, NullType> {
    @FXML
    private LoadingIndicator _loadingIndicator;
    @FXML
    private LoadingIndicator _additionalInformationLoadingIndicator;
    @FXML
    private Pane _mainPanel;
    @FXML
    private Pane _tablePane;
    @FXML
    private Button _searchButton;
    @FXML
    private BooleanProperty _isLoading;
    @FXML
    private BooleanProperty _isAdditionalInfoLoading;
    @FXML
    private ListProperty<CustomerSmallDto> _customerList;
    @FXML
    private ListProperty<ReservationDetailedDto> _reservationList;
    @FXML
    private ListProperty<LoanDetailedDto> _loanList;
    @FXML
    private TableView _customerTableView;
    @FXML
    private TableView _loanTableView;
    @FXML
    private TableView _reservationTableView;
    @FXML
    private Pane _reservationView;
    @FXML
    private Pane _loanView;
    @FXML
    private BooleanProperty _customerListViewVisible;
    @FXML
    private BooleanProperty _additionalListViewVisible;
    @FXML
    private TextField _searchField;
    @FXML
    private Button _ExtendButton;

    private Thread _searchTask;
    private Thread _showAdditionalInfoTask;

    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(SearchCustomer.class.getResource("search_customer.fxml"));
        setCenter(parent);

        _isLoading.setValue(false);
        _isAdditionalInfoLoading.setValue(false);
        _loadingIndicator.visibleProperty().bind(_isLoading);
        _additionalInformationLoadingIndicator.visibleProperty().bind(_isAdditionalInfoLoading);

        _customerListViewVisible.setValue(false);
        _customerTableView.visibleProperty().bind(_customerListViewVisible.and(_isLoading.not()));
        _customerTableView.itemsProperty().bind(_customerList);

        _additionalListViewVisible.setValue(false);
        _loanTableView.visibleProperty().bind(_additionalListViewVisible.and(_isLoading.not()));
        _loanView.visibleProperty().bind(_loanTableView.visibleProperty());
        _loanTableView.itemsProperty().bind(_loanList);

        _reservationTableView.visibleProperty().bind(_additionalListViewVisible.and(_isLoading.not()));
        _reservationView.visibleProperty().bind(_reservationTableView.visibleProperty());
        _reservationTableView.itemsProperty().bind(_reservationList);

        _searchButton.disableProperty().bind(_searchField.textProperty().isEmpty());
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
    private void search() {
        if(_searchTask == null && !_searchField.getText().isEmpty()) {
            _isLoading.setValue(true);
            _additionalListViewVisible.setValue(false);

            // reset the lists
            List<CustomerSmallDto> tmpCustomerList = new LinkedList<>();
            _customerList.set(new ObservableListWrapper<>(tmpCustomerList));

            List<ReservationDetailedDto> tmpReservationList = new LinkedList<>();
            _reservationList.set(new ObservableListWrapper<>(tmpReservationList));

            List<LoanDetailedDto> tmpLoanList = new LinkedList<>();
            _loanList.set(new ObservableListWrapper<>(tmpLoanList));

            _searchTask = startBackgroundTask(() -> {
                try {
                    // @todo: perhaps use a cache
                    MainRemoteObjectInf remoteObject = RmiHelper.getSession();

                    List<CustomerSmallDto> customerList = remoteObject.getCustomerRemoteObject().getList(_searchField.getText());
                    _customerList.set(new ObservableListWrapper<>(customerList));
                } catch (Exception e) {
                    Platform.runLater(() -> showRmiErrorMessage(e));
                } finally {
                    _searchTask = null;

                    Platform.runLater(() -> {
                        _customerListViewVisible.setValue(true);
                        _isLoading.setValue(false);
                    });
                }
            });
        }
    }

    @FXML
    public void customerItemClicked(MouseEvent mouseEvent) {
        Object entity = _customerTableView.getSelectionModel().getSelectedItem();

        if(entity != null) {
            CustomerSmallDto customer = (CustomerSmallDto) entity;

            if(customer != null) {
                _isAdditionalInfoLoading.setValue(true);
                _additionalListViewVisible.setValue(false);

                _showAdditionalInfoTask = startBackgroundTask(() -> {
                    try {
                        // @todo: perhaps use a cache
                        MainRemoteObjectInf remoteObject = RmiHelper.getSession();

                        List<ReservationDetailedDto> reservationList = remoteObject.getReservationRemoteObject().getListByCustomer(customer.getId());
                        _reservationList.set(new ObservableListWrapper<>(reservationList));

                        List<LoanDetailedDto> loanList = remoteObject.getLoanRemoteObject().getListByCustomer(customer.getId());
                        _loanList.set(new ObservableListWrapper<>(loanList));

                        Platform.runLater(() -> _additionalListViewVisible.setValue(true));
                    } catch (Exception e) {
                        Platform.runLater(() -> showRmiErrorMessage(e));
                    } finally {
                        Platform.runLater(() -> {
                            _isAdditionalInfoLoading.setValue(false);
                        });
                    }
                });
            }
        }
    }

    @FXML
    private void cancel() {
        try {
            stopTask(_searchTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            _isLoading.setValue(false);
        }
    }

    @FXML
    private void cancelAdditionalInfo() {
        try {
            stopTask(_showAdditionalInfoTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            _isAdditionalInfoLoading.setValue(false);
        }
    }

    @FXML
    private void extendLoan() {
        Object entity = _loanTableView.getSelectionModel().getSelectedItem();

        if(entity != null) {
            LoanDetailedDto loanEntity = (LoanDetailedDto) entity;

            try {
                MainRemoteObjectInf remoteObject = RmiHelper.getSession();
                LoanRemoteObjectInf loanRemote = remoteObject.getLoanRemoteObject();

                if(loanRemote.extendLoan(loanEntity, AccountManager.getInstance().getAccount())) {
                    _loanList.getValue().remove(loanEntity); //  @todo: test this
                    loanEntity = loanRemote.getLoanDetailedById(loanEntity.getId());
                    _loanList.getValue().add(loanEntity);

                    final LoanDetailedDto finalLoanEntity = loanEntity;

                    Platform.runLater(()-> showSuccessMessage("Success","Successfully extended the loan until " + finalLoanEntity.getEnd() + " weeks"));
                } else {
                    Platform.runLater(()-> showSuccessMessage("Error","Failed to extend the loan"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}