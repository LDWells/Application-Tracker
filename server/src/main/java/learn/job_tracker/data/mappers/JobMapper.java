package learn.job_tracker.data.mappers;

import learn.job_tracker.models.Job;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JobMapper implements RowMapper<Job> {

        @Override
        public Job mapRow(ResultSet resultSet, int i) throws SQLException {
            Job job  = new Job();
            job.setJobId(resultSet.getInt("id"));
            job.setTitle(resultSet.getString("title")); //change to company_name if this cause problems
            job.setDescription(resultSet.getString("description"));
            job.setCompanyId(resultSet.getInt("company_id"));
            return job;
        }
}
