package at.team2.application.facade;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.MapperHelper;
import at.team2.application.helper.RoleHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.ConnectorType;
import at.team2.database_wrapper.enums.MatchType;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.ConfigurationFacade;
import at.team2.database_wrapper.facade.MediaFacade;
import at.team2.database_wrapper.facade.ReservationFacade;
import at.team2.domain.entities.*;
import at.team2.domain.enums.properties.ConfigurationProperty;
import at.team2.domain.enums.properties.ReservationProperty;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ReservationApplicationFacade extends BaseApplicationFacade<Reservation, ReservationDetailedDto, AccountDetailedDto, ReservationProperty> {
    private ReservationFacade _reservationFacade;
    private MediaFacade _mediaFacade;
    private ConfigurationFacade _configurationFacade;

    public ReservationApplicationFacade() {
        super();
    }

    private ReservationFacade getReservationFacade() {
        if(_reservationFacade == null) {
            _reservationFacade = new ReservationFacade(getDbSession());
        }

        return _reservationFacade;
    }

    private MediaFacade getMediaFacade() {
        if(_mediaFacade == null) {
            _mediaFacade = new MediaFacade(getDbSession());
        }

        return _mediaFacade;
    }

    private ConfigurationFacade getConfigurationFacade() {
        if(_configurationFacade == null) {
            _configurationFacade = new ConfigurationFacade(getDbSession());
        }

        return _configurationFacade;
    }

    @Override
    public void closeDbSession() {
        if(_reservationFacade != null) {
            _reservationFacade.closeSession();
            _reservationFacade = null;
        }

        if(_mediaFacade != null) {
            _mediaFacade.closeSession();
            _mediaFacade = null;
        }

        if(_configurationFacade != null) {
            _configurationFacade.closeSession();
            _configurationFacade = null;
        }

        super.closeDbSession();
    }

    @Override
    protected void renewDbSession() {
        super.renewDbSession();

        if(_reservationFacade != null) {
            _reservationFacade.setSession(getDbSession());
        }

        if(_mediaFacade != null) {
            _mediaFacade.setSession(getDbSession());
        }

        if(_configurationFacade != null) {
            _configurationFacade.setSession(getDbSession());
        }
    }

    @Override
    public Reservation getById(int id) {
        renewDbSession();

        return getReservationFacade().getById(id);
    }

    @Override
    public List<Reservation> getList() {
        renewDbSession();

        return getReservationFacade().getList();
    }

    @Override
    public Pair<Integer, List<Pair<ReservationProperty, String>>> add(ReservationDetailedDto value, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Reservation entity = mapper.map(value, Reservation.class);
            List<Pair<ReservationProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getReservationFacade().add(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<ReservationProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(ReservationProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Integer, List<Pair<ReservationProperty, String>>> update(ReservationDetailedDto value, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Reservation entity = mapper.map(value, Reservation.class);
            List<Pair<ReservationProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getReservationFacade().update(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<ReservationProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(ReservationProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Boolean, List<Pair<ReservationProperty, String>>> delete(int id, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            ReservationFacade reservationFacade = getReservationFacade();
            List<Pair<ReservationProperty, String>> list = reservationFacade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(reservationFacade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<ReservationProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(ReservationProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }

    public int reserveMedia(MediaSmallDto media, CustomerSmallDto customer, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                        RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                        RoleHelper.hasRole(updater, Role.AUSLEIHE))) {

            ReservationFacade reservationFacade = getReservationFacade();
            // do not add duplicated entries
            List<Reservation> currentReservations = reservationFacade.filter(new FilterConnector<>(
                    new Filter<>(media.getMediaId(), ReservationProperty.MEDIA__ID, MatchType.EQUALS, CaseType.NORMAL),
                    ConnectorType.AND,
                    new Filter<>(customer.getId(), ReservationProperty.CUSTOMER__ID, MatchType.EQUALS, CaseType.NORMAL)
            ));

            MediaFacade mediaFacade = getMediaFacade();
            Media mediaEntity = mediaFacade.getById(media.getMediaId());

            if(mediaEntity != null) {
                if(!mediaEntity.getAvailable()) {
                    if (currentReservations.size() == 0) {
                        ModelMapper mapper = MapperHelper.getMapper();
                        Customer tmpCustomer = mapper.map(customer, Customer.class);
                        Media tmpMedia = mapper.map(media, Media.class);

                        Reservation reservation = new Reservation();
                        reservation.setCustomer(tmpCustomer);
                        reservation.setMedia(tmpMedia);
                        reservation.setReservationDate(new Date(Calendar.getInstance().getTime().getTime()));

                        return reservationFacade.add(reservation, TransactionType.AUTO_COMMIT);
                    }

                    // there are pending reservations for this media and this customer
                    return 0;
                }

                // the media cannot be reserved because one or more media members are available
                return 0;
            }

            // the media was not found
            return 0;
        } else {
            return 0;
        }
    }

    public List<Reservation> getListByCustomer(int id) {
        renewDbSession();

        FilterConnector<ReservationProperty, ReservationProperty> connector = new FilterConnector<>(
                new Filter<>(id, ReservationProperty.CUSTOMER__ID, MatchType.EQUALS, CaseType.NORMAL)
        );

        return getReservationFacade().filter(connector);
    }

    public void removeOldReservations() {
        renewDbSession();

        ConfigurationFacade configurationFacade = getConfigurationFacade();

        // load the configuration from the database
        FilterConnector<ConfigurationProperty, ConfigurationProperty> ldapAdServerConnector = new FilterConnector<>(
                new Filter<>("DAYS_TO_PRESERVE_RESERVATIONS", ConfigurationProperty.IDENTIFIER, MatchType.EQUALS, CaseType.NORMAL));

        List<Configuration> daysToPreserveReservationsList = configurationFacade.filter(ldapAdServerConnector);

        int daysToPreserveReservations = Integer.parseInt(daysToPreserveReservationsList.get(0).getData());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -daysToPreserveReservations);

        ReservationFacade facade = getReservationFacade();

        FilterConnector<ReservationProperty, ReservationProperty> connector = new FilterConnector<>(
                new Filter<>(new Date(calendar.getTime().getTime()), ReservationProperty.INFORMATION_DATE, MatchType.LESS_THAN, CaseType.NORMAL)
        );

        List<Reservation> reservationList = facade.filter(connector);

        for(Reservation item : reservationList) {
            facade.delete(item.getId(), TransactionType.AUTO_COMMIT);
        }
    }
}