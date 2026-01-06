package fm.example.personal_finance_manager.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class GoalDto {

    @NotBlank
    private String goalName;

    @DecimalMin(value = "0.01", inclusive = true)
    private double targetAmount;

    @NotNull
    private LocalDate targetDate;

    private LocalDate startDate; // optional, default today
}
