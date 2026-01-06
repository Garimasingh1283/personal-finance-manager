package fm.example.personal_finance_manager.repository;

import fm.example.personal_finance_manager.entity.Transaction;
import fm.example.personal_finance_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByDateDesc(User user);
    List<Transaction> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
    // Add more filters as needed for category, type

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.category.type = 'INCOME' AND t.date >= :startDate")
    Double sumIncomeSince(User user, LocalDate startDate);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.category.type = 'EXPENSE' AND t.date >= :startDate")
    Double sumExpenseSince(User user, LocalDate startDate);

    // For reports: sum by category, month, year, etc.
    @Query("SELECT c.name, SUM(t.amount) FROM Transaction t JOIN t.category c WHERE t.user = :user AND t.category.type = :type AND YEAR(t.date) = :year AND MONTH(t.date) = :month GROUP BY c.name")
    List<Object[]> sumByCategoryForMonth(User user, String type, int year, int month);
    // Similar for yearly
}