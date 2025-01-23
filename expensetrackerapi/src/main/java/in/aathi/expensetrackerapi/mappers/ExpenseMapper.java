package in.aathi.expensetrackerapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import in.aathi.expensetrackerapi.dto.ExpenseDTO;
import in.aathi.expensetrackerapi.entity.Expense;
import in.aathi.expensetrackerapi.io.ExpenseRequest;
import in.aathi.expensetrackerapi.io.ExpenseResponse;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
	
	ExpenseMapper INSTANCE = Mappers.getMapper( ExpenseMapper.class );
	
	@Mapping(target = "category", source = "expenseDTO.categoryDTO")
	ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO);
	
	ExpenseDTO mapTOExpenseDTO(ExpenseRequest request);
	
	Expense mapToExpenseEntity(ExpenseDTO expenseDTO);
	
	@Mapping(target = "categoryDTO" , source = "expense.category")
	ExpenseDTO mapToExpenseDTO(Expense expense);


}
