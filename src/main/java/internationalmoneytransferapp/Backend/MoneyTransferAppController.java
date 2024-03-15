package internationalmoneytransferapp.Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internationalmoneytransferapp.Backend.entities.Transaction;
import internationalmoneytransferapp.Backend.services.TransactionService;

@RestController
@RequestMapping(path = "/transactions")
public class MoneyTransferAppController {

	@Autowired
	TransactionService transactionService;

	@GetMapping("/all")
	public Iterable<Transaction> getTransactions() {
		return transactionService.getAllTransactions();
	}
}
