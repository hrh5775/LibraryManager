package at.team2.application.helper;

import at.team2.application.enums.Role;
import at.team2.common.dto.detailed.AccountDetailedDto;

public class RoleHelper {
    public static boolean hasRole(AccountDetailedDto account, Role requiredRole) {
        if(account != null) {
            return account.getAccountRole().getKey().equals(requiredRole.name());
        }

        return false;
    }
}