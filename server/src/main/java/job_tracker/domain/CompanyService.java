package job_tracker.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import job_tracker.data.CompanyRepository;
import job_tracker.models.Company;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final Validator validator;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, Validator validator) {
        this.companyRepository = companyRepository;
        this.validator = validator;
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public Company findCompanyById(int id) {
        return companyRepository.findById(id);
    }

    public Company addCompany(@Valid Company company) {
        return companyRepository.add(company);
    }

    public boolean updateCompany(@Valid Company company) {
        return companyRepository.update(company);
    }

    public boolean deleteCompanyById(int id) {
        return companyRepository.deleteById(id);
    }
}
