package at.team2.application.helper;

import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.small.*;
import at.team2.domain.entities.Book;
import at.team2.domain.entities.Dvd;
import at.team2.domain.entities.Media;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MapperHelper {
    private static ModelMapper mapper;

    public static ModelMapper getMapper() {
        if(mapper == null) {
            mapper = new ModelMapper();
            // http://modelmapper.org/user-manual/property-mapping/
            // https://www.programcreek.com/java-api-examples/index.php?api=org.modelmapper.ModelMapper

            try {
//                TypeMap<Media, MediaSmallDto> mediaMediaSmallDtoTypeMap = mapper.createTypeMap(Media.class, MediaSmallDto.class).addMappings(x -> {
//                    x.map(Media::getID, MediaSmallDto::setMediaId);
//                    x.map(Media::getStandardMediaId, MediaSmallDto::setStandardMediaId);
//                    x.map(Media::getAvailable, MediaSmallDto::setAvailable);
//                    x.map(Media::getTitle, MediaSmallDto::setTitle);
//                    x.map(Media::getPublishedDate, MediaSmallDto::setPublishedDate);
//                    x.map(Media::getDescription, MediaSmallDto::setDescription);
//                    x.map(Media::getBaseIndex, MediaSmallDto::setBaseIndex);
//                    x.map(Media::getMediaType, MediaSmallDto::setMediaType);
//                    x.map(Media::getPublisher, MediaSmallDto::setPublisher);
//                });

                mapper.addMappings(new PropertyMap<Book, BookSmallDto>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getID());
                        map().setMediaId(source.getMedia().getID());
                        map().setAvailable(source.getMedia().getAvailable());
                        map().setTitle(source.getMedia().getTitle());
                        map().setPublishedDate(source.getMedia().getPublishedDate());
                        map().setDescription(source.getMedia().getDescription());
                        map().setBaseIndex(source.getMedia().getBaseIndex());
                        map().getPublisher().setId(source.getMedia().getPublisher().getID());
                        map().getPublisher().setName(source.getMedia().getPublisher().getName());
                        map().getMediaType().setId(source.getMedia().getMediaType().getID());
                    }
                });

                mapper.addMappings(new PropertyMap<Dvd, DvdSmallDto>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getID());
                        map().setMediaId(source.getMedia().getID());
                        map().setAvailable(source.getMedia().getAvailable());
                        map().setTitle(source.getMedia().getTitle());
                        map().setPublishedDate(source.getMedia().getPublishedDate());
                        map().setDescription(source.getMedia().getDescription());
                        map().setBaseIndex(source.getMedia().getBaseIndex());
                        map().getPublisher().setId(source.getMedia().getPublisher().getID());
                        map().getPublisher().setName(source.getMedia().getPublisher().getName());
                        map().getMediaType().setId(source.getMedia().getMediaType().getID());
                    }
                });

                mapper.addMappings(new PropertyMap<Dvd, DvdDetailedDto>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getID());
                        map().setPlayingTime(source.getPlayingTime());
                        map().setCover(source.getMedia().getCover());
                        map().getGenre().setId(source.getMedia().getGenre().getID());
                        map().getGenre().setName(source.getMedia().getGenre().getName());

                        Type type = new TypeToken<List<CreatorPersonSmallDto>>() {}.getType();
                        map(source.getMedia().getCreatorPersons(), type); // how to convert a list type with missing mapping

                        map().setMediaId(source.getMedia().getID());
                        map().setAvailable(source.getMedia().getAvailable());
                        map().setTitle(source.getMedia().getTitle());
                        map().setPublishedDate(source.getMedia().getPublishedDate());
                        map().setDescription(source.getMedia().getDescription());
                        map().setBaseIndex(source.getMedia().getBaseIndex());
                        map().getPublisher().setId(source.getMedia().getPublisher().getID());
                        map().getPublisher().setName(source.getMedia().getPublisher().getName());
                        map().getMediaType().setId(source.getMedia().getMediaType().getID());
                    }
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return mapper;
    }
}