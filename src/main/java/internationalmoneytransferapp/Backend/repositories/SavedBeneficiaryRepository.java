package internationalmoneytransferapp.Backend.repositories;

import org.springframework.data.repository.CrudRepository;

import internationalmoneytransferapp.Backend.entities.SavedBeneficiary;

public interface SavedBeneficiaryRepository extends CrudRepository<SavedBeneficiary, Integer> {

}
