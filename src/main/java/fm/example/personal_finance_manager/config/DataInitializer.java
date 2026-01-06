package fm.example.personal_finance_manager.config;

import fm.example.personal_finance_manager.entity.Category;
import fm.example.personal_finance_manager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        createDefault("Salary", "INCOME");
        createDefault("Food", "EXPENSE");
        createDefault("Rent", "EXPENSE");
        createDefault("Transportation", "EXPENSE");
        createDefault("Entertainment", "EXPENSE");
        createDefault("Healthcare", "EXPENSE");
        createDefault("Utilities", "EXPENSE");
    }

    private void createDefault(String name, String type) {
        if (categoryRepository.findByName(name).isEmpty()) {
            Category category = new Category();
            category.setName(name);
            category.setType(type);
            category.setCustom(false);
            categoryRepository.save(category);
        }
    }
}