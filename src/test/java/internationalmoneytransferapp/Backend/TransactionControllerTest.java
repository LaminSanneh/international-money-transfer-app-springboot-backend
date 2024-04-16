package internationalmoneytransferapp.Backend;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import internationalmoneytransferapp.Backend.dto.TransactionDTO;
import internationalmoneytransferapp.Backend.dto.transaction.TransactionRecipientDTO;
import internationalmoneytransferapp.Backend.entities.Recipient;
import internationalmoneytransferapp.Backend.entities.Transaction;
import internationalmoneytransferapp.Backend.entities.UserEntity;
import internationalmoneytransferapp.Backend.services.TransactionService;

@SpringBootTest
public class TransactionControllerLevel1TestWebMvc2Test {

	@MockBean
	private TransactionService transactionService;

    protected MockMvc mockMvc;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void applySecurity() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
            .build();
    }

    @Test
    public void testGetAllTransactionsIsUnAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "lamin", roles = "")
    public void testGetAllTransactionsIsAuthenticatedButForbddenDueToLackOfRequiredRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions"))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "lamin", roles = "USER")
    public void testGetAllTransactions() throws Exception {

        UserEntity sender = new UserEntity();
        sender.setId(2);
        sender.setName("Sender 1");
        sender.setAccountNumber("121212");

        Recipient recipient = new Recipient();
        recipient.setId(3);
        recipient.setName("Recepient 1");

        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setSender(sender);
        transaction.setCurrency("EUR");
        transaction.setRecipient(recipient);
        transaction.setAmount(15);

        Mockito.when(transactionService.getAllTransactions(Mockito.any(String.class))).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency ").value("EUR"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(15));
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].sender.id").value(2))
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].sender.name").value("Sender 1"))
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipient.id").value(3))
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipient.name").value("Recepient 1"));
    }

    @Test
    public void testCreateTransactionIsUnauthorized() throws Exception {

        TransactionDTO requestObject = getCreateTransactionRequestObjectDTO();
        ObjectMapper obj = new ObjectMapper();
        String body = obj.writeValueAsString(requestObject);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            // .characterEncoding("utf-8")
            .content(body))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "lamin", roles = "")
    public void testCreateTransactionIsAutheticatedBuForbiddenDueToMissingRole() throws Exception {

        TransactionDTO requestObject = getCreateTransactionRequestObjectDTO();
        ObjectMapper obj = new ObjectMapper();
        String body = obj.writeValueAsString(requestObject);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            // .characterEncoding("utf-8")
            .content(body))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "lamin", roles = "USER")
    public void testCreateTransaction() throws Exception {

        TransactionDTO requestObject = getCreateTransactionRequestObjectDTO();
        ObjectMapper obj = new ObjectMapper();
        String body = obj.writeValueAsString(requestObject);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            // .characterEncoding("utf-8")
            .content(body))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("amount").value(2233))
            .andExpect(MockMvcResultMatchers.jsonPath("currency").value("USD"));
            // .andExpect(MockMvcResultMatchers.jsonPath("sender.id").value(2))
            // .andExpect(MockMvcResultMatchers.jsonPath("sender.name").value("Sender 1"))
            // .andExpect(MockMvcResultMatchers.jsonPath("recipient.id").value(3))
            // .andExpect(MockMvcResultMatchers.jsonPath("recipient.name").value("Recepient 1"));
    }

    private TransactionDTO getCreateTransactionRequestObjectDTO() {
        // Service Response mock ===================
        UserEntity sender = new UserEntity();
        sender.setId(2);
        sender.setName("Sender 1");
        sender.setAccountNumber("121212");

        Recipient recipient = new Recipient();
        recipient.setId(3);
        recipient.setName("Recepient 1");

        String currency = "USD";
        Integer amount = 2233;

        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setCurrency(currency);
        transaction.setAmount(amount);
        transaction.setSender(sender);
        transaction.setRecipient(recipient);
        // Service Response mock ===================

        // Post Request DTO Creator ===================
        TransactionDTO requestObject = new TransactionDTO();
        requestObject.setAmount(amount);
        requestObject.setCurrency(currency);
        TransactionRecipientDTO recipientForDTO = new TransactionRecipientDTO();
        recipientForDTO.setName("John Kerry");
        requestObject.setRecipient(recipientForDTO);
        requestObject.setSenderId(sender.getId());
        // Post Request DTO Creator ===================

        Mockito.when(transactionService.createTransaction(Mockito.any())).thenReturn(transaction);

        return requestObject;
    }
}
