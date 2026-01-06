package fm.example.personal_finance_manager.controller;

import fm.example.personal_finance_manager.dto.TransactionDto;
import fm.example.personal_finance_manager.entity.Transaction;
import fm.example.personal_finance_manager.service.TransactionService;
import fm.example.personal_finance_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction create(@Valid @RequestBody TransactionDto dto) {
        return transactionService.create(dto, userService.getCurrentUser());
    }


    @GetMapping
    public ResponseEntity<Map<String, List<Transaction>>> getAll(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long categoryId) {
        var transactions = transactionService.getAll(userService.getCurrentUser(), startDate, endDate, categoryId);
        return ResponseEntity.ok(Map.of("transactions", transactions));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id, @Valid @RequestBody TransactionDto dto) {
        return ResponseEntity.ok(transactionService.update(id, dto, userService.getCurrentUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        transactionService.delete(id, userService.getCurrentUser());
        return ResponseEntity.ok(Map.of("message", "Transaction deleted successfully"));
    }
}