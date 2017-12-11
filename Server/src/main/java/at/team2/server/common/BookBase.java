package at.team2.server.common;

import at.team2.application.facade.BookApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.common.dto.small.CreatorPersonSmallDto;
import at.team2.domain.entities.Book;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BookBase {
    private static Type typeSmall = new TypeToken<List<BookSmallDto>>() {}.getType();
    private static Type typeCreatorPersonSmall = new TypeToken<List<CreatorPersonSmallDto>>() {}.getType();
    private BookApplicationFacade _bookFacade;

    private BookApplicationFacade getBookFacade() {
        if(_bookFacade == null) {
            _bookFacade = new BookApplicationFacade();
        }

        return _bookFacade;
    }

    public BookSmallDto doGetBookSmallById(int id) {
        BookApplicationFacade facade = getBookFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Book entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, BookSmallDto.class);
        }

        return null;
    }

    public List<BookSmallDto> doGetBookSmallList() {
        BookApplicationFacade facade = getBookFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getList(), typeSmall);
    }

    public List<BookSmallDto> doGetBookSmallList(String searchString) {
        BookApplicationFacade facade = getBookFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.search(searchString), typeSmall);
    }

    public BookDetailedDto doGetBookDetailedById(int id) {
        BookApplicationFacade facade = getBookFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Book entity = facade.getById(id);

        if(entity != null) {
            BookDetailedDto result = mapper.map(entity, BookDetailedDto.class);
            result.setCreatorPersons(mapper.map(entity.getMedia().getCreatorPersons(), typeCreatorPersonSmall));
            return result;
        }

        return null;
    }

    public void close() {
        if(_bookFacade != null) {
            _bookFacade.closeDbSession();
        }
    }
}