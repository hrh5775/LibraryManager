package at.team2.application.facade;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.MapperHelper;
import at.team2.application.helper.RoleHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.CustomerDetailedDto;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.CustomerFacade;
import at.team2.domain.entities.Customer;
import at.team2.domain.enums.properties.CustomerProperty;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

public class CustomerApplicationFacade extends BaseApplicationFacade<Customer, CustomerDetailedDto, AccountDetailedDto, CustomerProperty> {
    private static CustomerApplicationFacade _instance;
    private CustomerFacade _facade;

    public static CustomerApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new CustomerApplicationFacade();
            _instance._facade = new CustomerFacade();
        }

        return _instance;
    }

    private CustomerApplicationFacade() {
    }

    @Override
    public Customer getById(int id) {
        return _facade.getById(id);
    }

    @Override
    public List<Customer> getList() {
        return _facade.getList();
    }

    @Override
    public void closeSession() {
        _facade.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<CustomerProperty, String>>> add(CustomerDetailedDto value, AccountDetailedDto updater) {
        if(SessionManager.getInstance().isSessionAvailable(updater) &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Customer entity = mapper.map(value, Customer.class);
            List<Pair<CustomerProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT), list);
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
        if(SessionManager.getInstance().isSessionAvailable(updater) &&
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
                return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT), list);
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
        if(SessionManager.getInstance().isSessionAvailable(updater) &&
                (RoleHelper.hasRole(updater, Role.ADMIN))) {
            List<Pair<CustomerProperty, String>> list = _facade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<CustomerProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(CustomerProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }
}