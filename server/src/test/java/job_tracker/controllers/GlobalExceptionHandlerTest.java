package job_tracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import job_tracker.data.CommentRepository;
import job_tracker.models.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @MockBean
    CommentRepository repository;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldHandleExceptions() throws Exception {
        when(repository.add(any())).thenThrow(RuntimeException.class);

        Comment comment = new Comment(1, 1, 1, "joe", "Test comment", LocalDate.of(2023, 1, 1));

        String commentJson = objectMapper.writeValueAsString(comment);

        ErrorResponse errorResponse = new ErrorResponse("Something went wrong on our end. Your request failed. :(");
        String expectedJson = objectMapper.writeValueAsString(errorResponse);

        var request = post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentJson);

        mvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldHandleDataIntegrityExceptions() throws Exception {
        when(repository.add(any())).thenThrow(DataIntegrityViolationException.class);

        Comment comment = new Comment(1, 1, 1, "joe", "Test comment", LocalDate.of(2023, 1, 1));

        String commentJson = objectMapper.writeValueAsString(comment);

        ErrorResponse errorResponse = new ErrorResponse("Something went wrong in the database. " +
                "Please ensure that any referenced records exist. Your request failed. :(");
        String expectedJson = objectMapper.writeValueAsString(errorResponse);

        var request = post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentJson);

        mvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(expectedJson));
    }
}
