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
import at.team2.database_wrapper.facade.ReservationFacade;
import at.team2.domain.entities.*;
import at.team2.domain.enums.properties.ReservationProperty;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ReservationApplicationFacade extends BaseApplicationFacade<Reservation, ReservationDetailedDto, AccountDetailedDto, ReservationProperty> {
    private ReservationFacade _reservationFacade;

    public ReservationApplicationFacade() {
        super();
    }

    private ReservationFacade getReservationFacade() {
        if(_reservationFacade == null) {
            _reservationFacade = new ReservationFacade(getSession());
        }

        return _reservationFacade;
    }

    @Override
    public Reservation getById(int id) {
        return getReservationFacade().getById(id);
    }

    @Override
    public List<Reservation> getList() {
        return getReservationFacade().getList();
    }

    @Override
    public void closeSession() {
        if(_reservationFacade != null) {
            _reservationFacade.closeSession();
            _reservationFacade = null;
        }

        super.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<ReservationProperty, String>>> add(ReservationDetailedDto value, AccountDetailedDto updater) {
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

            if(currentReservations.size() == 0) {
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

            //return 0;
        } else {
            return 0;
        }
    }

    public List<Reservation> getListByCustomer(int id) {
        FilterConnector<ReservationProperty, ReservationProperty> connector = new FilterConnector<>(
                new Filter<>(id, ReservationProperty.CUSTOMER__ID, MatchType.EQUALS, CaseType.NORMAL)
        );

        return getReservationFacade().filter(connector);
    }
}