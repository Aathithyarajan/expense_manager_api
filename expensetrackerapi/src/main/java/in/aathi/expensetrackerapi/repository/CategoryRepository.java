package in.aathi.expensetrackerapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import in.aathi.expensetrackerapi.entity.Categoryentity;

public interface CategoryRepository extends JpaRepository<Categoryentity, Long> {
	
	List<Categoryentity> findByUserId(Long userId);
	
	Optional<Categoryentity> findByUserIdAndCategoryId(Long id, String categoryId);
	
	boolean existsByNameAndUserId(String name, Long userId);
	
	Optional<Categoryentity> findByNameAndUserId(String name, Long userId);

}
