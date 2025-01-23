package in.aathi.expensetrackerapi.controller;


import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import in.aathi.expensetrackerapi.dto.ExpenseDTO;
import in.aathi.expensetrackerapi.io.ExpenseRequest;
import in.aathi.expensetrackerapi.io.ExpenseResponse;
import in.aathi.expensetrackerapi.mappers.ExpenseMapper;
import in.aathi.expensetrackerapi.service.ExpenseService;
import jakarta.validation.Valid;


@RestController 
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ExpenseMapper expenseMapper;
	
	@GetMapping("/expenses")
	public List<ExpenseResponse> getAllExpenses(Pageable page) {
		List<ExpenseDTO> listOfExpenses = expenseService.getAllExpenses(page);
		return listOfExpenses.stream().map(expenseDTO -> expenseMapper.mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());
	}
	
	@GetMapping("/expenses/{expenseId}")
	public ExpenseResponse getExpenseById(@PathVariable String expenseId) {
		ExpenseDTO expenseDTO =  expenseService.getExpenseById(expenseId);
		return expenseMapper.mapToExpenseResponse(expenseDTO);
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void deleteExpenseById(@RequestParam String expenseId) {
		expenseService.deleteExpenseById(expenseId);
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expenses")
	public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
		ExpenseDTO expenseDTO = expenseMapper.mapTOExpenseDTO(expenseRequest);
		expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
		return expenseMapper.mapToExpenseResponse(expenseDTO);
	}


	@PutMapping("/expenses/{expenseId}")
	public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest expenseRequest, @PathVariable String expenseId) {
		ExpenseDTO updatedExpense =  expenseMapper.mapTOExpenseDTO(expenseRequest);
		updatedExpense = expenseService.updateExpenseDetails(expenseId, updatedExpense);
		return expenseMapper.mapToExpenseResponse(updatedExpense);
	}

	@GetMapping("/expenses/category")
	public List<ExpenseResponse> getExpenseByCategory(@RequestParam String category, Pageable page){
		List<ExpenseDTO> list = expenseService.readByCategory(category, page);
		return list.stream().map(expenseDTO -> expenseMapper.mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());
	}
	
	@GetMapping("/expenses/name")
	public List<ExpenseResponse> getExpenseByName(@RequestParam String keyword, Pageable page){
		List<ExpenseDTO> list = expenseService.readByName(keyword, page);
		return list.stream().map(expenseDTO -> expenseMapper.mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());
	}
	
	@GetMapping("/expenses/date")
	public List<ExpenseResponse> getExpenseByDate(
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate,
			Pageable page){
		List<ExpenseDTO> list = expenseService.readByDate(startDate, endDate, page);
		return list.stream().map(expenseDTO -> expenseMapper.mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());
	}
	
}
