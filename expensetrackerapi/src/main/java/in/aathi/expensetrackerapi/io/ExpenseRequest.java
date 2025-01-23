package in.aathi.expensetrackerapi.io;

import java.math.BigDecimal;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ExpenseRequest {

	@NotBlank(message = "Expense name must not be null ")
	@Size(min = 3, message = "Expense name must be atleast should be 3 Characters")
	private String name;
	
	@NotNull(message = "Description should not be null")
	private String description;
	
	@Column(name = "expense_amount")
	@NotNull(message = "Expense amount should not be null")
	private BigDecimal amount;
	
	@NotBlank(message = "Category should not be null")
	private String categoryId;
	
	@NotNull(message = "Date should not be null")
	private Date date;
}
