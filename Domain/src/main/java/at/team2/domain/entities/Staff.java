package at.team2.domain.entities;

import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;
import at.team2.domain.enums.properties.StaffProperty;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class Staff extends BaseDomainEntity<StaffProperty> {
    private int _id;
    private String _firstName;
    private String _lastName;
    private Date _birthDate;
    private String _address;
    private String _email;
    private Account _account;

    @Override
    public int getId() {
        return _id;
    }

    public String getFirstName() {
        return _firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public Date getBirthDate() {
        return _birthDate;
    }

    public String getAddress() {
        return _address;
    }

    public String getEmail() {
        return _email;
    }

    public Account getAccount() {
        return _account;
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

    public void setBirthDate(Date birthDate) {
        _birthDate = birthDate;
    }

    public void setAddress(String address) {
        _address = address;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public void setAccount(Account account) {
        _account = account;
    }

    @Override
    public List<Pair<StaffProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}