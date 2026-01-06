package fm.example.personal_finance_manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
    @NotBlank
    private String name;
    @NotBlank
    private String type; // INCOME/EXPENSE
}