package job_tracker.data;

import job_tracker.models.AppUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppUserRepository {
    @Transactional
    AppUser findByUsername(String username);

    @Transactional
    List<AppUser> findAll();

    @Transactional
    AppUser create(AppUser user);

    @Transactional
    void update(AppUser user);

    @Transactional
    boolean deleteById(AppUser user);
}
