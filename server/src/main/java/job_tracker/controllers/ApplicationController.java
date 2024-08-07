package job_tracker.controllers;

import job_tracker.domain.ApplicationService;
import job_tracker.domain.Result;
import job_tracker.models.Application;
import job_tracker.models.ApplicationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<Application> findAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> findById(@PathVariable int applicationId) {
        Application application = applicationService.findById(applicationId);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(application);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Application application) {
        Result<Application> result = applicationService.add(application);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{applicationId}")
    public ResponseEntity<Object> update(@PathVariable int applicationId, @RequestBody Application application) {
        if (applicationId != application.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Application> result = applicationService.update(application);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteById(@PathVariable int applicationId) {
        if (applicationService.deleteById(applicationId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/details")
    public List<ApplicationDTO> findAllApplicationsWithDetails() {
        return applicationService.findAllApplicationsWithDetails();
    }

    @GetMapping("/details/{applicationId}")
    public ResponseEntity<ApplicationDTO> findApplicationByIdWithDetails(@PathVariable int applicationId) {
        ApplicationDTO applicationDTO = applicationService.findApplicationByIdWithDetails(applicationId);
        if (applicationDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(applicationDTO);
    }
}
