package fm.example.personal_finance_manager.controller;

import fm.example.personal_finance_manager.dto.GoalDto;
import fm.example.personal_finance_manager.dto.GoalResponseDto;
import fm.example.personal_finance_manager.service.GoalService;
import fm.example.personal_finance_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<GoalResponseDto> create(@Valid @RequestBody GoalDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(goalService.create(dto, userService.getCurrentUser()));
    }

    @GetMapping
    public ResponseEntity<Map<String, List<GoalResponseDto>>> getAll() {
        return ResponseEntity.ok(
                Map.of("goals", goalService.getAll(userService.getCurrentUser()))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                goalService.get(id, userService.getCurrentUser())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody GoalDto dto
    ) {
        return ResponseEntity.ok(
                goalService.update(id, dto, userService.getCurrentUser())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        goalService.delete(id, userService.getCurrentUser());
        return ResponseEntity.ok(Map.of("message", "Goal deleted successfully"));
    }
}
