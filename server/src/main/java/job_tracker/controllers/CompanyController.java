package job_tracker.controllers;

import job_tracker.domain.CompanyService;
import job_tracker.domain.Result;
import job_tracker.models.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> findById(@PathVariable int companyId) {
        Company company = companyService.findById(companyId);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Company company) {
        Result<Company> result = companyService.add(company);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<Object> update(@PathVariable int companyId, @RequestBody Company company) {
        if (companyId != company.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Company> result = companyService.update(company);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteById(@PathVariable int companyId) {
        if (companyService.deleteById(companyId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
