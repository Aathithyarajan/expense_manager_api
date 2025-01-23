package in.aathi.expensetrackerapi.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.aathi.expensetrackerapi.entity.ErrorObject;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFoundException ex, WebRequest request){
		
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorObject.setMesssage(ex.getMessage());
		errorObject.setTimeStamp(new Date());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);	
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorObject> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request){
		
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
		errorObject.setMesssage(ex.getMessage());
		errorObject.setTimeStamp(new Date());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> handleGeneralException(Exception ex, WebRequest request){
		
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorObject.setMesssage(ex.getMessage());
		errorObject.setTimeStamp(new Date());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status code", HttpStatus.BAD_REQUEST.value());
		
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("message", errors);
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ItemAlreadyExistsException.class)
	public ResponseEntity<ErrorObject> handleItemExistException(Exception ex, WebRequest request){
		
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.CONFLICT.value());
		errorObject.setMesssage(ex.getMessage());
		errorObject.setTimeStamp(new Date());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.CONFLICT);
	}
	
}
