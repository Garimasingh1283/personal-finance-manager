package fm.example.personal_finance_manager.controller;

import fm.example.personal_finance_manager.dto.ReportDto;
import fm.example.personal_finance_manager.service.ReportService;
import fm.example.personal_finance_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<ReportDto> monthly(@PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(reportService.getMonthly(userService.getCurrentUser(), year, month));
    }

    // Add yearly similar
}