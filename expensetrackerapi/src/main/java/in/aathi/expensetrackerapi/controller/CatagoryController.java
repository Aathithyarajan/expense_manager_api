package in.aathi.expensetrackerapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.aathi.expensetrackerapi.dto.CategoryDto;
import in.aathi.expensetrackerapi.io.CategoryRequest;
import in.aathi.expensetrackerapi.io.CategoryResponse;
import in.aathi.expensetrackerapi.mappers.CategoryMapper;
import in.aathi.expensetrackerapi.service.CategoryService;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CatagoryController {
	
	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;
	
	@PostMapping
	public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
		CategoryDto categoryDto = categoryMapper.mapToCategoryDTO(categoryRequest);
		categoryDto =categoryService.saveCategory(categoryDto);
		return categoryMapper.mapToCategoryResponse(categoryDto);
	}
	
	@GetMapping
	public List<CategoryResponse> readCategories(){
		List<CategoryDto> listOfCategories = categoryService.getAllCategories();
		return listOfCategories.stream().map(CategoryDto -> categoryMapper.mapToCategoryResponse(CategoryDto)).collect(Collectors.toList());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable String categoryId) {
		categoryService.deleteCategory(categoryId);
	}


}
