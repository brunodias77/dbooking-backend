package com.brunodias.dbooking.application.services.interfaces;

import com.brunodias.dbooking.domain.entities.Role;
import com.brunodias.dbooking.domain.entities.User;

import java.util.List;

public interface IRoleService {

    List<Role> getRoles();
    Role createRole(Role theRole);
    void deleteRole(Long id);
    Role findByName(String name);
    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
