package at.team2.application.facade;

import at.team2.application.helper.MapperHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.ConnectorType;
import at.team2.database_wrapper.enums.MatchType;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.AccountFacade;
import at.team2.domain.entities.*;
import at.team2.domain.enums.properties.AccountProperty;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;
import java.util.LinkedList;
import java.util.List;

public class AccountApplicationFacade extends BaseApplicationFacade<Account, AccountDetailedDto, AccountProperty> {
    private static AccountApplicationFacade _instance;
    private AccountFacade _facade;

    private AccountApplicationFacade() {
    }

    public static AccountApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new AccountApplicationFacade();
            _instance._facade = new AccountFacade();
        }

        return _instance;
    }

    @Override
    public Account getById(int id) {
        return _facade.getById(id);
    }

    @Override
    public List<Account> getList() {
        return _facade.getList();
    }

    @Override
    public void closeSession() {
        _facade.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<AccountProperty, String>>> add(AccountDetailedDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        Account entity = mapper.map(value, Account.class);
        List<Pair<AccountProperty, String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT),list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Integer, List<Pair<AccountProperty, String>>> update(AccountDetailedDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        Account entity = mapper.map(value, Account.class);
        List<Pair<AccountProperty,String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT),list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Boolean, List<Pair<AccountProperty, String>>> delete(int id) {
        List<Pair<AccountProperty,String>> list = _facade.getById(id).validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
        }

        return new Pair<>(false, new LinkedList<>());
    }

    public Account login(String userName, String password) {
        FilterConnector<AccountProperty, AccountProperty> connector = new FilterConnector<>(
                new Filter<>(userName, AccountProperty.USER_NAME, MatchType.EQUALS, CaseType.NORMAL),
                ConnectorType.AND,
                new Filter<>(password, AccountProperty.PASSWORT, MatchType.EQUALS, CaseType.NORMAL));

        List<Account> list = _facade.filter(connector);

        if(list.size() > 0) {
            return list.get(0);
        }

        return null;
    }
}