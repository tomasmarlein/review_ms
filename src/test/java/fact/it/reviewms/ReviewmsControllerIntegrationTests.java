package fact.it.reviewms;

import fact.it.reviewms.model.Review;
import fact.it.reviewms.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewmsControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    private Review review1Movie1 = new Review(1, 1, "review 1 movie 1", 7.25, new Date());
    private Review review2Movie2 = new Review(2, 2, "review 2 movie 2", 2.25, new Date());
    private Review review3Movie2 = new Review(3, 2, "review 3 movie 2", 3.75, new Date());
    private Review reviewToBeDeleted = new Review(4, 3, "review 4 movie 3 to be deleted", 9.25, new Date());

    @BeforeEach
    public void beforeAllTests() {
        reviewRepository.deleteAll();
        reviewRepository.save(review1Movie1);
        reviewRepository.save(review2Movie2);
        reviewRepository.save(review3Movie2);
        reviewRepository.save(reviewToBeDeleted);
    }

    @AfterEach
    public void afterAllTests() {
        reviewRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenReview_whenGetReviewsByMovieUuid_thenReturnJsonReviews() throws Exception {
        mockMvc.perform(get("/reviews/movie/{movieUuid}", 2))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].uuid", is(2)))
                .andExpect(jsonPath("$[0].movieUuid", is(2)))
                .andExpect(jsonPath("$[0].text", is("review 2 movie 2")))
                .andExpect(jsonPath("$[0].rating", is(2.25)))
                .andExpect(jsonPath("$[1].uuid", is(3)))
                .andExpect(jsonPath("$[1].movieUuid", is(2)))
                .andExpect(jsonPath("$[1].text", is("review 3 movie 2")))
                .andExpect(jsonPath("$[1].rating", is(3.75)));
    }

    @Test
    public void givenReview_whenGetAllReviews_thenReturnJsonReviews() throws Exception {
        mockMvc.perform(get("/reviews/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].uuid", is(1)))
                .andExpect(jsonPath("$[0].movieUuid", is(1)))
                .andExpect(jsonPath("$[0].text", is("review 1 movie 1")))
                .andExpect(jsonPath("$[0].rating", is(7.25)))
                .andExpect(jsonPath("$[1].uuid", is(2)))
                .andExpect(jsonPath("$[1].movieUuid", is(2)))
                .andExpect(jsonPath("$[1].text", is("review 2 movie 2")))
                .andExpect(jsonPath("$[1].rating", is(2.25)))
                .andExpect(jsonPath("$[2].uuid", is(3)))
                .andExpect(jsonPath("$[2].movieUuid", is(2)))
                .andExpect(jsonPath("$[2].text", is("review 3 movie 2")))
                .andExpect(jsonPath("$[2].rating", is(3.75)))
                .andExpect(jsonPath("$[3].uuid", is(4)))
                .andExpect(jsonPath("$[3].movieUuid", is(3)))
                .andExpect(jsonPath("$[3].text", is("review 4 movie 3 to be deleted")))
                .andExpect(jsonPath("$[3].rating", is(9.25)));
    }

    @Test
    public void whenPostReview_thenReturnJsonReview() throws Exception {
        Review review5Movie4 = new Review(5, 4, "review 5 movie 4", 5.25, new Date());

        mockMvc.perform(post("/reviews")
                .content(mapper.writeValueAsString(review1Movie1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is(1)))
                .andExpect(jsonPath("$.movieUuid", is(1)))
                .andExpect(jsonPath("$.text", is("review 1 movie 1")))
                .andExpect(jsonPath("$.rating", is(7.25)));
    }

    @Test
    public void givenReview_whenPutReview_thenReturnJsonReview() throws Exception {
        Review updatedReview = new Review(2, 2, "updated", 1.15, new Date());

        mockMvc.perform(put("/reviews")
                .content(mapper.writeValueAsString(updatedReview))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is(2)))
                .andExpect(jsonPath("$.movieUuid", is(2)))
                .andExpect(jsonPath("$.text", is("updated")))
                .andExpect(jsonPath("$.rating", is(1.15)));
    }

    @Test
    public void givenReview_whenDeleteReview_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/reviews/{uuid}", 4)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoReview_whenDeleteReview_thenStatusNotFound() throws Exception {
        mockMvc.perform(delete("/reviews/{uuid}", 100)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
