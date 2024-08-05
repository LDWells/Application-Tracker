package job_tracker.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import job_tracker.data.CompanyRepository;
import job_tracker.data.mappers.CompanyMapper;
import job_tracker.models.Company;

import java.util.List;

@Repository
public class JdbcCompanyRepository implements CompanyRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCompanyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Company findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Company WHERE id = ?", new CompanyMapper(), id);
    }

    @Override
    public List<Company> findAll() {
        return jdbcTemplate.query("SELECT * FROM Company", new CompanyMapper());
    }

    @Override
    public Company add(Company company) {
        jdbcTemplate.update("INSERT INTO Company (name, address) VALUES (?, ?)",
                company.getName(), company.getAddress());
        return company;
    }

    @Override
    public boolean update(Company company) {
        return jdbcTemplate.update("UPDATE Company SET name = ?, address = ? WHERE id = ?",
                company.getName(), company.getAddress(), company.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM Company WHERE id = ?", id) > 0;
    }
}
