package at.team2.domain.entities;

import at.team2.domain.interfaces.BaseDomainEntity;
import javafx.util.Pair;
import at.team2.domain.enums.properties.CustomerProperty;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class Customer extends BaseDomainEntity<CustomerProperty> {
    private int _id;
    private String _firstName;
    private String _lastName;
    private Date _birthDate;
    private String _address;
    private String _email;
    private String _phoneNumber;
    private String _bankAccountNumber;
    private Date _dateOfExpiry;
    private Account _account;

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

    public Date getBirthDate() {
        return _birthDate;
    }

    public String getAddress() {
        return _address;
    }

    public String getEmail() {
        return _email;
    }

    public String getPhoneNumber() {
        return _phoneNumber;
    }

    public String getBankAccountNumber() {
        return _bankAccountNumber;
    }

    public Date getDateOfExpiry() {
        return _dateOfExpiry;
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

    public void setPhoneNumber(String phoneNumber) {
        _phoneNumber = phoneNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        _bankAccountNumber = bankAccountNumber;
    }

    public void setDateOfExpiry(Date dateOfExpiry) {
        _dateOfExpiry = dateOfExpiry;
    }

    public void setAccount(Account account) {
        _account = account;
    }

    @Override
    public List<Pair<CustomerProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}