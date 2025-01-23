package in.aathi.expensetrackerapi.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import in.aathi.expensetrackerapi.dto.ExpenseDTO;

public interface ExpenseService {
	
	List<ExpenseDTO> getAllExpenses(Pageable page);
	
	ExpenseDTO getExpenseById(String expenseId);
	
	void deleteExpenseById(String expenseId);
	
	ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO);
	
	ExpenseDTO updateExpenseDetails(String expenseId, ExpenseDTO expense);
	
	List<ExpenseDTO> readByCategory(String category, Pageable Page);
	
	List<ExpenseDTO> readByName(String keyword, Pageable page);
	
	List<ExpenseDTO> readByDate(Date startDate, Date endDate, Pageable page);
}
