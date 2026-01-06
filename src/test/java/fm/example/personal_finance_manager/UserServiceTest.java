package fm.example.personal_finance_manager;

import fm.example.personal_finance_manager.service.UserService;
import fm.example.personal_finance_manager.dto.UserRegistrationDto;
import fm.example.personal_finance_manager.entity.User;
import fm.example.personal_finance_manager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_success() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUsername("test@example.com");
        dto.setPassword("pass");
        dto.setFullName("Test");
        dto.setPhoneNumber("123");

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        assertNotNull(userService.register(dto));
    }
}
