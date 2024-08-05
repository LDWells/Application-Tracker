package learn.job_tracker.data.mappers;

import learn.job_tracker.models.Company;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company  = new Company();
        company.setCompanyId(resultSet.getInt("id"));
        company.setCompanyName(resultSet.getString("name")); //change to company_name if this cause problems
        company.setCompanyAdrress(resultSet.getString("address"));

        return company;
    }
}
}
