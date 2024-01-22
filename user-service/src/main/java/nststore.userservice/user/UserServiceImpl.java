package nststore.userservice.user;

import lombok.RequiredArgsConstructor;
import nststore.userservice.constant.MessageConstant;
import nststore.userservice.dto.UserCredentialDto;
import nststore.userservice.dto.UserDto;
import nststore.userservice.exception.DuplicateException;
import nststore.userservice.exception.ErrorCode;
import nststore.userservice.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findById(email)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));
  }

  @Override
  public void createUser(UserCredentialDto userDto) {
    if (userRepository.existsById(userDto.getEmail())) {
      throw new DuplicateException(ErrorCode.NOT_NULL, MessageConstant.EMAIL_EXISTED);
    }
    User user = User.builder()
            .email(userDto.getEmail())
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .build();
    userRepository.save(user);
  }

  @Override
  @Transactional
  public Boolean updateUser(UserDto userDto) {
    User user = findByEmail(userDto.getEmail());
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setPhone(userDto.getPhone());
    user.setAddress(userDto.getAddress());
    userRepository.save(user);
    return true;
  }

}
