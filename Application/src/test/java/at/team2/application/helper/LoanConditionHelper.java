package at.team2.application.helper;

import at.team2.domain.entities.LoanCondition;

public class LoanConditionHelper {
    public static LoanCondition getLoanCondition() {
        LoanCondition entity = new LoanCondition();
        entity.setId(14525627);
        entity.setExtension(4454);
        entity.setLoanTerm(123247);

        return entity;
    }
}
