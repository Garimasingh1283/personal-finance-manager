package fm.example.personal_finance_manager.service;

import fm.example.personal_finance_manager.dto.ReportDto;
import fm.example.personal_finance_manager.entity.User;
import fm.example.personal_finance_manager.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final TransactionRepository transactionRepository;

    public ReportDto getMonthly(User user, int year, int month) {
        ReportDto dto = new ReportDto();
        dto.setYear(year);
        dto.setMonth(month);

        Map<String, Double> income = new HashMap<>();
        List<Object[]> incomeData = transactionRepository.sumByCategoryForMonth(user, "INCOME", year, month);
        for (Object[] row : incomeData) {
            income.put((String) row[0], (Double) row[1]);
        }
        dto.setTotalIncome(income);

        Map<String, Double> expenses = new HashMap<>();
        List<Object[]> expenseData = transactionRepository.sumByCategoryForMonth(user, "EXPENSE", year, month);
        for (Object[] row : expenseData) {
            expenses.put((String) row[0], (Double) row[1]);
        }
        dto.setTotalExpenses(expenses);

        double totalIncome = income.values().stream().mapToDouble(Double::doubleValue).sum();
        double totalExpense = expenses.values().stream().mapToDouble(Double::doubleValue).sum();
        dto.setNetSavings(totalIncome - totalExpense);

        return dto;
    }

    // Similar for yearly: aggregate months or direct query
}