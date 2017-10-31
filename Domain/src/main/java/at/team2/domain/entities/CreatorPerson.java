package at.team2.domain.entities;

import at.team2.domain.enums.properties.CreatorPersonProperty;
import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class CreatorPerson extends BaseDomainEntity<CreatorPersonProperty> {
    private int _id;
    private String _firstName;
    private String _lastName;
    private String _academicTitle;
    private Date _birthDate;
    private CreatorType _creatorType;

    @Override
    public int getID() {
        return _id;
    }

    public String getFirstName() {
        return _firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public String getAcademicTitle() {
        return _academicTitle;
    }

    public Date getBirthDate() {
        return _birthDate;
    }

    public CreatorType getCreatorType() {
        return _creatorType;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public void setLastName(String lastName) {
        _lastName = lastName;
    }

    public void setAcademicTitle(String academicTitle) {
        _academicTitle = _academicTitle;
    }

    public void setBirthDate(Date birthDate) {
        _birthDate = birthDate;
    }

    public void setCreatorType(CreatorType creatorType) {
        _creatorType = creatorType;
    }

    @Override
    public List<Pair<CreatorPersonProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}