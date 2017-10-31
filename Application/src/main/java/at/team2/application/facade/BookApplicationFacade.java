package at.team2.application.facade;

import at.team2.application.helper.MapperHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.connector.dto.detailed.BookDetailedDto;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.BookFacade;
import at.team2.domain.entities.Book;
import at.team2.domain.enums.properties.BookProperty;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

public class BookApplicationFacade extends BaseApplicationFacade<Book, BookDetailedDto, BookProperty> {
    private static BookApplicationFacade _instance;
    private BookFacade _facade = new BookFacade();

    public static BookApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new BookApplicationFacade();
        }

        return _instance;
    }

    private BookApplicationFacade() {
    }

    @Override
    public Book getByID(int id) {
        return _instance.getByID(id);
    }

    @Override
    public List<Book> getList() {
        return _instance.getList();
    }

    @Override
    public void closeSession() {
        _instance.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<BookProperty, String>>> add(BookDetailedDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        Book entity = mapper.map(value, Book.class);
        List<Pair<BookProperty, String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT), list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Integer, List<Pair<BookProperty, String>>> update(BookDetailedDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        Book entity = mapper.map(value, Book.class);
        List<Pair<BookProperty, String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT), list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Boolean, List<Pair<BookProperty, String>>> delete(int id) {
        List<Pair<BookProperty, String>> list = _instance.getByID(id).validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
        }

        return new Pair<>(false, new LinkedList<>());
    }
}