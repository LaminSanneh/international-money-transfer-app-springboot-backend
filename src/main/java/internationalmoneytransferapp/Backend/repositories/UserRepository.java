package internationalmoneytransferapp.Backend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import internationalmoneytransferapp.Backend.entities.UserEntity;

@Repository
public interface UserRepository  extends CrudRepository<UserEntity, Integer>{
    Optional<UserEntity> findByUsername(String name);
    boolean existsByUsername(String name);
}
