package nststore.userservice.user;


import nststore.userservice.dto.UserCredentialDto;
import nststore.userservice.dto.UserDto;

import java.util.List;

public interface UserService {
  List<User> findAll();

  User findByEmail(String email);

  Boolean updateUser(UserDto userDto);

  void createUser(UserCredentialDto userCredentialDto);
}
