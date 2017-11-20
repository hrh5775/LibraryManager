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
import at.team2.database_wrapper.enums.MatchType;
import at.team2.database_wrapper.facade.*;
import at.team2.domain.entities.*;
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
    private static LoanApplicationFacade _instance;
    private LoanFacade _facade;
    private MediaMemberFacade _mediaMemberFacade;
    private CustomerFacade _customerFacade;
    private static final int loanTermMultiplier = 7 * 24 * 3600 * 1000;

    private LoanApplicationFacade() {
    }

    public static LoanApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new LoanApplicationFacade();
            _instance._facade = new LoanFacade();
            _instance._mediaMemberFacade = new MediaMemberFacade(_instance._facade.getCurrentSession());
            _instance._customerFacade = new CustomerFacade(_instance._facade.getCurrentSession());
        }

        return _instance;
    }

    @Override
    public Loan getById(int id) {
        return _facade.getById(id);
    }

    @Override
    public List<Loan> getList() {
        return _facade.getList();
    }

    @Override
    public void closeSession() {
        _facade.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<LoanProperty, String>>> add(LoanDetailedDto value, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Loan entity = mapper.map(value, Loan.class);
            List<Pair<LoanProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT), list);
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
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Loan entity = mapper.map(value, Loan.class);
            List<Pair<LoanProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT), list);
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
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            List<Pair<LoanProperty, String>> list = _facade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<LoanProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(LoanProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }

    public int loanMediaMember(MediaMemberSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            // get a list of available media members => books, dvds, for the specified media
            MediaMember mediaMemberEntity = _mediaMemberFacade.getNotLoanedMediaMember(mediaMember.getId());
            Customer customerEntity = _customerFacade.getById(customer.getId());

            if (mediaMemberEntity != null && customerEntity != null) {
                Media mediaEntity = mediaMemberEntity.getMedia();
                int loanTerm = mediaEntity.getMediaType().getLoanCondition().getLoanTerm(); // weeksToExtend

                Loan loan = new Loan();
                loan.setCustomer(customerEntity);
                loan.setMediaMember(mediaMemberEntity);
                loan.setStart(new Date(Calendar.getInstance().getTime().getTime()));
                loan.setEnd(new Date(Calendar.getInstance().getTime().getTime() + (loanTerm * loanTermMultiplier)));
                return _facade.add(loan, TransactionType.AUTO_COMMIT);
            }

            return 0;
        } else {
            return 0;
        }
    }

    public boolean extendLoan(LoanDetailedDto loan, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                        RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                        RoleHelper.hasRole(updater, Role.AUSLEIHE))) {
            Loan loanEntity = _facade.getById(loan.getId());

            if (loanEntity != null) {
                loanEntity.setLastRenewalStart(new Date(Calendar.getInstance().getTime().getTime()));
                LoanCondition loanCondition = loanEntity.getMediaMember().getMedia().getMediaType().getLoanCondition();

                int weeksToExtend = loanCondition.getLoanTerm();
                int extendedCount;

                if(loanEntity.getLastRenewalStart() != null) {
                    extendedCount = (int)(((loanEntity.getEnd().getTime() - loanEntity.getStart().getTime()) / (loanEntity.getEnd().getTime() - loanEntity.getLastRenewalStart().getTime())) -1);
                } else {
                    extendedCount = 0;
                }

                if(extendedCount < loanCondition.getExtension()) {
                    loanEntity.setLastRenewalStart(new Date(Calendar.getInstance().getTime().getTime()));
                    loanEntity.setEnd(new Date(loanEntity.getEnd().getTime() + (weeksToExtend * loanTermMultiplier)));
                    ModelMapper mapper = MapperHelper.getMapper();
                    Loan entity = mapper.map(loanEntity, Loan.class);
                    _facade.update(entity, TransactionType.AUTO_COMMIT);

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
        FilterConnector<LoanProperty, LoanProperty> connector = new FilterConnector<>(
                new Filter<>(id, LoanProperty.CUSTOMER__ID, MatchType.EQUALS, CaseType.NORMAL));

        return _facade.filter(connector);
    }
}