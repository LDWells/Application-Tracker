package job_tracker.domain;

import job_tracker.data.AppUserRepository;
import job_tracker.models.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class UserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public AppUser findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public AppUser create(AppUser user) {
        validateUser(user);
        validatePassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.create(user);
    }

    public void update(AppUser user) {
        validateUser(user);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            validatePassword(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.update(user);
    }

    public boolean deleteById(AppUser user) {
        return userRepository.deleteById(user);
    }

    private void validateUser(AppUser user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new ValidationException("Username is required");
        }
        if (user.getUsername().length() > 50) {
            throw new ValidationException("Username must be less than 50 characters");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("Email is required");
        }
        if (user.getEmail().length() > 100) {
            throw new ValidationException("Email must be less than 100 characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters");
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        if (digits == 0 || letters == 0 || others == 0) {
            throw new ValidationException("Password must contain a digit, a letter, and a non-digit/non-letter");
        }
    }
}
