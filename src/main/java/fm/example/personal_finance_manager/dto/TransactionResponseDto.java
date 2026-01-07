package fm.example.personal_finance_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionResponseDto {

    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private String category;
    private String type;
    private String description;
}
