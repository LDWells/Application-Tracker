package job_tracker.data;

import job_tracker.models.User;

import java.util.List;

public interface UserRepository {
    User findById(int id);
    User findByEmail(String email);
    List<User> findAll();
    User add(User user);
    User save(User user);
    boolean update(User user);
    boolean deleteById(int id);
}