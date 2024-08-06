package job_tracker.domain;

import job_tracker.data.AppUserRepository;
import job_tracker.models.AppUser;
import job_tracker.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ValidationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<AppUser> appUserCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createValidUserReturnsCreatedUser() {
        AppUser user = new AppUser(0, "testuser", "test@example.com", "password123*", false, List.of(Role.USER.getName()));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userRepository.create(any(AppUser.class))).thenReturn(user);

        AppUser createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
        verify(userRepository).create(appUserCaptor.capture());
        assertEquals("encodedPassword", appUserCaptor.getValue().getPassword());
    }

    @Test
    void createUserWithShortPasswordThrowsValidationException() {
        AppUser user = new AppUser(0, "testuser", "test@example.com", "short", false, List.of(Role.USER.getName()));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Password must be at least 8 characters", exception.getMessage());
    }

    @Test
    void createUserWithNoDigitInPasswordThrowsValidationException() {
        AppUser user = new AppUser(0, "testuser", "test@example.com", "Password!", false, List.of(Role.USER.getName()));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Password must contain a digit, a letter, and a non-digit/non-letter", exception.getMessage());
    }

    @Test
    void createUserWithNoLetterInPasswordThrowsValidationException() {
        AppUser user = new AppUser(0, "testuser", "test@example.com", "12345678!", false, List.of(Role.USER.getName()));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Password must contain a digit, a letter, and a non-digit/non-letter", exception.getMessage());
    }

    @Test
    void createUserWithNoSpecialCharInPasswordThrowsValidationException() {
        AppUser user = new AppUser(0, "testuser", "test@example.com", "Password1", false, List.of(Role.USER.getName()));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Password must contain a digit, a letter, and a non-digit/non-letter", exception.getMessage());
    }

    @Test
    void updateUserWithNewPasswordEncodesPassword() {
        AppUser user = new AppUser(0, "testuser", "test@example.com", "newPassword123!", false, List.of(Role.USER.getName()));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedNewPassword");

        userService.update(user);

        verify(userRepository).update(appUserCaptor.capture());
        assertEquals("encodedNewPassword", appUserCaptor.getValue().getPassword());
    }

    @Test
    void findByUsernameUserExistsReturnsUser() {
        AppUser user = new AppUser(0, "testuser", "test@example.com", "password123", false, List.of(Role.USER.getName()));
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        AppUser foundUser = userService.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void findByUsernameUserDoesNotExistReturnsNull() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        AppUser foundUser = userService.findByUsername("nonexistent");

        assertNull(foundUser);
    }

    @Test
    void deleteByIdUserExistsDeletesUser() {
        AppUser user = new AppUser(0, "testuser", "test@example.com", "password123", false, List.of(Role.USER.getName()));
        when(userRepository.deleteById(user)).thenReturn(true);

        boolean result = userService.deleteById(user);

        assertTrue(result);
        verify(userRepository).deleteById(user);
    }

    @Test
    void deleteByIdUserDoesNotExistReturnsFalse() {
        AppUser user = new AppUser(0, "nonexistent", "test@example.com", "password123", false, List.of(Role.USER.getName()));
        when(userRepository.deleteById(user)).thenReturn(false);

        boolean result = userService.deleteById(user);

        assertFalse(result);
        verify(userRepository).deleteById(user);
    }

    @Test
    void createUserWithLongUsernameThrowsValidationException() {
        AppUser user = new AppUser(0, "a".repeat(51), "test@example.com", "password123!", false, List.of(Role.USER.getName()));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Username must be less than 50 characters", exception.getMessage());
    }

    @Test
    void createUserWithLongEmailThrowsValidationException() {
        AppUser user = new AppUser(0, "testuser", "a".repeat(101), "password123!", false, List.of(Role.USER.getName()));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Email must be less than 100 characters", exception.getMessage());
    }
}
