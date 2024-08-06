package job_tracker.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import job_tracker.data.CompanyRepository;
import job_tracker.data.mappers.CompanyMapper;
import job_tracker.models.Company;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
        final String sql = "INSERT INTO Company (name, address) "
                + " VALUES (?, ?);";

        //Needed to auto-generate primary id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, company.getName());
            ps.setString(2, company.getAddress());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        company.setId(keyHolder.getKey().intValue());

        return company;
    }

    @Override
    public boolean update(Company company) {
        return jdbcTemplate.update("UPDATE Company SET name = ?, address = ? WHERE id = ?",
                company.getName(), company.getAddress(), company.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        // First delete tasks related to applications of jobs of the company
        jdbcTemplate.update("DELETE FROM Task WHERE application_id IN (SELECT id FROM Application WHERE job_id IN (SELECT id FROM Job WHERE company_id = ?))", id);

        // Then delete applications related to jobs of the company
        jdbcTemplate.update("DELETE FROM Application WHERE job_id IN (SELECT id FROM Job WHERE company_id = ?)", id);

        // Then delete jobs related to the company
        jdbcTemplate.update("DELETE FROM Job WHERE company_id = ?", id);

        // Finally delete the company
        return jdbcTemplate.update("DELETE FROM Company WHERE id = ?", id) > 0;
    }

}
