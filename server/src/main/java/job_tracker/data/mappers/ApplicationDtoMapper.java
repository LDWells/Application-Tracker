package job_tracker.data.mappers;

import job_tracker.models.ApplicationDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationDtoMapper implements RowMapper<ApplicationDTO> {

    @Override
    public ApplicationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setCompanyId(rs.getInt("company_id"));
        dto.setCompanyName(rs.getString("company_name"));
        dto.setCompanyAddress(rs.getString("company_address"));
        dto.setJobId(rs.getInt("job_id"));
        dto.setJobTitle(rs.getString("job_title"));
        dto.setJobDescription(rs.getString("job_description"));
        dto.setApplicationId(rs.getInt("application_id"));
        dto.setUserId(rs.getInt("user_id"));
        dto.setApplicationDate(rs.getDate("application_date").toLocalDate());
        dto.setAppliedOn(rs.getString("applied_on"));
        dto.setStatus(rs.getString("status"));
        return dto;
    }
}
