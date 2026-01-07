package fm.example.personal_finance_manager.service;

import fm.example.personal_finance_manager.dto.TransactionDto;
import fm.example.personal_finance_manager.dto.UpdateTransactionDto;
import fm.example.personal_finance_manager.dto.TransactionResponseDto;
import fm.example.personal_finance_manager.entity.Category;
import fm.example.personal_finance_manager.entity.Transaction;
import fm.example.personal_finance_manager.entity.User;
import fm.example.personal_finance_manager.exception.ForbiddenException;
import fm.example.personal_finance_manager.exception.ResourceNotFoundException;
import fm.example.personal_finance_manager.exception.ValidationException;
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

        if (category.getUser() != null && !category.getUser().equals(user)) {
            throw new ForbiddenException("Category not accessible");
        }

        Transaction t = new Transaction();
        t.setAmount(dto.getAmount());
        t.setDate(dto.getDate());
        t.setCategory(category);
        t.setDescription(dto.getDescription());
        t.setUser(user);

        return transactionRepository.save(t);
    }

    public List<TransactionResponseDto> getAll(
            User user,
            LocalDate startDate,
            LocalDate endDate,
            Long categoryId
    ) {

        List<Transaction> list;

        if (categoryId != null) {
            list = transactionRepository.findByUserAndCategoryId(user, categoryId);
        } else if (startDate != null && endDate != null) {
            list = transactionRepository.findByUserAndDateBetween(user, startDate, endDate);
        } else {
            list = transactionRepository.findByUserOrderByDateDesc(user);
        }

        return list.stream().map(t ->
                new TransactionResponseDto(
                        t.getId(),
                        t.getAmount(),
                        t.getDate(),
                        t.getCategory().getName(),
                        t.getCategory().getType(),
                        t.getDescription()
                )
        ).toList();

    }

    public TransactionResponseDto update(Long id, UpdateTransactionDto dto, User user) {

        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!t.getUser().equals(user)) {
            throw new ForbiddenException("Not your transaction");
        }

        // 🔥 Date MUST NOT be updated (ignore silently)

        if (dto.getAmount() != null) {
            t.setAmount(dto.getAmount());
        }

        if (dto.getDescription() != null) {
            t.setDescription(dto.getDescription());
        }

        if (dto.getCategory() != null) {
            Category category = categoryRepository.findByName(dto.getCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            t.setCategory(category);
        }

        Transaction saved = transactionRepository.save(t);

        return new TransactionResponseDto(
                saved.getId(),
                saved.getAmount(),
                saved.getDate(),
                saved.getCategory().getName(),
                saved.getCategory().getType(),
                saved.getDescription()
        );

    }

    public void delete(Long id, User user) {

        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!t.getUser().equals(user)) {
            throw new ForbiddenException("Not your transaction");
        }

        transactionRepository.delete(t);
    }
}

