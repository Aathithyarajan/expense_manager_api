package in.aathi.expensetrackerapi.service;

import java.util.List;

import in.aathi.expensetrackerapi.dto.CategoryDto;

public interface CategoryService {
	List<CategoryDto> getAllCategories();

	CategoryDto saveCategory(CategoryDto categoryDTO);
	
	void deleteCategory(String categoryId);
}
