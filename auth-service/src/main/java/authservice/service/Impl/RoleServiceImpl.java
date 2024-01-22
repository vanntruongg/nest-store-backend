package authservice.service.Impl;

import authservice.constant.MessageConstant;
import authservice.entity.Role;
import authservice.exception.ErrorCode;
import authservice.exception.NotFoundException;
import authservice.repository.RoleRepository;
import authservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository repository;

  @Override
  public Role getByName(String roleName) {
    return repository.findByRoleName(roleName).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ROLE_NOT_FOUND));
  }

  @Override
  public List<Role> getAllRoleByIds(List<Integer> ids) {
    return repository.findAllById(ids);
  }
}
