package at.team2.application.entities;

import at.team2.application.helper.DvdHelper;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.CreatorPersonSmallDto;
import at.team2.domain.entities.CreatorPerson;
import at.team2.domain.entities.Dvd;
import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DvdDetailedDtoTest {
    @Test
    public void testConvert() {
        ModelMapper mapper = MapperHelper.getMapper();
        Dvd entity = DvdHelper.getDvd();
        DvdDetailedDto result = mapper.map(entity, DvdDetailedDto.class);
        // @todo: use implicit conversion when possible
        Type type = new TypeToken<List<CreatorPersonSmallDto>>() {}.getType();
        result.setCreatorPersons(mapper.map(entity.getMedia().getCreatorPersons(), type));

        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getPlayingTime(), result.getPlayingTime());
        Assert.assertEquals(entity.getMedia().getCover().length, result.getCover().length);
        Assert.assertEquals(entity.getMedia().getGenre().getId(), result.getGenre().getId());
        Assert.assertEquals(entity.getMedia().getGenre().getName(), result.getGenre().getName());
        Assert.assertEquals(entity.getMedia().getPublisher().getId(), result.getPublisher().getId());
        Assert.assertEquals(entity.getMedia().getPublisher().getName(), result.getPublisher().getName());

        Assert.assertEquals(entity.getMedia().getCreatorPersons().size(), result.getCreatorPersons().size());

        for(int i = 0; i < entity.getMedia().getCreatorPersons().size(); i++) {
            Assert.assertEquals(entity.getMedia().getCreatorPersons().get(i).getId(), result.getCreatorPersons().get(i).getId());
            Assert.assertEquals(entity.getMedia().getCreatorPersons().get(i).getFirstName(), result.getCreatorPersons().get(i).getFirstName());
            Assert.assertEquals(entity.getMedia().getCreatorPersons().get(i).getLastName(), result.getCreatorPersons().get(i).getLastName());
        }

        Assert.assertEquals(entity.getMedia().getId(), result.getMediaId());
        Assert.assertEquals(entity.getMedia().getAvailable(), result.getAvailable());
        Assert.assertEquals(entity.getMedia().getBaseIndex(), result.getBaseIndex());
        Assert.assertEquals(entity.getMedia().getDescription(), result.getDescription());
        Assert.assertEquals(entity.getMedia().getTitle(), result.getTitle());
        Assert.assertEquals(entity.getMedia().getStandardMediaId(), result.getStandardMediaId());
        Assert.assertEquals(entity.getMedia().getPublishedDate(), result.getPublishedDate());

        Assert.assertEquals(entity.getMedia().getPublisher().getId(), result.getPublisher().getId());
        Assert.assertEquals(entity.getMedia().getPublisher().getName(), result.getPublisher().getName());

        Assert.assertEquals(entity.getMedia().getMediaType().getId(), result.getMediaType().getId());
        Assert.assertEquals(entity.getMedia().getMediaType().getName(), result.getMediaType().getName());


        // test the conversion back
        Dvd resultBack = mapper.map(entity, Dvd.class);
        // @todo: use implicit conversion when possible
        Type typeResultBack = new TypeToken<List<CreatorPerson>>() {}.getType();
        resultBack.getMedia().setCreatorPersons(mapper.map(entity.getMedia().getCreatorPersons(), typeResultBack));

        Assert.assertEquals(resultBack.getId(), result.getId());
        Assert.assertEquals(resultBack.getPlayingTime(), result.getPlayingTime());
        Assert.assertEquals(resultBack.getMedia().getCover().length, result.getCover().length);
        Assert.assertEquals(resultBack.getMedia().getGenre().getId(), result.getGenre().getId());
        Assert.assertEquals(resultBack.getMedia().getGenre().getName(), result.getGenre().getName());
        Assert.assertEquals(resultBack.getMedia().getPublisher().getId(), result.getPublisher().getId());
        Assert.assertEquals(resultBack.getMedia().getPublisher().getName(), result.getPublisher().getName());

        Assert.assertEquals(resultBack.getMedia().getCreatorPersons().size(), result.getCreatorPersons().size());

        for(int i = 0; i < resultBack.getMedia().getCreatorPersons().size(); i++) {
            Assert.assertEquals(resultBack.getMedia().getCreatorPersons().get(i).getId(), result.getCreatorPersons().get(i).getId());
            Assert.assertEquals(resultBack.getMedia().getCreatorPersons().get(i).getFirstName(), result.getCreatorPersons().get(i).getFirstName());
            Assert.assertEquals(resultBack.getMedia().getCreatorPersons().get(i).getLastName(), result.getCreatorPersons().get(i).getLastName());
        }

        Assert.assertEquals(resultBack.getMedia().getId(), result.getMediaId());
        Assert.assertEquals(resultBack.getMedia().getAvailable(), result.getAvailable());
        Assert.assertEquals(resultBack.getMedia().getBaseIndex(), result.getBaseIndex());
        Assert.assertEquals(resultBack.getMedia().getDescription(), result.getDescription());
        Assert.assertEquals(resultBack.getMedia().getTitle(), result.getTitle());
        Assert.assertEquals(resultBack.getMedia().getStandardMediaId(), result.getStandardMediaId());
        Assert.assertEquals(resultBack.getMedia().getPublishedDate(), result.getPublishedDate());

        Assert.assertEquals(resultBack.getMedia().getPublisher().getId(), result.getPublisher().getId());
        Assert.assertEquals(resultBack.getMedia().getPublisher().getName(), result.getPublisher().getName());

        Assert.assertEquals(resultBack.getMedia().getMediaType().getId(), result.getMediaType().getId());
        Assert.assertEquals(resultBack.getMedia().getMediaType().getName(), result.getMediaType().getName());
    }
}