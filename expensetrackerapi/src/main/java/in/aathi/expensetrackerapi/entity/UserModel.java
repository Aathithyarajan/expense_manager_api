package in.aathi.expensetrackerapi.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {

	@NotBlank(message = "Name should not be empty")
	private String name;
	
	@NotNull(message = "Email should not be null")
	@Email(message = "Enter a Valid Email")
	private String email;
	
	@NotNull(message = "Password should not be empty")
	@Size(min = 5, message = "Password should atleast 5 characters")
	private String password;
	
	private Long age = 0L;
	
}
