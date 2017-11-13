package at.team2.server.remote;

import at.team2.application.facade.BookApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.CreatorPersonSmallDto;
import at.team2.common.interfaces.BookRemoteObjectInf;
import at.team2.domain.entities.Book;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BookRemoteObject extends UnicastRemoteObject implements BookRemoteObjectInf {
    private static Type typeSmall = new TypeToken<List<BookSmallDto>>() {}.getType();
    private static Type typeCreatorPersonSmall = new TypeToken<List<CreatorPersonSmallDto>>() {}.getType();

    public BookRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public BookSmallDto getBookSmallById(int id) throws RemoteException {
        BookApplicationFacade facade = BookApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();
        Book entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, BookSmallDto.class);
        }

        return null;
    }

    @Override
    public List<BookSmallDto> getBookSmallList() throws RemoteException {
        BookApplicationFacade facade = BookApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getList(), typeSmall);
    }

    @Override
    public List<BookSmallDto> getBookSmallList(String searchString) throws RemoteException {
        BookApplicationFacade facade =  BookApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.search(searchString), typeSmall);
    }

    @Override
    public BookDetailedDto getBookDetailedById(int id) throws RemoteException {
        BookApplicationFacade facade = BookApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();
        Book entity = facade.getById(id);

        if(entity != null) {
            BookDetailedDto result = mapper.map(entity, BookDetailedDto.class);
            result.setCreatorPersons(mapper.map(entity.getMedia().getCreatorPersons(), typeCreatorPersonSmall));
            return result;
        }

        return null;
    }
}