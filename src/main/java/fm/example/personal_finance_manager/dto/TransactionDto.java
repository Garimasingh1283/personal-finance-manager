package fm.example.personal_finance_manager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionDto {

    @DecimalMin(value = "0.01", inclusive = true)
    private double amount;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String category; // name

    private String description;
}
