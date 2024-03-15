package internationalmoneytransferapp.Backend.repositories;

import org.springframework.data.repository.CrudRepository;

import internationalmoneytransferapp.Backend.entities.Beneficiary;

public interface BeneficiaryRepository extends CrudRepository<Beneficiary, Integer> {
	Iterable<Beneficiary> findByUserId(Integer userId);
}
