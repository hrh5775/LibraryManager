package at.team2.common.interfaces.ejb;

import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.BookSmallDto;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface BookRemote {
    public BookSmallDto getBookSmallById(int id);
    public List<BookSmallDto> getBookSmallList();
    public List<BookSmallDto> getBookSmallList(String searchString);

    public BookDetailedDto getBookDetailedById(int id);
}