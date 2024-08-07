package job_tracker.controllers;

import job_tracker.data.JdbcAppUserRepository;
import job_tracker.models.AppUser;
import job_tracker.security.JwtConverter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest
{
    @MockBean
    JdbcAppUserRepository repository;

    @Autowired
    MockMvc mvc;

    @Autowired
    JwtConverter jwtConverter;

    String token;

    @BeforeEach
    void setup()
    {
        AppUser appUser = new AppUser(1, "admin", "top-secret-password", false,
                List.of("ADMIN"));

        when(repository.findByUsername("admin")).thenReturn(appUser);

        token = jwtConverter.getTokenFromUser(appUser);
    }
}