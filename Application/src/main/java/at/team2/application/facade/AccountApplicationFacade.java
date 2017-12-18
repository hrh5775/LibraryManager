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
    private AccountFacade _accountFacade;
    private ConfigurationFacade _configurationFacade;

    public AccountApplicationFacade() {
        super();
    }

    private AccountFacade getAccountFacade() {
        if(_accountFacade == null) {
            _accountFacade = new AccountFacade(getDbSession());
        }

        return _accountFacade;
    }

    private ConfigurationFacade getConfigurationFacade() {
        if(_configurationFacade == null) {
            _configurationFacade = new ConfigurationFacade(getDbSession());
        }

        return _configurationFacade;
    }

    @Override
    public void closeDbSession() {
        if(_accountFacade != null) {
            _accountFacade.closeSession();
            _accountFacade = null;
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

        if(_accountFacade != null) {
            _accountFacade.setSession(getDbSession());
        }

        if(_configurationFacade != null) {
            _configurationFacade.setSession(getDbSession());
        }
    }

    @Override
    public Account getById(int id) {
        renewDbSession();

        return getAccountFacade().getById(id);
    }

    @Override
    public List<Account> getList() {
        renewDbSession();

        return getAccountFacade().getList();
    }

    @Override
    public Pair<Integer, List<Pair<AccountProperty, String>>> add(AccountDetailedDto value, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Account entity = mapper.map(value, Account.class);
            List<Pair<AccountProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getAccountFacade().add(entity, TransactionType.AUTO_COMMIT), list);
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
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Account entity = mapper.map(value, Account.class);
            List<Pair<AccountProperty,String>> list = entity.validate();

            if(list.size() == 0) {
                return new Pair<>(getAccountFacade().update(entity, TransactionType.AUTO_COMMIT), list);
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
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            AccountFacade accountFacade = getAccountFacade();
            List<Pair<AccountProperty, String>> list = accountFacade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(accountFacade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<AccountProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(AccountProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }

    public Account login(String userName, String password) {
        renewDbSession();

        FilterConnector<AccountProperty, AccountProperty> connector = new FilterConnector<>(
                new Filter<>(userName, AccountProperty.USER_NAME, MatchType.EQUALS, CaseType.NORMAL),
                ConnectorType.AND,
                new Filter<>(password, AccountProperty.PASSWORT, MatchType.EQUALS, CaseType.NORMAL));

        List<Account> list = getAccountFacade().filter(connector);

        if(list.size() > 0) {
            Account tmp = list.get(0);
            AccountDetailedDto accountDto = MapperHelper.getMapper().map(tmp, AccountDetailedDto.class);
            SessionManager.getInstance().addAccount(accountDto);
            return tmp;
        }

        ConfigurationFacade configurationFacade = getConfigurationFacade();

        // try to login with ldap
        FilterConnector<ConfigurationProperty, ConfigurationProperty> ldapAdServerConnector = new FilterConnector<>(
                new Filter<>("LDAP_AD_SERVER", ConfigurationProperty.IDENTIFIER, MatchType.EQUALS, CaseType.NORMAL));
        List<Configuration> ldapAdServerList = configurationFacade.filter(ldapAdServerConnector);

        FilterConnector<ConfigurationProperty, ConfigurationProperty> additionalDNInformationConnector = new FilterConnector<>(
                new Filter<>("LDAP_ADDITIONAL_DN_INFORMATION", ConfigurationProperty.IDENTIFIER, MatchType.EQUALS, CaseType.NORMAL));
        List<Configuration> additionalDNInformationList = configurationFacade.filter(additionalDNInformationConnector);

        if(ldapAdServerList.size() > 0 && additionalDNInformationList.size() > 0) {
            String ldapAdServer = ldapAdServerList.get(0).getData();
            String additionalDNInformation;

            for (int i = 0; i < additionalDNInformationList.size(); i++) {
                additionalDNInformation = additionalDNInformationList.get(i).getData();

                String fullDNInformation = "uid=" + userName + (additionalDNInformation != null && !additionalDNInformation.isEmpty() ?
                        "," + additionalDNInformation : "");

                if(LdapHelper.hasValidCredentials(ldapAdServer, true, fullDNInformation, password)) {
                    Account account = new Account();
                    account.setId(0);
                    account.setUserName(userName);
                    account.setPassword(password);

                    AccountRole role = new AccountRole();
                    role.setId(0);
                    role.setKey(Role.ADMIN.toString());
                    role.setRoleName(Role.ADMIN.toString());
                    account.setAccountRole(role);

                    AccountDetailedDto accountDto = MapperHelper.getMapper().map(account, AccountDetailedDto.class);
                    SessionManager.getInstance().addAccount(accountDto);
                    return account;
                }
            }
        }

        return null;
    }

    public void logout(int id) {
        renewDbSession();

        FilterConnector<AccountProperty, AccountProperty> connector = new FilterConnector<>(
                new Filter<>(id, AccountProperty.ID, MatchType.EQUALS, CaseType.NORMAL)
        );

        List<Account> list = getAccountFacade().filter(connector);

        if(list.size() > 0) {
            Account tmp = list.get(0);
            AccountDetailedDto account = MapperHelper.getMapper().map(tmp, AccountDetailedDto.class);
            SessionManager.getInstance().removeSession(account);
        }
    }
}