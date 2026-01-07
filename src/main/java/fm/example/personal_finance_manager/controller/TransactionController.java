package fm.example.personal_finance_manager.controller;

import fm.example.personal_finance_manager.dto.TransactionDto;
import fm.example.personal_finance_manager.dto.TransactionResponseDto;
import fm.example.personal_finance_manager.dto.UpdateTransactionDto;
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
    public TransactionResponseDto create(@Valid @RequestBody TransactionDto dto) {
        var t = transactionService.create(dto, userService.getCurrentUser());

        return new TransactionResponseDto(
                t.getId(),
                t.getAmount(),
                t.getDate(),
                t.getCategory().getName(),
                t.getCategory().getType(),   // STRING, not enum
                t.getDescription()
        );
    }

    @GetMapping
    public ResponseEntity<Map<String, List<TransactionResponseDto>>> getAll(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long categoryId
    ) {
        var transactions = transactionService.getAll(
                userService.getCurrentUser(),
                startDate,
                endDate,
                categoryId
        );

        return ResponseEntity.ok(Map.of("transactions", transactions));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionDto dto
    ) {
        return ResponseEntity.ok(
                transactionService.update(id, dto, userService.getCurrentUser())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        transactionService.delete(id, userService.getCurrentUser());
        return ResponseEntity.ok(Map.of("message", "Transaction deleted successfully"));
    }
}
