package at.team2.application.entities;

import at.team2.application.helper.DvdHelper;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.domain.entities.Dvd;
import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;

public class DvdDetailedDtoTest {
    @Test
    public void testConvert() {
        ModelMapper mapper = MapperHelper.getMapper();
        Dvd entity = DvdHelper.getDvd();
        DvdDetailedDto result = mapper.map(entity, DvdDetailedDto.class);

        Assert.assertEquals(entity.getID(), result.getId());
        Assert.assertEquals(entity.getPlayingTime(), result.getPlayingTime());
        //Assert.assertEquals(entity.getMedia().getCover(), result.getCover());
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