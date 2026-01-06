package fm.example.personal_finance_manager.service;

import fm.example.personal_finance_manager.dto.CategoryDto;
import fm.example.personal_finance_manager.entity.Category;
import fm.example.personal_finance_manager.entity.User;
import fm.example.personal_finance_manager.exception.ConflictException;
import fm.example.personal_finance_manager.exception.ForbiddenException;
import fm.example.personal_finance_manager.exception.ResourceNotFoundException;
import fm.example.personal_finance_manager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(User user) {
        return categoryRepository.findByUserOrUserIsNull(user);
    }

    public Category createCustom(CategoryDto dto, User user) {
        if (categoryRepository.findByNameAndUser(dto.getName(), user).isPresent()) {
            throw new ConflictException("Category name exists");
        }
        Category category = new Category();
        category.setName(dto.getName());
        category.setType(dto.getType());
        category.setCustom(true);
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public void deleteCustom(String name, User user) {
        Category category = categoryRepository.findByNameAndUser(name, user)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if (!category.isCustom()) {
            throw new ForbiddenException("Cannot delete default category");
        }
        // Check if used in transactions (add query if needed)
        categoryRepository.delete(category);
    }

    // Init defaults on startup (see Step 12)
}