package at.team2.domain.entities;

import at.team2.domain.enums.properties.MediaTypeProperty;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class MediaType extends BaseDomainEntity<MediaTypeProperty>  {
    private int _id;
    private String _name;
    private LoanCondition _loanCondition;

    @Override
    public int getID() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public LoanCondition getLoanCondition() {
        return _loanCondition;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setLoanCondition(LoanCondition loanCondition) {
        _loanCondition = loanCondition;
    }

    @Override
    public List<Pair<MediaTypeProperty, String>> validate() {
        // @todo: implement
        return new LinkedList<>();
    }
}