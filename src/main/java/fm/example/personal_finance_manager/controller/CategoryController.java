package fm.example.personal_finance_manager.controller;

import fm.example.personal_finance_manager.dto.CategoryDto;
import fm.example.personal_finance_manager.entity.Category;
import fm.example.personal_finance_manager.service.CategoryService;
import fm.example.personal_finance_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Map<String, List<Category>>> getAll() {
        return ResponseEntity.ok(Map.of("categories", categoryService.getAllCategories(userService.getCurrentUser())));
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCustom(dto, userService.getCurrentUser()));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String name) {
        categoryService.deleteCustom(name, userService.getCurrentUser());
        return ResponseEntity.ok(Map.of("message", "Category deleted successfully"));
    }
}