package at.team2.domain.logic;

import at.team2.domain.enums.EntityType;
import at.team2.domain.interfaces.EntityLogic;

import java.util.HashMap;

public class LogicFactory {
    private static HashMap<EntityType, EntityLogic> _logicList;

    private LogicFactory() {
    }

    public static EntityLogic getLogic(EntityType entity) {
        if(_logicList == null) {
            initialize();
        }

        return _logicList.get(entity);
    }

    private static void initialize() {
        _logicList = new HashMap<>();
        //_logicList.put(EntityType.EVENT_DUTY, new EventDutyLogic());
    }
}
