package at.team2.application.helper;

import at.team2.domain.entities.MediaType;

public class MediaTypeHelper {
    public static MediaType getMediaType() {
        MediaType entity = new MediaType();
        entity.setId(254512151);
        entity.setName("bane45454");
        entity.setLoanCondition(LoanConditionHelper.getLoanCondition());

        return entity;
    }
}
