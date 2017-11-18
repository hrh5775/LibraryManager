package at.team2.application.facade;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.MapperHelper;
import at.team2.application.helper.RoleHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.enums.CaseType;
import at.team2.database_wrapper.enums.ConnectorType;
import at.team2.database_wrapper.enums.MatchType;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.BookFacade;
import at.team2.domain.entities.Book;
import at.team2.domain.enums.properties.BookProperty;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

public class BookApplicationFacade extends BaseApplicationFacade<Book, BookDetailedDto, AccountDetailedDto, BookProperty> {
    private static BookApplicationFacade _instance;
    private BookFacade _facade;

    public static BookApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new BookApplicationFacade();
            _instance._facade = new BookFacade();
        }

        return _instance;
    }

    private BookApplicationFacade() {
    }

    @Override
    public Book getById(int id) {
        return _facade.getById(id);
    }

    @Override
    public List<Book> getList() {
        return _facade.getList();
    }

    @Override
    public void closeSession() {
        _facade.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<BookProperty, String>>> add(BookDetailedDto value, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Book entity = mapper.map(value, Book.class);
            List<Pair<BookProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<BookProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(BookProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Integer, List<Pair<BookProperty, String>>> update(BookDetailedDto value, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            ModelMapper mapper = MapperHelper.getMapper();
            Book entity = mapper.map(value, Book.class);
            List<Pair<BookProperty, String>> list = entity.validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(0, new LinkedList<>());
        } else {
            List<Pair<BookProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(BookProperty.ID, "permission denied"));
            return new Pair<>(0, list);
        }
    }

    @Override
    public Pair<Boolean, List<Pair<BookProperty, String>>> delete(int id, AccountDetailedDto updater) {
        updater = SessionManager.getInstance().getSession(updater);

        if(updater != null &&
                (RoleHelper.hasRole(updater, Role.ADMIN) ||
                RoleHelper.hasRole(updater, Role.DATENPFLEGER) ||
                RoleHelper.hasRole(updater, Role.OPERATOR) ||
                RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            List<Pair<BookProperty, String>> list = _facade.getById(id).validate();

            if (list.size() == 0) {
                return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
            }

            return new Pair<>(false, new LinkedList<>());
        } else {
            List<Pair<BookProperty, String>> list = new LinkedList<>();
            list.add(new Pair<>(BookProperty.ID, "permission denied"));
            return new Pair<>(false, list);
        }
    }

    public List<Book> search(String searchString){
        /*String[] items = searchString.split(" ");
        FilterConnector<BookProperty, BookProperty> connector = null;
        FilterConnector<BookProperty, BookProperty> tmpConnector;
        FilterConnector<BookProperty, BookProperty> lastConnector = null;

        for(int i = 0; i < items.length; i++) {
            if(connector == null) {
                connector = getFilterConnector(items[i]);
                lastConnector = connector;
            } else {
                tmpConnector =  getFilterConnector(items[i]);
                lastConnector.setRightFilterConnector(ConnectorType.AND, tmpConnector);
                lastConnector = tmpConnector;
            }
        }*/

        return _facade.filter(getFilterConnector(searchString));
    }

    private FilterConnector<BookProperty, BookProperty> getFilterConnector(String searchString) {
        FilterConnector<BookProperty, BookProperty> connector = new FilterConnector<>(
                new FilterConnector<>(
                        new FilterConnector<>(
                                new Filter<>(searchString, BookProperty.MEDIA__STANDARD_MEDIA_ID, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                                ConnectorType.OR,
                                new Filter<>(searchString, BookProperty.MEDIA__TITLE, MatchType.CONTAINS, CaseType.IGNORE_CASE)
                        ),
                        ConnectorType.OR,
                        new FilterConnector<>(
                                new Filter<>(searchString, BookProperty.MEDIA__DESCRIPTION, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                                ConnectorType.OR,
                                new Filter<>(searchString, BookProperty.MEDIA__PUBLISHER, MatchType.CONTAINS, CaseType.IGNORE_CASE)
                        )
                ),
                ConnectorType.OR,
                new FilterConnector<>(
                        new FilterConnector<>(
                                new Filter<>(searchString, BookProperty.MEDIA__PUBLISHER_TYPE, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                                ConnectorType.OR,
                                new Filter<>(searchString, BookProperty.MEDIA__CREATOR_PERSON, MatchType.CONTAINS, CaseType.IGNORE_CASE)
                        ),
                        ConnectorType.OR,
                        new FilterConnector<>(
                                new Filter<>(searchString, BookProperty.MEDIA__CREATOR_TYPE, MatchType.CONTAINS, CaseType.IGNORE_CASE),
                                ConnectorType.OR,
                                new Filter<>(searchString, BookProperty.MEDIA__GENRE, MatchType.CONTAINS, CaseType.IGNORE_CASE)
                        )
                )
        );

        return connector;
    }
}