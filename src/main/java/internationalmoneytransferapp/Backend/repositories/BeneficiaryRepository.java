package internationalmoneytransferapp.Backend.repositories;

import org.springframework.data.repository.CrudRepository;

import internationalmoneytransferapp.Backend.entities.SavedBeneficiary;

public interface BeneficiaryRepository extends CrudRepository<SavedBeneficiary, Integer> {
	Iterable<SavedBeneficiary> findAllBySavedById(Integer userId);
}
