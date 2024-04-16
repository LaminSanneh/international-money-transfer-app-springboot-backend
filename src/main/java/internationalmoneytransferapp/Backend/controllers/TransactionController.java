package internationalmoneytransferapp.Backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internationalmoneytransferapp.Backend.dto.TransactionDTO;
import internationalmoneytransferapp.Backend.entities.Transaction;
import internationalmoneytransferapp.Backend.repositories.TransactionRepository;
import internationalmoneytransferapp.Backend.services.TransactionService;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/transactions")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@Autowired
	TransactionRepository transactionRepository;

	@GetMapping
	// @PreAuthorize("hasRole('USER')")
	public ResponseEntity<Iterable<Transaction>> getTransactions(
		// Principal principal
		) {
		return new ResponseEntity<>(transactionService.getAllTransactions(
			// principal.getName()
			"lamin.evra@gmail.com"
			), HttpStatus.OK);
	}

	@PostMapping
	// @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) {

        Transaction createdTransaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }
}
