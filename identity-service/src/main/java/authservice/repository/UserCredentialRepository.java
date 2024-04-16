package authservice.repository;

import authservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends MongoRepository<User, Integer> {
  Optional<User> findByEmail(String email);
  Boolean existsByEmail(String email);

  Optional<User> findByTokens_TokenValue(String tokenValue);
  long count();

}
