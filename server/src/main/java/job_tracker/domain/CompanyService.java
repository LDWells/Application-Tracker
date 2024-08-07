package job_tracker.domain;

import job_tracker.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.CompanyRepository;
import job_tracker.models.Company;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public Company findCompanyById(int id) {
        return companyRepository.findById(id);
    }

    public Result<Company> addCompany(Company company) {
        Result<Company> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Company>> violations = validator.validate(company);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Company> violation : violations) {
                result.addMessage(violation.getMessage());
            }
            return result;
        }

        result.setPayload(companyRepository.add(company));
        return result;
    }

    public Result<Company> updateCompany(Company company) {
        Result<Company> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Company>> violations = validator.validate(company);
        if(!violations.isEmpty()){
            for(ConstraintViolation<Company> violation : violations) {
                result.addMessage(violation.getMessage());
            }
            return result;
        }

        companyRepository.update(company);
        result.setPayload(company);
        return result;
    }

    public boolean deleteCompanyById(int id) {
        return companyRepository.deleteById(id);
    }
}
