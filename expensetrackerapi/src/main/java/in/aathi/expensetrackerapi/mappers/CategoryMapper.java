package in.aathi.expensetrackerapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import in.aathi.expensetrackerapi.dto.CategoryDto;
import in.aathi.expensetrackerapi.entity.Categoryentity;
import in.aathi.expensetrackerapi.io.CategoryRequest;
import in.aathi.expensetrackerapi.io.CategoryResponse;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	Categoryentity mapToCategoryEntity(CategoryDto categoryDTO);
	
	CategoryDto mapToCategoryDTO(Categoryentity entity);
	
	@Mapping(target = "categoryIcon" , source = "icon") 
	CategoryDto mapToCategoryDTO(CategoryRequest request);
	
	CategoryResponse mapToCategoryResponse(CategoryDto categoryDTO);

}
