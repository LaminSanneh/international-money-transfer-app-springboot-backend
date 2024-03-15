package internationalmoneytransferapp.Backend.repositories;

import org.springframework.data.repository.CrudRepository;

import internationalmoneytransferapp.Backend.entities.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {

}
