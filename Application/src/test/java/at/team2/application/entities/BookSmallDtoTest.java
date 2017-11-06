package at.team2.application.entities;

import at.team2.application.helper.*;
import at.team2.common.dto.small.BookSmallDto;
import at.team2.domain.entities.Book;
import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;

public class BookSmallDtoTest {
    @Test
    public void testConvert() {
        ModelMapper mapper = MapperHelper.getMapper();
        Book entity = BookHelper.getBook();
        BookSmallDto result = mapper.map(entity, BookSmallDto.class);

        Assert.assertEquals(entity.getID(), result.getId());
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