package job_tracker.data;

import job_tracker.models.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository
{
    @Transactional
    public AppUser findByUsername(String username);

    @Transactional
    public AppUser add(AppUser appUser);

    @Transactional
    public void update(AppUser appUser);
}
