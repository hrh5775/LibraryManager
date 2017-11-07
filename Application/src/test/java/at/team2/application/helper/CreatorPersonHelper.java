package at.team2.application.helper;

import at.team2.domain.entities.CreatorPerson;

import java.sql.Date;
import java.util.Calendar;

public class CreatorPersonHelper {
    public static CreatorPerson getCreatorPerson() {
        CreatorPerson entity = new CreatorPerson();
        entity.setId(5445);
        entity.setFirstName("blaXY");
        entity.setLastName("klasdfklmygod");
        entity.setAcademicTitle("nothing");
        entity.setBirthDate(new Date(Calendar.getInstance().getTime().getTime() + 5));
        entity.setCreatorType(CreatorTypeHelper.getCreatorType());

        return entity;
    }

    public static CreatorPerson getCreatorPerson2() {
        CreatorPerson entity = new CreatorPerson();
        entity.setId(5445);
        entity.setFirstName("blaXY2");
        entity.setLastName("klasdfkl2mygod");
        entity.setAcademicTitle("nothi2ng");
        entity.setBirthDate(new Date(Calendar.getInstance().getTime().getTime() + 7));
        entity.setCreatorType(CreatorTypeHelper.getCreatorType2());

        return entity;
    }
}
