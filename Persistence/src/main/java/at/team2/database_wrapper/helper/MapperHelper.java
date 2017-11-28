package at.team2.database_wrapper.helper;

import at.team2.database_wrapper.entities.LoanEntity;
import at.team2.database_wrapper.entities.MediaCreatorPersonEntity;
import at.team2.database_wrapper.entities.MediaEntity;
import at.team2.domain.entities.CreatorPerson;
import at.team2.domain.entities.Loan;
import at.team2.domain.entities.Media;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MapperHelper {
    private static ModelMapper mapper;

    public static ModelMapper getMapper() {
        if(mapper == null) {
            mapper = new ModelMapper();
            // http://modelmapper.org/user-manual/property-mapping/
            // https://www.programcreek.com/java-api-examples/index.php?api=org.modelmapper.ModelMapper

            mapper.addMappings(new PropertyMap<Loan, LoanEntity>() {
                @Override
                protected void configure() {
                    skip().setMediaMemberByMediaMemberId(null);
                    skip().setCustomerByCustomerId(null);
                    skip().setReminderByReminderId(null);
                }
            });

            mapper.addMappings(new PropertyMap<LoanEntity, Loan>() {
                @Override
                protected void configure() {
                    map().getMediaMember().setId(source.getMediaMemberId());
                    map().getMediaMember().getMedia().setId(source.getMediaMemberByMediaMemberId().getMediaId());
                }
            });
        }

        return mapper;
    }

    public static Media map(MediaEntity entity) {
        if(entity != null) {
            ModelMapper mapper = MapperHelper.getMapper();
            Media value = mapper.map(entity, Media.class);
            Collection<MediaCreatorPersonEntity> list = entity.getMediaCreatorPeopleById();
            List<CreatorPerson> creatorPersons = new LinkedList<>();

            if(list != null) {
                for(MediaCreatorPersonEntity item : list) {
                    creatorPersons.add(mapper.map(item.getCreatorPersonByCreatorPersonId(), CreatorPerson.class));
                }

                value.setCreatorPersons(creatorPersons);
            }

            return value;
        }

        return null;
    }

    public static List<Media> map(List<MediaEntity> entities) {
        if(entities != null) {
            List<Media> list = new LinkedList<>();

            for(MediaEntity entity : entities) {
                list.add(map(entity));
            }

            return list;
        }

        return null;
    }
}
