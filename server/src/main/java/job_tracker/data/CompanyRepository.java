package job_tracker.data;

import job_tracker.models.Company;

import java.util.List;

public interface CompanyRepository {
    Company findById(int id);
    List<Company> findAll();
    Company add(Company company);
    boolean update(Company company);
    boolean deleteById(int id);
}