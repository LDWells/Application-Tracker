package job_tracker.data;

import job_tracker.data.mappers.ApplicationDtoMapper;
import job_tracker.data.mappers.ApplicationMapper;
import job_tracker.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcApplicationRepository implements ApplicationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;

    public JdbcApplicationRepository(JdbcTemplate jdbcTemplate, CompanyRepository companyRepository, JobRepository jobRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public Application findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Application WHERE id = ?", new ApplicationMapper(), id);
    }

    @Override
    public List<Application> findAll() {
        return jdbcTemplate.query("SELECT * FROM Application", new ApplicationMapper());
    }

    @Override
    public Application add(Application application) {
        final String sql ="INSERT INTO Application (user_id, job_id, application_date, applied_on, status) "
                + " VALUES (?, ?, ?, ?, ?);";

        //Needed to auto-generate primary id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, application.getUserId());
            ps.setInt(2, application.getJobId());
            ps.setDate(3, application.getApplicationDate() == null ? null : Date.valueOf(application.getApplicationDate()));
            ps.setString(4, application.getAppliedOn());
            ps.setString(5, application.getStatus().toString());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        application.setId(keyHolder.getKey().intValue());

        return application;
    }

    @Override
    public boolean update(Application application) {
        return jdbcTemplate.update("UPDATE Application SET user_id = ?, job_id = ?, application_date = ?, applied_on = ?, status = ? WHERE id = ?",
                application.getUserId(), application.getJobId(), application.getApplicationDate(), application.getAppliedOn(), application.getStatus().toString(), application.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        jdbcTemplate.update("DELETE FROM Task WHERE application_id = ? ", id); //need to first remove from referenced table
        return jdbcTemplate.update("DELETE FROM Application WHERE id = ?", id) > 0;
    }

    @Override
    public List<ApplicationDTO> findAllWithDetails() {
        String sql = "SELECT c.id AS company_id, c.name AS company_name, c.address AS company_address, " +
                "j.id AS job_id, j.title AS job_title, j.description AS job_description, " +
                "a.id AS application_id, a.user_id, a.application_date, a.applied_on, a.status " +
                "FROM Company c " +
                "LEFT JOIN Job j ON c.id = j.company_id " +
                "LEFT JOIN Application a ON j.id = a.job_id";

        return jdbcTemplate.query(sql, new ApplicationDtoMapper());
    }

    @Override
    public ApplicationDTO findByIdWithDetails(int id) {
        String sql = "SELECT c.id AS company_id, c.name AS company_name, c.address AS company_address, " +
                "j.id AS job_id, j.title AS job_title, j.description AS job_description, " +
                "a.id AS application_id, a.user_id, a.application_date, a.applied_on, a.status " +
                "FROM Company c " +
                "LEFT JOIN Job j ON c.id = j.company_id " +
                "LEFT JOIN Application a ON j.id = a.job_id " +
                "WHERE a.id = ?";

        return jdbcTemplate.queryForObject(sql, new ApplicationDtoMapper(), id);
    }

    @Override
    public ApplicationDTO addWithDetails(ApplicationDTO applicationDTO) {
        // Always add new company
        Company company = new Company(applicationDTO.getCompanyName(), applicationDTO.getCompanyAddress());
        Company storedCompany = companyRepository.add(company);
        if (storedCompany == null) {
            return null;
        }
        applicationDTO.setCompanyId(storedCompany.getId());

        // Add job
        Job job = new Job(storedCompany.getId(), applicationDTO.getJobTitle(), applicationDTO.getJobDescription());
        Job storedJob = jobRepository.add(job);
        if (storedJob == null) {
            return null;
        }
        applicationDTO.setJobId(storedJob.getId());

        // Add application
        Application application = new Application(applicationDTO.getUserId(), storedJob.getId(), applicationDTO.getApplicationDate(), applicationDTO.getAppliedOn(), Status.valueOf(applicationDTO.getStatus()));
        Application storedApplication = add(application);
        if (storedApplication == null) {
            return null;
        }
        applicationDTO.setApplicationId(storedApplication.getId());

        return applicationDTO;
    }
}
