import org.junit.Test;
import team2.database_wrapper.facade.BookFacade;
import team2.domain.entities.Book;

import javax.naming.NamingException;
import java.util.List;

public class BookFacadeTest {
    @Test
    public void getList() throws NamingException {
        BookFacade facade = new BookFacade();
        List<Book> list = facade.getList();
        list.size();
    }
}