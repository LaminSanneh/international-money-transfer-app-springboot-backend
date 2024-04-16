package internationalmoneytransferapp.Backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import internationalmoneytransferapp.Backend.dto.TransactionDTO;
import internationalmoneytransferapp.Backend.entities.Recipient;
import internationalmoneytransferapp.Backend.entities.SavedBeneficiary;
import internationalmoneytransferapp.Backend.entities.Transaction;
import internationalmoneytransferapp.Backend.entities.UserEntity;
import internationalmoneytransferapp.Backend.repositories.SavedBeneficiaryRepository;
import internationalmoneytransferapp.Backend.repositories.TransactionRepository;
import internationalmoneytransferapp.Backend.repositories.UserRepository;
import internationalmoneytransferapp.Backend.services.exceptions.CannotCreateTransactionException;

@Service
public class TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	SavedBeneficiaryRepository savedBeneficiaryRepository;

	@Autowired
	UserRepository userRepository;

	public Iterable<Transaction> getAllTransactions(String username) {
		return transactionRepository.findAllBySenderUsername(username);
	}

	public Transaction createTransaction(TransactionDTO transactionDTO) {
		boolean paymentSuccess = true;

		Integer savedBeneficiaryId = transactionDTO.getRecipient().getSaved_beneficiary_id();
		Recipient transactionRecipient = new Recipient();

		Integer senderId = transactionDTO.getSenderId();

		if (savedBeneficiaryId != null) {
			Optional<SavedBeneficiary> savedBeneficiary = savedBeneficiaryRepository.findById(savedBeneficiaryId);

			if (savedBeneficiary.isEmpty()) {
				throw new CannotCreateTransactionException("Saved Beneficiary does not exist with given id");
			}

			transactionRecipient.setName(savedBeneficiary.get().getName());
		} else {
			transactionRecipient.setName(transactionDTO.getRecipient().getName());
		}

		UserEntity senderEntity;
		if (senderId != null) {
			Optional<UserEntity> sender = userRepository.findById(senderId);

			if (sender.isEmpty()) {
				throw new CannotCreateTransactionException("Sender does not exist with given id");
			}

			senderEntity = sender.get();
		} else {
			throw new CannotCreateTransactionException("Sender id not given");
		}

		Transaction transaction = new Transaction();
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setCurrency(transactionDTO.getCurrency());
		transaction.setDate(transactionDTO.getDate());
		transaction.setRecipient(transactionRecipient);

		transaction.setSender(senderEntity);

		Transaction createdTransaction = transactionRepository.save(transaction);
		// userRepository.save(senderEntity);

		if (paymentSuccess != true) {
			throw new CannotCreateTransactionException("Payment processing failed");
		}

		// return transaction;
		return createdTransaction;
	}
}
