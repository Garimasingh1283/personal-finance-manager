package fm.example.personal_finance_manager.service;
import fm.example.personal_finance_manager.exception.ValidationException;
import fm.example.personal_finance_manager.exception.ForbiddenException;
import fm.example.personal_finance_manager.dto.TransactionDto;
import fm.example.personal_finance_manager.entity.Category;
import fm.example.personal_finance_manager.entity.Transaction;
import fm.example.personal_finance_manager.entity.User;
import fm.example.personal_finance_manager.exception.ResourceNotFoundException;
import fm.example.personal_finance_manager.repository.CategoryRepository;
import fm.example.personal_finance_manager.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public Transaction create(TransactionDto dto, User user) {
        if (dto.getDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Date cannot be future");
        }
        Category category = categoryRepository.findByName(dto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        // Check if category accessible to user (default or own custom)
        if (category.getUser() != null && !category.getUser().equals(user)) {
            throw new ForbiddenException("Category not accessible");
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setDate(dto.getDate());
        transaction.setCategory(category);
        transaction.setDescription(dto.getDescription());
        transaction.setUser(user);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAll(User user, LocalDate startDate, LocalDate endDate, Long categoryId) {
        // Implement filters
        if (startDate != null && endDate != null) {
            return transactionRepository.findByUserAndDateBetween(user, startDate, endDate);
        }
        return transactionRepository.findByUserOrderByDateDesc(user);
    }

    public Transaction update(Long id, TransactionDto dto, User user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (!transaction.getUser().equals(user)) {
            throw new ForbiddenException("Not your transaction");
        }
        // Date not updatable
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        // Category update similar to create
        if (dto.getCategory() != null) {
            Category category = categoryRepository.findByName(dto.getCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            transaction.setCategory(category);
        }
        return transactionRepository.save(transaction);
    }

    public void delete(Long id, User user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (!transaction.getUser().equals(user)) {
            throw new ForbiddenException("Not your transaction");
        }
        transactionRepository.delete(transaction);
    }
}