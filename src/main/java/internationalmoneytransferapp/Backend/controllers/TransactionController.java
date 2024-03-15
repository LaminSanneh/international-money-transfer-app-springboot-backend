package internationalmoneytransferapp.Backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internationalmoneytransferapp.Backend.entities.Transaction;
import internationalmoneytransferapp.Backend.services.TransactionService;

@RestController
@RequestMapping(path = "/api/transactions")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@GetMapping
	public Iterable<Transaction> getTransactions() {
		return transactionService.getAllTransactions();
	}
}
