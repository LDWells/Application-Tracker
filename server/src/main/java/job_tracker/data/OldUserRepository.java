package job_tracker.data;

import job_tracker.models.OldUser;

import java.util.List;

public interface OldUserRepository
{
    OldUser findById(int id);
    OldUser findByEmail(String email);
    List<OldUser> findAll();
    OldUser add(OldUser oldUser);
    OldUser save(OldUser oldUser);
    boolean update(OldUser oldUser);
    boolean deleteById(int id);
}