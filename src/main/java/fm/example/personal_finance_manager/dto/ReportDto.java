package fm.example.personal_finance_manager.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ReportDto {
    private int month;
    private int year;
    private Map<String, Double> totalIncome;
    private Map<String, Double> totalExpenses;
    private double netSavings;
}

// Similar for YearlyReportDto