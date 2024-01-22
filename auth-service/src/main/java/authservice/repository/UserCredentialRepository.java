package authservice.repository;

import authservice.entity.UserCredential;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends MongoRepository<UserCredential, Integer> {
  Optional<UserCredential> findByEmail(String email);
  Boolean existsByEmail(String email);

}
