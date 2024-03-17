package internationalmoneytransferapp.Backend;

import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import internationalmoneytransferapp.Backend.controllers.TransactionController;
import internationalmoneytransferapp.Backend.entities.Transaction;
import internationalmoneytransferapp.Backend.services.TransactionService;

@WebMvcTest
public class TransactionControllerTest {

	@Mock
	private TransactionService transactionService;
	
	@InjectMocks
	private TransactionController transactionController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setRecipient("John Doe");
        transaction.setAmount(100.0);
        transaction.setStatus("Completed");
        transaction.setDate(new Date());

        Mockito.when(transactionService.getAllTransactions()).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipient").value("John Doe"));
    }
}
