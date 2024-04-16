package internationalmoneytransferapp.Backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user_saved_beneficiaries")
public class SavedBeneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private UserEntity savedBy;

    // private String accountNumber;

	// public Integer getId() {
	// 	return id;
	// }

	// public String getAccountNumber() {
	// 	return accountNumber;
	// }
	// public void setAccountNumber(String accountNumber) {
	// 	this.accountNumber = accountNumber;
	// }
}
