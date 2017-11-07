package at.team2.application.helper;

import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.detailed.MediaMemberDetailedDto;
import at.team2.common.dto.small.*;
import at.team2.domain.entities.Book;
import at.team2.domain.entities.Dvd;
import at.team2.domain.entities.MediaMember;
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
                        map().getMediaType().setId(source.getMedia().getMediaType().getID());
                    }
                });

                mapper.addMappings(new PropertyMap<BookSmallDto, Book>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().getMedia().setId(source.getMediaId());
                        map().getMedia().setAvailable(source.getAvailable());
                        map().getMedia().setTitle(source.getTitle());
                        map().getMedia().setPublishedDate(source.getPublishedDate());
                        map().getMedia().setDescription(source.getDescription());
                        map().getMedia().setBaseIndex(source.getBaseIndex());
                        map().getMedia().getPublisher().setId(source.getPublisher().getId());
                        map().getMedia().getPublisher().setName(source.getPublisher().getName());
                        map().getMedia().getMediaType().setId(source.getMediaType().getId());
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

                mapper.addMappings(new PropertyMap<DvdSmallDto, Dvd>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().getMedia().setId(source.getId());
                        map().getMedia().setAvailable(source.getAvailable());
                        map().getMedia().setTitle(source.getTitle());
                        map().getMedia().setPublishedDate(source.getPublishedDate());
                        map().getMedia().setDescription(source.getDescription());
                        map().getMedia().setBaseIndex(source.getBaseIndex());
                        map().getMedia().getPublisher().setId(source.getPublisher().getId());
                        map().getMedia().getPublisher().setName(source.getPublisher().getName());
                        map().getMedia().getMediaType().setId(source.getMediaType().getId());
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

                        // somehow we can't convert creatorPersons

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

                mapper.addMappings(new PropertyMap<DvdDetailedDto, Dvd>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setPlayingTime(source.getPlayingTime());
                        map().getMedia().setCover(source.getCover());
                        map().getMedia().getGenre().setId(source.getGenre().getId());
                        map().getMedia().getGenre().setName(source.getGenre().getName());

                        // somehow we can't convert creatorPersons

                        map().getMedia().setId(source.getId());
                        map().getMedia().setAvailable(source.getAvailable());
                        map().getMedia().setTitle(source.getTitle());
                        map().getMedia().setPublishedDate(source.getPublishedDate());
                        map().getMedia().setDescription(source.getDescription());
                        map().getMedia().setBaseIndex(source.getBaseIndex());
                        map().getMedia().getPublisher().setId(source.getPublisher().getId());
                        map().getMedia().getPublisher().setName(source.getPublisher().getName());
                        map().getMedia().getMediaType().setId(source.getMediaType().getId());
                    }
                });

                mapper.addMappings(new PropertyMap<Book, BookDetailedDto>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getID());
                        map().setEdition(source.getEdition());
                        map().setCover(source.getMedia().getCover());
                        map().getGenre().setId(source.getMedia().getGenre().getID());
                        map().getGenre().setName(source.getMedia().getGenre().getName());

                        // somehow we can't convert creatorPersons

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

                mapper.addMappings(new PropertyMap<BookDetailedDto, Book>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setEdition(source.getEdition());
                        map().getMedia().setCover(source.getCover());
                        map().getMedia().getGenre().setId(source.getGenre().getId());
                        map().getMedia().getGenre().setName(source.getGenre().getName());

                        // somehow we can't convert creatorPersons

                        map().getMedia().setId(source.getId());
                        map().getMedia().setAvailable(source.getAvailable());
                        map().getMedia().setTitle(source.getTitle());
                        map().getMedia().setPublishedDate(source.getPublishedDate());
                        map().getMedia().setDescription(source.getDescription());
                        map().getMedia().setBaseIndex(source.getBaseIndex());
                        map().getMedia().getPublisher().setId(source.getPublisher().getId());
                        map().getMedia().getPublisher().setName(source.getPublisher().getName());
                        map().getMedia().getMediaType().setId(source.getMediaType().getId());
                    }
                });

                mapper.addMappings(new PropertyMap<MediaMember, MediaMemberSmallDto>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getID());
                        map().setExtendedIndex(source.getExtendedIndex());
                        map().getMedia().setMediaId(source.getMedia().getID());
                        map().getMedia().setStandardMediaId(source.getMedia().getStandardMediaId());
                    }
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return mapper;
    }
}