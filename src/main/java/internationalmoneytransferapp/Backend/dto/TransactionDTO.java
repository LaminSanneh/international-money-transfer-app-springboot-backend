package internationalmoneytransferapp.Backend.dto;

import java.util.Date;

import org.springframework.data.annotation.ReadOnlyProperty;

import internationalmoneytransferapp.Backend.dto.transaction.TransactionRecipientDTO;
import lombok.Data;

@Data
public class TransactionDTO {

    private double amount;
	private String currency;

    private TransactionRecipientDTO recipient;

    private Integer senderId;

    @ReadOnlyProperty
    private Date date = (new Date());
}
