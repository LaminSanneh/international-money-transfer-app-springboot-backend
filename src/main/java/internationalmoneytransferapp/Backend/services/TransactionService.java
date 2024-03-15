package internationalmoneytransferapp.Backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import internationalmoneytransferapp.Backend.entities.Transaction;
import internationalmoneytransferapp.Backend.repositories.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;

	public Iterable<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	public Transaction createTransaction(Transaction transaction) {
		boolean paymentSuccess = true;
		
		if (paymentSuccess) {
			return transactionRepository.save(transaction);
		} else {
			throw new RuntimeException("Payment processing failed");
		}
	}
}
