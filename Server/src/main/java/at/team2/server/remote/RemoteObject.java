package at.team2.server.remote;

import at.team2.application.helper.MapperHelper;
import at.team2.connector.configuration.ConnectionInfo;
import at.team2.connector.dto.small.BookSmallDto;
import at.team2.connector.interfaces.RemoteObjectInf;
import at.team2.database_wrapper.facade.BookFacade;
import at.team2.domain.entities.Book;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RemoteObject extends UnicastRemoteObject implements RemoteObjectInf {
    public RemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public int getVersion() {
        return ConnectionInfo.version;
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
