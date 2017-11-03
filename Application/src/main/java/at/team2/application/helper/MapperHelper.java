package at.team2.application.helper;

import at.team2.common.dto.small.*;
import at.team2.domain.entities.Book;
import at.team2.domain.entities.Dvd;
import at.team2.domain.entities.Media;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

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
                        skip().getPublisher().setMediaId(source.getMedia().getID());
                        map().getMediaType().setId(source.getMedia().getMediaType().getID());
                        skip().getMediaType().setMediaId(source.getMedia().getID());
                    }
                });
//
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
                        skip().getPublisher().setMediaId(source.getMedia().getID());
                        map().getMediaType().setId(source.getMedia().getMediaType().getID());
                        skip().getMediaType().setMediaId(source.getMedia().getID());
                    }
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return mapper;
    }

    private static void convertMedia(Media source, MediaSmallDto destination) {
        destination.setMediaId(source.getID());
        destination.setAvailable(source.getAvailable());
        destination.setTitle(source.getTitle());
        destination.setPublishedDate(source.getPublishedDate());
        destination.setDescription(source.getDescription());
        destination.setBaseIndex(source.getBaseIndex());
    }
}