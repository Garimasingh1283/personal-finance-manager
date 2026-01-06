package fm.example.personal_finance_manager.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class GoalResponseDto {
    private Long id;
    private String goalName;
    private double targetAmount;
    private LocalDate targetDate;
    private LocalDate startDate;
    private double currentProgress;
    private double progressPercentage;
    private double remainingAmount;
}