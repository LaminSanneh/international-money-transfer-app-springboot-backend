package internationalmoneytransferapp.Backend.repositories;

import org.springframework.data.repository.CrudRepository;

import internationalmoneytransferapp.Backend.entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

}
