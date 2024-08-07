package job_tracker.domain;

import job_tracker.data.CompanyRepository;
import job_tracker.models.Application;
import job_tracker.models.Comment;
import job_tracker.models.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    CompanyService service;

    @MockBean
    CompanyRepository repository;

    @Test
    void shouldFindById(){
        Company expected = makeCompany();
        when(repository.findById(1)).thenReturn(expected);
        Company actual = service.findCompanyById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByInvalidId(){
        Company expected = makeCompany();
        when(repository.findById(1)).thenReturn(expected);
        Company actual = service.findCompanyById(-1);
        assertNotEquals(expected, actual);
    }

    @Test
    void shouldAddWhenValid(){
        Company expected = makeCompany();
        Company arg = makeCompany();

        when(repository.add(arg)).thenReturn(expected);
        Result<Company> result = service.addCompany(arg);
        assertNotNull(result.getPayload());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidName(){
        Company company = makeCompany();
        company.setName("This name is longer than one hundred characters long This name is longer than one hundred characters long");

        Result<Company> result = service.addCompany(company);
        assertNull(result.getPayload());

        company.setName(null);
        result = service.addCompany(company);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalidAddress(){
        Company company = makeCompany();
        company.setAddress("This name is longer than two hundred characters long This name is longer than two hundred characters long" +
                "This name is longer than two hundred characters long This name is longer than two hundred characters long" +
                "This name is longer than two hundred characters long This name is longer than two hundred characters long" +
                "This name is longer than two hundred characters long This name is longer than two hundred characters long");

        Result<Company> result = service.addCompany(company);
        assertNull(result.getPayload());

        company.setName(null);
        result = service.addCompany(company);
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate(){
        Company company = makeCompany();

        company.setName("Testing Company");
        when(repository.update(company)).thenReturn(true);
        Result<Company> result = service.updateCompany(company);
        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());
    }

    @Test
    void shouldNotUpdateWhenInvalidName(){
        Company company = makeCompany();

        company.setName(null);
        Result<Company> result = service.updateCompany(company);
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalidAddress(){
        Company company = makeCompany();

        company.setAddress("This name is longer than two hundred characters long This name is longer than two hundred characters long" +
                "This name is longer than two hundred characters long This name is longer than two hundred characters long" +
                "This name is longer than two hundred characters long This name is longer than two hundred characters long" +
                "This name is longer than two hundred characters long This name is longer than two hundred characters long");
        Result<Company> result = service.updateCompany(company);
        assertNull(result.getPayload());
    }

    @Test
    void shouldDeleteById(){
        Company company = makeCompany();
        Company expected = makeCompany();
        when(repository.add(company)).thenReturn(expected);
        Result<Company> result = service.addCompany(company);

        assertNotNull(result.getPayload());
        assertEquals(0, result.getMessages().size());

        when(repository.deleteById(company.getId())).thenReturn(true);
        boolean deletedResult = service.deleteCompanyById(company.getId());
        assertTrue(deletedResult);
    }

    Company makeCompany(){
        Company company = new Company();
        company.setId(1);
        company.setName("Jigglypuff inc.");
        company.setAddress("1237 N Pikachu Rd.");

        return company;
    }
}
