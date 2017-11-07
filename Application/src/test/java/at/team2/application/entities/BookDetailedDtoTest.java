package at.team2.application.entities;

import at.team2.application.helper.BookHelper;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.small.CreatorPersonSmallDto;
import at.team2.domain.entities.Book;
import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BookDetailedDtoTest {
    @Test
    public void testConvert() {
        ModelMapper mapper = MapperHelper.getMapper();
        Book entity = BookHelper.getBook();
        BookDetailedDto result = mapper.map(entity, BookDetailedDto.class);
        // @todo: use implicit conversion when possible
        Type type = new TypeToken<List<CreatorPersonSmallDto>>() {}.getType();
        result.setCreatorPersons(mapper.map(entity.getMedia().getCreatorPersons(), type));

        Assert.assertEquals(entity.getID(), result.getId());
        Assert.assertEquals(entity.getEdition(), result.getEdition());
        Assert.assertEquals(entity.getMedia().getCover().length, result.getCover().length);
        Assert.assertEquals(entity.getMedia().getGenre().getID(), result.getGenre().getId());
        Assert.assertEquals(entity.getMedia().getGenre().getName(), result.getGenre().getName());
        Assert.assertEquals(entity.getMedia().getPublisher().getID(), result.getPublisher().getId());
        Assert.assertEquals(entity.getMedia().getPublisher().getName(), result.getPublisher().getName());

        Assert.assertEquals(entity.getMedia().getCreatorPersons().size(), result.getCreatorPersons().size());

        for(int i = 0; i < entity.getMedia().getCreatorPersons().size(); i++) {
            Assert.assertEquals(entity.getMedia().getCreatorPersons().get(i).getID(), result.getCreatorPersons().get(i).getId());
            Assert.assertEquals(entity.getMedia().getCreatorPersons().get(i).getFirstName(), result.getCreatorPersons().get(i).getFirstName());
            Assert.assertEquals(entity.getMedia().getCreatorPersons().get(i).getLastName(), result.getCreatorPersons().get(i).getLastName());
        }

        Assert.assertEquals(entity.getMedia().getID(), result.getMediaId());
        Assert.assertEquals(entity.getMedia().getAvailable(), result.getAvailable());
        Assert.assertEquals(entity.getMedia().getBaseIndex(), result.getBaseIndex());
        Assert.assertEquals(entity.getMedia().getDescription(), result.getDescription());
        Assert.assertEquals(entity.getMedia().getTitle(), result.getTitle());
        Assert.assertEquals(entity.getMedia().getStandardMediaId(), result.getStandardMediaId());
        Assert.assertEquals(entity.getMedia().getPublishedDate(), result.getPublishedDate());

        Assert.assertEquals(entity.getMedia().getPublisher().getID(), result.getPublisher().getId());
        Assert.assertEquals(entity.getMedia().getPublisher().getName(), result.getPublisher().getName());

        Assert.assertEquals(entity.getMedia().getMediaType().getID(), result.getMediaType().getId());
        Assert.assertEquals(entity.getMedia().getMediaType().getName(), result.getMediaType().getName());
    }
}