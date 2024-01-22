package authservice.service;

import authservice.entity.Role;

import java.util.List;

public interface RoleService {

  Role getByName(String roleName);
  List<Role> getAllRoleByIds(List<Integer> ids);
}
