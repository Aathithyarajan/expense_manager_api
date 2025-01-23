package in.aathi.expensetrackerapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import in.aathi.expensetrackerapi.dto.CategoryDto;
import in.aathi.expensetrackerapi.dto.UserDto;
import in.aathi.expensetrackerapi.entity.Categoryentity;
import in.aathi.expensetrackerapi.entity.User;
import in.aathi.expensetrackerapi.exceptions.ItemAlreadyExistsException;
import in.aathi.expensetrackerapi.exceptions.ResourceNotFoundException;
import in.aathi.expensetrackerapi.mappers.CategoryMapper;
import in.aathi.expensetrackerapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final UserService userService;
	private final CategoryMapper categoryMapper;
	
	@Override
	public List<CategoryDto> getAllCategories() {
		List<Categoryentity> list = categoryRepository.findByUserId(userService.getLoggedInUser().getId());
		
		return list.stream().map(categoryEntity -> categoryMapper.mapToCategoryDTO(categoryEntity)).collect(Collectors.toList());
	}


	private UserDto mapToUserDTO(User user) {
		return UserDto.builder()
				.email(user.getEmail())
				.name(user.getName())
				.build();
	}

	@Override
	public CategoryDto saveCategory(CategoryDto categoryDTO) {
		boolean isCategoryPresent = categoryRepository.existsByNameAndUserId(categoryDTO.getName(), userService.getLoggedInUser().getId());
		if(isCategoryPresent) {
			throw new ItemAlreadyExistsException("Category is already present for the name "+categoryDTO.getName());
		}
		Categoryentity newCategory =  categoryMapper.mapToCategoryEntity(categoryDTO);
		newCategory.setCategoryId(UUID.randomUUID().toString());
		newCategory.setUser(userService.getLoggedInUser());		newCategory = categoryRepository.save(newCategory);
		return categoryMapper.mapToCategoryDTO(newCategory);
	}


	@Override
	public void deleteCategory(String categoryId) {
		Optional<Categoryentity> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), categoryId);
		if(!optionalCategory.isPresent()) {
			throw new ResourceNotFoundException("Category not found for the id " + categoryId);
		}
		categoryRepository.delete(optionalCategory.get());
	}
	

}
