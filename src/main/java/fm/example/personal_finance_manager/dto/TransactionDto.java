package fm.example.personal_finance_manager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDto {

    @NotNull(message = "date must not be null")
    private LocalDate date;

    @NotBlank(message = "category must not be blank")
    private String category;

    @NotNull(message = "amount must not be null")
    @DecimalMin(value = "0.01", message = "amount must be greater than or equal to 0.01")
    private BigDecimal amount;

    private String description;
}
