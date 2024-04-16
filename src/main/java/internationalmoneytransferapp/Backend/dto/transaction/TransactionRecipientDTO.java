package internationalmoneytransferapp.Backend.dto.transaction;

import lombok.Data;

@Data
public class TransactionRecipientDTO {

    private Integer saved_beneficiary_id = null;
    private String name;
}
