/************************************************************************
 * Author: LaDarion Wells
 * Date Created: 8/5/2024
 ************************************************************************/
package job_tracker.data.mappers;

import job_tracker.models.Company;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company  = new Company();
        company.setId(resultSet.getInt("id"));
        company.setName(resultSet.getString("name")); //change to company_name if this cause problems
        company.setAddress(resultSet.getString("address"));

        return company;
    }
}

