package in.aathi.expensetrackerapi.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorObject {
	
	private Integer statusCode;
	
	private String messsage;
	
	private Date timeStamp;


}
