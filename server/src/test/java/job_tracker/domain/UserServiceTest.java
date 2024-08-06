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
        MockitoAnnotations.initMocks(this); // Use initMocks for older versions of Mockito
    }

    @Test
    void createValidUserReturnsCreatedUser() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "password123", false, List.of(Role.USER.getName()));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userRepository.create(any(AppUser.class))).thenReturn(user);

        // Act
        AppUser createdUser = userService.create(user);

        // Assert
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userRepository).create(appUserCaptor.capture());
        assertEquals("encodedPassword", appUserCaptor.getValue().getPassword());
    }

    @Test
    void createUserWithShortPasswordThrowsValidationException() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "short", false, List.of(Role.USER.getName()));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Password must be at least 8 characters", exception.getMessage());
    }

    @Test
    void createUserWithNoDigitInPasswordThrowsValidationException() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "Password!", false, List.of(Role.USER.getName()));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Password must contain a digit, a letter, and a non-digit/non-letter", exception.getMessage());
    }

    @Test
    void createUserWithNoLetterInPasswordThrowsValidationException() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "12345678!", false, List.of(Role.USER.getName()));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Password must contain a digit, a letter, and a non-digit/non-letter", exception.getMessage());
    }

    @Test
    void createUserWithNoSpecialCharInPasswordThrowsValidationException() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "Password1", false, List.of(Role.USER.getName()));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Password must contain a digit, a letter, and a non-digit/non-letter", exception.getMessage());
    }

    @Test
    void updateUserWithNewPasswordEncodesPassword() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "newPassword123!", false, List.of(Role.USER.getName()));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedNewPassword");

        // Act
        userService.update(user);

        // Assert
        verify(userRepository).update(appUserCaptor.capture());
        assertEquals("encodedNewPassword", appUserCaptor.getValue().getPassword());
    }

    @Test
    void updateUserWithoutNewPasswordKeepsOldPassword() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "existingPassword", false, List.of(Role.USER.getName()));
        user.setPassword(null);

        // Act
        userService.update(user);

        // Assert
        verify(userRepository).update(appUserCaptor.capture());
        assertNull(appUserCaptor.getValue().getPassword());
    }

    @Test
    void findByUsernameUserExistsReturnsUser() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "password123", false, List.of(Role.USER.getName()));
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        // Act
        AppUser foundUser = userService.findByUsername("testuser");

        // Assert
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void findByUsernameUserDoesNotExistReturnsNull() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        // Act
        AppUser foundUser = userService.findByUsername("nonexistent");

        // Assert
        assertNull(foundUser);
    }

    @Test
    void deleteByIdUserExistsDeletesUser() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "password123", false, List.of(Role.USER.getName()));
        when(userRepository.deleteById(user)).thenReturn(true);

        // Act
        boolean result = userService.deleteById(user);

        // Assert
        assertTrue(result);
        verify(userRepository).deleteById(user);
    }

    @Test
    void deleteByIdUserDoesNotExistReturnsFalse() {
        // Arrange
        AppUser user = new AppUser(0, "nonexistent", "password123", false, List.of(Role.USER.getName()));
        when(userRepository.deleteById(user)).thenReturn(false);

        // Act
        boolean result = userService.deleteById(user);

        // Assert
        assertFalse(result);
        verify(userRepository).deleteById(user);
    }

    @Test
    void createUserWithBlankUsernameThrowsValidationException() {
        // Arrange
        AppUser user = new AppUser(0, "", "password123!", false, List.of(Role.USER.getName()));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    void createUserWithLongUsernameThrowsValidationException() {
        // Arrange
        AppUser user = new AppUser(0, "a".repeat(51), "password123!", false, List.of(Role.USER.getName()));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Username must be less than 50 characters", exception.getMessage());
    }

    @Test
    void createUserWithBlankEmailThrowsValidationException() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "", false, List.of(Role.USER.getName()));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    void createUserWithLongEmailThrowsValidationException() {
        // Arrange
        AppUser user = new AppUser(0, "testuser", "a".repeat(101), false, List.of(Role.USER.getName()));

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Email must be less than 100 characters", exception.getMessage());
    }
}
