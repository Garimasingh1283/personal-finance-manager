package fm.example.personal_finance_manager.repository;

import fm.example.personal_finance_manager.entity.Transaction;
import fm.example.personal_finance_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserOrderByDateDesc(User user);

    List<Transaction> findByUserAndDateBetween(
            User user,
            LocalDate start,
            LocalDate end
    );

    List<Transaction> findByUserAndCategoryId(User user, Long categoryId);

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.user = :user
          AND t.category.type = 'INCOME'
          AND t.date >= :startDate
    """)
    BigDecimal sumIncomeSince(User user, LocalDate startDate);

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.user = :user
          AND t.category.type = 'EXPENSE'
          AND t.date >= :startDate
    """)
    BigDecimal sumExpenseSince(User user, LocalDate startDate);

    // ✅ ADD THIS METHOD (THIS FIXES REPORT SERVICE ERROR)
    @Query("""
        SELECT c.name, COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        JOIN t.category c
        WHERE t.user = :user
          AND c.type = :type
          AND YEAR(t.date) = :year
          AND MONTH(t.date) = :month
        GROUP BY c.name
    """)
    List<Object[]> sumByCategoryForMonth(
            User user,
            String type,
            int year,
            int month
    );
}
