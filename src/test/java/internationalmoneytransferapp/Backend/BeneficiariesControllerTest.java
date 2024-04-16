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

import internationalmoneytransferapp.Backend.dto.SavedBeneficiaryDTO;
import internationalmoneytransferapp.Backend.entities.SavedBeneficiary;
import internationalmoneytransferapp.Backend.entities.UserEntity;
import internationalmoneytransferapp.Backend.repositories.BeneficiaryRepository;

@SpringBootTest
public class BeneficiariesControllerTest {

	@MockBean
	private BeneficiaryRepository beneficiaryRepository;

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
    public void testGetAllbeneficiariesIsUnAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/beneficiaries"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "lamin", roles = "")
    public void testGetAllbeneficiariesIsAuthenticatedButForbddenDueToLackOfRequiredRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/beneficiaries"))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "lamin", roles = "USER")
    public void testGetAllbeneficiaries() throws Exception {

        UserEntity savedBy = new UserEntity();
        savedBy.setId(2);
        savedBy.setName("Lisa Green");
        savedBy.setAccountNumber("121212");

        SavedBeneficiary savedBeneficiary = new SavedBeneficiary();
        savedBeneficiary.setId(1);
        savedBeneficiary.setName("John keller");
        savedBeneficiary.setSavedBy(savedBy);

        Mockito.when(beneficiaryRepository.findAllBySavedById(Mockito.anyInt())).thenReturn(Collections.singletonList(savedBeneficiary));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/beneficiaries"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John keller"));
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].savedBy.id").value(2))
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].savedBy.name").value("Lisa Green"))
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].savedBy.accountNumber").value(121212));
    }

    @Test
    public void testCreateBeneficiaryIsUnauthorized() throws Exception {

        SavedBeneficiaryDTO requestObject = getCreateBeneficiaryRequestObjectDTO();
        ObjectMapper obj = new ObjectMapper();
        String body = obj.writeValueAsString(requestObject);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/beneficiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            // .characterEncoding("utf-8")
            .content(body))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "lamin", roles = "")
    public void testCreateBeneficiaryIsAutheticatedBuForbiddenDueToMissingRole() throws Exception {

        SavedBeneficiaryDTO requestObject = getCreateBeneficiaryRequestObjectDTO();
        ObjectMapper obj = new ObjectMapper();
        String body = obj.writeValueAsString(requestObject);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/beneficiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            // .characterEncoding("utf-8")
            .content(body))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "lamin", roles = "USER")
    public void testCreateBeneficiary() throws Exception {

        SavedBeneficiaryDTO requestObject = getCreateBeneficiaryRequestObjectDTO();
        ObjectMapper obj = new ObjectMapper();
        String body = obj.writeValueAsString(requestObject);

        SavedBeneficiary beneficiary = new SavedBeneficiary();
        beneficiary.setId(1);
        beneficiary.setName(requestObject.getName());

        Mockito.when(beneficiaryRepository.save(Mockito.any(SavedBeneficiary.class))).thenReturn(beneficiary);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/beneficiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            // .characterEncoding("utf-8")
            .content(body))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("name").value("Bill Rice"));
            // .andExpect(MockMvcResultMatchers.jsonPath("sender.id").value(2))
            // .andExpect(MockMvcResultMatchers.jsonPath("sender.name").value("Sender 1"))
            // .andExpect(MockMvcResultMatchers.jsonPath("recipient.id").value(3))
            // .andExpect(MockMvcResultMatchers.jsonPath("recipient.name").value("Recepient 1"));
    }

    private SavedBeneficiaryDTO getCreateBeneficiaryRequestObjectDTO() {


        // Post Request DTO Creator ===================
        SavedBeneficiaryDTO requestObject = new SavedBeneficiaryDTO();
        requestObject.setName("Bill Rice");
        requestObject.setSaverId(1);

        // Mockito.when(transactionService.createBeneficiary(Mockito.any())).thenReturn(transaction);

        return requestObject;
    }
}
