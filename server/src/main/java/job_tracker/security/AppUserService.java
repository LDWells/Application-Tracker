package job_tracker.security;

import job_tracker.data.AppUserRepository;
import job_tracker.models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserService implements UserDetailsService
{
    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder)
    {
        this.repository = repository;
        this.encoder = encoder;
        ensureAdmin();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        AppUser appUser = repository.findByUsername(username);
        if (appUser == null || appUser.isDisabled()) {
            throw new UsernameNotFoundException(username + " not found.");
        }
        List<GrantedAuthority> authorities = appUser.getRoles().stream()
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                .collect(Collectors.toList());
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    public AppUser loadAppUserByUsername(String username) throws UsernameNotFoundException
    {
        AppUser appUser = repository.findByUsername(username);
        if (appUser == null || appUser.isDisabled()) {
            throw new UsernameNotFoundException(username + " not found.");
        }
//        List<GrantedAuthority> authorities = appUser.getRoles().stream()
//                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
//                .collect(Collectors.toList());
        return appUser;
    }

    public AppUser add(@Valid AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.add(user);
    }

    private void ensureAdmin() {
        AppUser user = repository.findByUsername("admin");
        if (user == null) {
            String randomPassword = "top-secret-password";//UUID.randomUUID().toString();
            user = new AppUser(1, "admin", randomPassword, false, List.of("ADMIN", "USER"));
            try {
                add(user);
                System.out.printf("%n%nRandom admin password: %s%n%n", randomPassword);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
