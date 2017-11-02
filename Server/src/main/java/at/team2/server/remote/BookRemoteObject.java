package at.team2.server.remote;

import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.interfaces.BookRemoteObjectInf;
import at.team2.database_wrapper.facade.BookFacade;
import at.team2.domain.entities.Book;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BookRemoteObject extends UnicastRemoteObject implements BookRemoteObjectInf {
    public BookRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public BookSmallDto getBookSmallById(int id) throws RemoteException {
        BookFacade facade = new BookFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Book book = facade.getById(id);

        return mapper.map(book, BookSmallDto.class);
    }

    @Override
    public List<BookSmallDto> getBookSmallList() {
        BookFacade facade = new BookFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Type type = new TypeToken<List<BookSmallDto>>() {}.getType();

        return mapper.map(facade.getList(), type);
    }
}