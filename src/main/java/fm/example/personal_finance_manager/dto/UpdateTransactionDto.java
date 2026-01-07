package fm.example.personal_finance_manager.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateTransactionDto {
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
    private String category;
    private String description;
}

