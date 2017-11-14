package at.team2.application.facade;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.LdapHelper;
import at.team2.application.helper.MapperHelper;
import at.team2.application.helper.RoleHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.ConnectorType;
import at.team2.database_wrapper.enums.MatchType;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.AccountFacade;
import at.team2.database_wrapper.facade.ConfigurationFacade;
import at.team2.domain.entities.*;
import at.team2.domain.enums.properties.AccountProperty;
import at.team2.domain.enums.properties.ConfigurationProperty;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;
import java.util.LinkedList;
import java.util.List;

public class AccountApplicationFacade extends BaseApplicationFacade<Account, AccountDetailedDto, AccountDetailedDto, AccountProperty> {
    private static AccountApplicationFacade _instance;
    private AccountFacade _facade;
    private ConfigurationFacade _configurationFacade;

    private AccountApplicationFacade() {
    }

    public static AccountApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new AccountApplicationFacade();
            _instance._facade = new AccountFacade();
            _instance._configurationFacade = new ConfigurationFacade(_instance._facade.getCurrentSession());
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
    public Pair<Integer, List<Pair<AccountProperty, String>>> add(AccountDetailedDto value, AccountDetailedDto updater) {
        if(SessionManager.getInstance().isSessionAvailable(updater) &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Account entity = mapper.map(value, Account.class);
            List<Pair<AccountProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<AccountProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(AccountProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Integer, List<Pair<AccountProperty, String>>> update(AccountDetailedDto value, AccountDetailedDto updater) {
        if(SessionManager.getInstance().isSessionAvailable(updater) &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Account entity = mapper.map(value, Account.class);
            List<Pair<AccountProperty,String>> list = entity.validate();

            if(list.size() == 0) {
                return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<AccountProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(AccountProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Boolean, List<Pair<AccountProperty, String>>> delete(int id, AccountDetailedDto updater) {
        if(SessionManager.getInstance().isSessionAvailable(updater) &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            List<Pair<AccountProperty, String>> list = _facade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<AccountProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(AccountProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }

    public Account login(String userName, String password) {
        FilterConnector<AccountProperty, AccountProperty> connector = new FilterConnector<>(
                new Filter<>(userName, AccountProperty.USER_NAME, MatchType.EQUALS, CaseType.NORMAL),
                ConnectorType.AND,
                new Filter<>(password, AccountProperty.PASSWORT, MatchType.EQUALS, CaseType.NORMAL));

        List<Account> list = _facade.filter(connector);

        if(list.size() > 0) {
            Account tmp = list.get(0);
            AccountDetailedDto accountDto = MapperHelper.getMapper().map(tmp, AccountDetailedDto.class);
            SessionManager.getInstance().addAccount(accountDto);
            return tmp;
        }

        // try to login with ldap
        FilterConnector<ConfigurationProperty, ConfigurationProperty> ldapAdServerConnector = new FilterConnector<>(
                new Filter<>("LDAP_AD_SERVER", ConfigurationProperty.IDENTIFIER, MatchType.EQUALS, CaseType.NORMAL));
        List<Configuration> ldapAdServerList = _configurationFacade.filter(ldapAdServerConnector);

        FilterConnector<ConfigurationProperty, ConfigurationProperty> additionalDNInformationConnector = new FilterConnector<>(
                new Filter<>("LDAP_ADDITIONAL_DN_INFORMATION", ConfigurationProperty.IDENTIFIER, MatchType.EQUALS, CaseType.NORMAL));
        List<Configuration> additionalDNInformationList = _configurationFacade.filter(additionalDNInformationConnector);

        if(ldapAdServerList.size() > 0 && additionalDNInformationList.size() > 0) {
            String ldapAdServer = ldapAdServerList.get(0).getData();
            String additionalDNInformation = additionalDNInformationList.get(0).getData();

            String fullDNInformation = "uid=" + userName + (additionalDNInformation != null && !additionalDNInformation.isEmpty() ?
                    "," + additionalDNInformation : "");

            if(LdapHelper.hasValidCredentials(ldapAdServer, true, fullDNInformation, password)) {
                Account account = new Account();
                account.setId(0);
                account.setUserName(userName);
                account.setPassword(password);

                AccountRole role = new AccountRole();
                role.setId(0);
                role.setKey("");
                role.setRoleName("");
                account.setAccountRole(role);

                AccountDetailedDto accountDto = MapperHelper.getMapper().map(account, AccountDetailedDto.class);
                SessionManager.getInstance().addAccount(accountDto);
                return account;
            }
        }

        return null;
    }

    public void logout(int id) {
        FilterConnector<AccountProperty, AccountProperty> connector = new FilterConnector<>(
                new Filter<>(id, AccountProperty.ID, MatchType.EQUALS, CaseType.NORMAL)
        );

        List<Account> list = _facade.filter(connector);

        if(list.size() > 0) {
            Account tmp = list.get(0);
            AccountDetailedDto account = MapperHelper.getMapper().map(tmp, AccountDetailedDto.class);
            SessionManager.getInstance().removeSession(account);
        }
    }
}