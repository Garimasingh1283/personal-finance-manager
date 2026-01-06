package fm.example.personal_finance_manager.repository;

import fm.example.personal_finance_manager.entity.Category;
import fm.example.personal_finance_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserOrUserIsNull(User user);
    Optional<Category> findByNameAndUser(String name, User user);
    Optional<Category> findByName(String name);
}