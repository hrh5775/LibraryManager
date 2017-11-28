package at.team2.application.facade;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.MapperHelper;
import at.team2.application.helper.RoleHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.CustomerDetailedDto;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.ConnectorType;
import at.team2.database_wrapper.enums.MatchType;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.CustomerFacade;
import at.team2.domain.entities.Customer;
import at.team2.domain.enums.properties.CustomerProperty;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

public class CustomerApplicationFacade extends BaseApplicationFacade<Customer, CustomerDetailedDto, AccountDetailedDto, CustomerProperty> {
    private CustomerFacade _customerFacade;

    public CustomerApplicationFacade() {
        super();
    }

    private CustomerFacade getCustomerFacade() {
        if(_customerFacade == null) {
            _customerFacade = new CustomerFacade(getDbSession());
        }

        return _customerFacade;
    }

    @Override
    public void closeDbSession() {
        if(_customerFacade != null) {
            _customerFacade.closeSession();
            _customerFacade = null;
        }

        super.closeDbSession();
    }

    @Override
    protected void renewDbSession() {
        super.renewDbSession();

        if(_customerFacade != null) {
            _customerFacade.setSession(getDbSession());
        }
    }

    @Override
    public Customer getById(int id) {
        renewDbSession();

        return getCustomerFacade().getById(id);
    }

    @Override
    public List<Customer> getList() {
        renewDbSession();

        return getCustomerFacade().getList();
    }

    @Override
    public Pair<Integer, List<Pair<CustomerProperty, String>>> add(CustomerDetailedDto value, AccountDetailedDto updater) {
        renewDbSession();

        if(SessionManager.getInstance().isSessionAvailable(updater) &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Customer entity = mapper.map(value, Customer.class);
            List<Pair<CustomerProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getCustomerFacade().add(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<CustomerProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(CustomerProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Integer, List<Pair<CustomerProperty, String>>> update(CustomerDetailedDto value, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.AUSLEIHE) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKSBENUTZER) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Customer entity = mapper.map(value, Customer.class);
            List<Pair<CustomerProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(getCustomerFacade().update(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<CustomerProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(CustomerProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Boolean, List<Pair<CustomerProperty, String>>> delete(int id, AccountDetailedDto updater) {
        renewDbSession();

        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            CustomerFacade customerFacade = getCustomerFacade();
            List<Pair<CustomerProperty, String>> list = customerFacade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(customerFacade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<CustomerProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(CustomerProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }

    public List<Customer> search(String searchString){
        renewDbSession();

        Integer id = -1;

        try {
            id = Integer.parseInt(searchString);
        } catch (NumberFormatException e) {
        }

        FilterConnector<CustomerProperty, CustomerProperty> connector = new FilterConnector<>(
            new FilterConnector<>(
                    new FilterConnector<>(
                            new Filter<>(searchString, CustomerProperty.ACCOUNT, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                            ConnectorType.OR,
                            new Filter<>(id, CustomerProperty.ID, MatchType.EQUALS, CaseType.NORMAL)
                    ),
                    ConnectorType.OR,
                    new Filter<>(searchString, CustomerProperty.FULL_NAME, MatchType.CONTAINS, CaseType.IGNORE_CASE)
            ),
            ConnectorType.OR,
            new FilterConnector<>(
                    new Filter<>(searchString, CustomerProperty.FIRST_NAME, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                    ConnectorType.OR,
                    new Filter<>(searchString, CustomerProperty.LAST_NAME, MatchType.CONTAINS, CaseType.IGNORE_CASE)
            )
        );

        return getCustomerFacade().filter(connector);
    }
}