package fm.example.personal_finance_manager.service;

import fm.example.personal_finance_manager.dto.GoalDto;
import fm.example.personal_finance_manager.dto.GoalResponseDto;
import fm.example.personal_finance_manager.entity.Goal;
import fm.example.personal_finance_manager.entity.User;
import fm.example.personal_finance_manager.exception.ForbiddenException;
import fm.example.personal_finance_manager.exception.ResourceNotFoundException;
import fm.example.personal_finance_manager.exception.ValidationException;
import fm.example.personal_finance_manager.repository.GoalRepository;
import fm.example.personal_finance_manager.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final TransactionRepository transactionRepository;

    public GoalResponseDto create(GoalDto dto, User user) {

        if (dto.getTargetDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Target date must be in the future");
        }

        if (dto.getStartDate() != null &&
                dto.getStartDate().isAfter(dto.getTargetDate())) {
            throw new ValidationException("Start date cannot be after target date");
        }

        Goal goal = new Goal();
        goal.setGoalName(dto.getGoalName());
        goal.setTargetAmount(dto.getTargetAmount());
        goal.setTargetDate(dto.getTargetDate());
        goal.setStartDate(
                dto.getStartDate() != null ? dto.getStartDate() : LocalDate.now()
        );
        goal.setUser(user);

        return mapToResponse(goalRepository.save(goal));
    }

    public List<GoalResponseDto> getAll(User user) {
        return goalRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public GoalResponseDto get(Long id, User user) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        if (!goal.getUser().equals(user)) {
            throw new ForbiddenException("Not your goal");
        }

        return mapToResponse(goal);
    }

    public GoalResponseDto update(Long id, GoalDto dto, User user) {

        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        if (!goal.getUser().equals(user)) {
            throw new ForbiddenException("Not your goal");
        }

        if (dto.getTargetAmount() > 0) {
            goal.setTargetAmount(dto.getTargetAmount());
        }

        if (dto.getTargetDate() != null) {
            if (dto.getTargetDate().isBefore(goal.getStartDate())) {
                throw new ValidationException("Target date cannot be before start date");
            }
            goal.setTargetDate(dto.getTargetDate());
        }

        return mapToResponse(goalRepository.save(goal));
    }

    public void delete(Long id, User user) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        if (!goal.getUser().equals(user)) {
            throw new ForbiddenException("Not your goal");
        }

        goalRepository.delete(goal);
    }

    private GoalResponseDto mapToResponse(Goal goal) {

        BigDecimal income = transactionRepository
                .sumIncomeSince(goal.getUser(), goal.getStartDate());

        BigDecimal expense = transactionRepository
                .sumExpenseSince(goal.getUser(), goal.getStartDate());

        BigDecimal progress = income.subtract(expense);

        if (progress.compareTo(BigDecimal.ZERO) < 0) {
            progress = BigDecimal.ZERO;
        }

        BigDecimal targetAmount = BigDecimal.valueOf(goal.getTargetAmount());

        BigDecimal percentage = BigDecimal.ZERO;
        if (targetAmount.compareTo(BigDecimal.ZERO) > 0) {
            percentage = progress
                    .divide(targetAmount, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        BigDecimal remaining = targetAmount.subtract(progress);

        GoalResponseDto dto = new GoalResponseDto();
        dto.setId(goal.getId());
        dto.setGoalName(goal.getGoalName());
        dto.setTargetAmount(goal.getTargetAmount());
        dto.setTargetDate(goal.getTargetDate());
        dto.setStartDate(goal.getStartDate());
        dto.setCurrentProgress(progress.doubleValue());
        dto.setProgressPercentage(percentage.doubleValue());
        dto.setRemainingAmount(remaining.doubleValue());

        return dto;
    }

}
