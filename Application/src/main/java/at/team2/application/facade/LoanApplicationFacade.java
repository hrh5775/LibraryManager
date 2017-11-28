package at.team2.application.facade;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.RoleHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.ConnectorType;
import at.team2.database_wrapper.enums.MatchType;
import at.team2.database_wrapper.facade.*;
import at.team2.domain.entities.*;
import at.team2.domain.enums.properties.MediaMemberProperty;
import at.team2.domain.enums.properties.ReservationProperty;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import at.team2.application.helper.MapperHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.domain.enums.properties.LoanProperty;
import javafx.util.Pair;

public class LoanApplicationFacade extends BaseApplicationFacade<Loan, LoanDetailedDto, AccountDetailedDto, LoanProperty> {
    private static final int loanTermMultiplier = 7 * 24 * 3600 * 1000;
    private LoanFacade _loanFacade;
    private MediaMemberFacade _mediaMemberFacade;
    private MediaFacade _mediaFacade;
    private CustomerFacade _customerFacade;
    private ReservationFacade _reservationFacade;

    public LoanApplicationFacade() {
        super();
    }

    private LoanFacade getLoanFacade() {
        if(_loanFacade == null) {
            _loanFacade = new LoanFacade(getDbSession());
        }

        return _loanFacade;
    }

    private MediaMemberFacade getMediaMemberFacade() {
        if(_mediaMemberFacade == null) {
            _mediaMemberFacade = new MediaMemberFacade(getDbSession());
        }

        return _mediaMemberFacade;
    }

    private MediaFacade getMediaFacade() {
        if(_mediaFacade == null) {
            _mediaFacade = new MediaFacade(getDbSession());
        }

        return _mediaFacade;
    }

    private CustomerFacade getCustomerFacade() {
        if(_customerFacade == null) {
            _customerFacade = new CustomerFacade(getDbSession());
        }

        return _customerFacade;
    }

    private ReservationFacade getReservationFacade() {
        if(_reservationFacade == null) {
            _reservationFacade = new ReservationFacade(getDbSession());
        }

        return _reservationFacade;
    }

    @Override
    public void closeDbSession() {
        if(_loanFacade != null) {
            _loanFacade.closeSession();
            _loanFacade = null;
        }

        if(_mediaMemberFacade != null) {
            _mediaMemberFacade.closeSession();
            _mediaMemberFacade = null;
        }

        if(_mediaFacade != null) {
            _mediaFacade.closeSession();
            _mediaFacade = null;
        }

        if(_customerFacade != null) {
            _customerFacade.closeSession();
            _customerFacade = null;
        }

        if(_reservationFacade != null) {
            _reservationFacade.closeSession();
            _reservationFacade = null;
        }

        super.closeDbSession();
    }

    @Override
    protected void renewDbSession() {
        super.renewDbSession();

        if(_loanFacade != null) {
            _loanFacade.setSession(getDbSession());
        }

        if(_mediaMemberFacade != null) {
            _mediaMemberFacade.setSession(getDbSession());
        }

        if(_mediaFacade != null) {
            _mediaFacade.setSession(getDbSession());
        }

        if(_customerFacade != null) {
            _customerFacade.setSession(getDbSession());
        }

        if(_reservationFacade != null) {
            _reservationFacade.setSession(getDbSession());
        }
    }

    @Override
    public Loan getById(int id) {
        renewDbSession();

        return getLoanFacade().getById(id);
    }

    @Override
    public List<Loan> getList() {
        renewDbSession();

        return getLoanFacade().getList();
    }

    @Override
    public Pair<Integer, List<Pair<LoanProperty, String>>> add(LoanDetailedDto value, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Loan entity = mapper.map(value, Loan.class);
            List<Pair<LoanProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getLoanFacade().add(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<LoanProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(LoanProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Integer, List<Pair<LoanProperty, String>>> update(LoanDetailedDto value, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Loan entity = mapper.map(value, Loan.class);
            List<Pair<LoanProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getLoanFacade().update(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<LoanProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(LoanProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Boolean, List<Pair<LoanProperty, String>>> delete(int id, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            LoanFacade loanFacade = getLoanFacade();
            List<Pair<LoanProperty, String>> list = loanFacade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(loanFacade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<LoanProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(LoanProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }

    public int loanMediaMember(MediaMemberSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            // get a list of available media members => books, dvds, for the specified media
            MediaMember mediaMemberEntity = getMediaMemberFacade().getNotLoanedMediaMember(mediaMember.getId());
            Customer customerEntity = getCustomerFacade().getById(customer.getId());

            if (mediaMemberEntity != null && customerEntity != null &&
                    checkIsLoanPossible(mediaMemberEntity.getMedia().getId(), customerEntity.getId(), false)) {
                Media mediaEntity = mediaMemberEntity.getMedia();
                int loanTerm = mediaEntity.getMediaType().getLoanCondition().getLoanTerm(); // weeksToExtend

                Loan loan = new Loan();
                loan.setCustomer(customerEntity);
                loan.setMediaMember(mediaMemberEntity);
                loan.setStart(new Date(Calendar.getInstance().getTime().getTime()));
                loan.setEnd(new Date(Calendar.getInstance().getTime().getTime() + (loanTerm * loanTermMultiplier)));
                return getLoanFacade().add(loan, TransactionType.AUTO_COMMIT);
            }

            return 0;
        } else {
            return 0;
        }
    }

    public int takeBackMediaMember(LoanDetailedDto loan){
        renewDbSession();

        LoanFacade loanFacade = getLoanFacade();
        Loan loanEntity = loanFacade.getById(loan.getId());
        loanEntity.setClosed(true);
        return loanFacade.update(loanEntity,TransactionType.AUTO_COMMIT);
    }

    public boolean extendLoan(LoanDetailedDto loan, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if (updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                        RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                        RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            LoanFacade loanFacade = getLoanFacade();
            Loan loanEntity = loanFacade.getById(loan.getId());

            if (loanEntity != null &&
                    checkIsLoanPossible(loanEntity.getMediaMember().getMedia().getId(), loan.getCustomer().getId(), true)) {
                loanEntity.setLastRenewalStart(new Date(Calendar.getInstance().getTime().getTime()));
                LoanCondition loanCondition = loanEntity.getMediaMember().getMedia().getMediaType().getLoanCondition();

                int weeksToExtend = loanCondition.getLoanTerm();
                int extendedCount;

                if (loanEntity.getLastRenewalStart() != null) {
                    extendedCount = (int) (((loanEntity.getEnd().getTime() - loanEntity.getStart().getTime()) / (weeksToExtend * loanTermMultiplier)) - 1);
                } else {
                    extendedCount = 0;
                }

                if (extendedCount < loanCondition.getExtension()) {
                    loanEntity.setLastRenewalStart(new Date(Calendar.getInstance().getTime().getTime()));
                    loanEntity.setEnd(new Date(loanEntity.getEnd().getTime() + (weeksToExtend * loanTermMultiplier)));
                    ModelMapper mapper = MapperHelper.getMapper();
                    Loan entity = mapper.map(loanEntity, Loan.class);
                    loanFacade.update(entity, TransactionType.AUTO_COMMIT);

                    return true;
                }

                return false;
            }

            return false;
        } else {
            List<Pair<LoanProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(LoanProperty.ID, "permission denied"));
            return false;
        }
    }

    public List<Loan> getListByCustomer(int id) {
        renewDbSession();

        FilterConnector<LoanProperty, LoanProperty> connector = new FilterConnector<>(
                new Filter<>(id, LoanProperty.CUSTOMER__ID, MatchType.EQUALS, CaseType.NORMAL));

        return getLoanFacade().filter(connector);
    }

    public boolean isLoanPossible(int mediaId, int customerId, boolean isExtend) {
        renewDbSession();

        return checkIsLoanPossible(mediaId, customerId, isExtend);
    }

    /**
     * We have to check if there are less reservations than available media members, which are currently not on loan
     * and the unique reservation with the mediaId for the customer has the "informationDate" set, which would lead to
     * a possible loan. A loan extend will also fail if there are pending reservations, which have a valid
     * "informationDate".
     * @param mediaId
     * @param customerId
     * @return true     the loan is possible
     *         false    otherwise
     */
    private boolean checkIsLoanPossible(int mediaId, int customerId, boolean isExtend) {
        MediaFacade mediaFacade = getMediaFacade();
        Media mediaEntity = mediaFacade.getById(mediaId);

        if(mediaEntity != null) {
            if(!isExtend) {
                if(!mediaEntity.getAvailable()) {
                    return false;
                }
            }

            CustomerFacade customerFacade = getCustomerFacade();
            Customer customerEntity = customerFacade.getById(customerId);

            if(customerEntity != null) {
                // check if the reservation with a valid "informationDate" was made for the customerId
                FilterConnector<ReservationProperty, ReservationProperty> reservationConnector = new FilterConnector<>(
                        new Filter<>(null, ReservationProperty.INFORMATION_DATE, MatchType.IS_NOT, CaseType.NORMAL),
                        ConnectorType.AND,
                        new FilterConnector<>(
                                new Filter<>(mediaEntity.getId(), ReservationProperty.MEDIA__ID, MatchType.EQUALS, CaseType.NORMAL),
                                ConnectorType.AND,
                                new Filter<>(customerEntity.getId(), ReservationProperty.CUSTOMER__ID, MatchType.EQUALS, CaseType.NORMAL)
                        )
                );

                ReservationFacade reservationFacade = getReservationFacade();
                List<Reservation> reservationList = reservationFacade.filter(reservationConnector);

                // there should only be one at most, but we check intentionally if the size is greater than 0
                if(reservationList.size() > 0) {
                    // the customer picks up his reservation, therefore allow it
                    return true;
                }


                // in every other case we have to check the above mentioned conditions
                // now we do not have to use the customerId anymore


                // get the list with the media members for the mediaId
                FilterConnector<MediaMemberProperty, MediaMemberProperty> mediaMemberConnector = new FilterConnector<>(
                        new Filter<>(mediaEntity.getId(), MediaMemberProperty.MEDIA__ID, MatchType.EQUALS, CaseType.NORMAL)
                );

                List<MediaMember> mediaMemberList = getMediaMemberFacade().filter(mediaMemberConnector);

                // get the reservations for the mediaId with a valid "informationDate", which is in this case not null
                reservationConnector = new FilterConnector<>(
                        new Filter<>(null, ReservationProperty.INFORMATION_DATE, MatchType.IS_NOT, CaseType.NORMAL),
                        ConnectorType.AND,
                        new Filter<>(mediaEntity.getId(), ReservationProperty.MEDIA__ID, MatchType.EQUALS, CaseType.NORMAL)
                );

                reservationList = reservationFacade.filter(reservationConnector);

                // get the active loans for the mediaId
                FilterConnector<LoanProperty, LoanProperty> loanConnector = new FilterConnector<>(
                        new Filter<>(mediaEntity.getId(), LoanProperty.MEDIA__ID, MatchType.EQUALS, CaseType.NORMAL),
                        ConnectorType.AND,
                        new Filter<>(false, LoanProperty.CLOSED, MatchType.EQUALS, CaseType.NORMAL)
                );

                List<Loan> loanList = getLoanFacade().filter(loanConnector);

                // compare the sizes
                if(isExtend) {
                    // we can extend if there are less loans open than media members available, but only when there is
                    // no pending reservation available
                    if(reservationList.size() == 0) {
                        return loanList.size() <= mediaMemberList.size();
                    }
                }

                return (reservationList.size() + loanList.size()) < mediaMemberList.size();
            }
        }

        return false;
    }
}