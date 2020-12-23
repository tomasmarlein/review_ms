package fact.it.reviewms;

import fact.it.reviewms.model.Review;
import fact.it.reviewms.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

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
public class ReviewmsControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewRepository reviewRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenReview_whenGetReviewsByMovieUuid_thenReturnJsonReviews() throws Exception {
        Review review1Movie1 = new Review(1, 1, "review 1 movie 1", 7.25, new Date());
        Review review2Movie1 = new Review(2, 1, "review 2 movie 1", 2, new Date());

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review1Movie1);
        reviewList.add(review2Movie1);

        given(reviewRepository.findReviewsByMovieUuid(1)).willReturn(reviewList);

        mockMvc.perform(get("/reviews/movie/{movieUuid}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].uuid", is(1)))
                .andExpect(jsonPath("$[0].movieUuid", is(1)))
                .andExpect(jsonPath("$[0].text", is("review 1 movie 1")))
                .andExpect(jsonPath("$[0].rating", is(7.25)))
                .andExpect(jsonPath("$[1].uuid", is(2)))
                .andExpect(jsonPath("$[1].movieUuid", is(1)))
                .andExpect(jsonPath("$[1].text", is("review 2 movie 1")))
                .andExpect(jsonPath("$[1].rating", is(2.0)));
    }

//    @Test
//    public void givenReview_whenGetReviewsByBestRated_thenReturnJsonReviews() throws Exception {
//        Review review1Movie1 = new Review(1, 1, "best rated", 7.25, new Date());
//        Review review2Movie2 = new Review(2, 2, "not best rated", 2, new Date());
//        Review review3Movie3 = new Review(3, 3, "best rated", 10, new Date());
//
//        List<Review> reviewList = new ArrayList<>();
//        reviewList.add(review1Movie1);
//        reviewList.add(review2Movie2);
//        reviewList.add(review3Movie3);
//
//        given(reviewRepository.findReviewsByRatingGreaterThan((double) 6)).willReturn(reviewList);
//
//        mockMvc.perform(get("/reviews/bestrated"))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].uuid", is(1)))
//                .andExpect(jsonPath("$[0].movieUuid", is(1)))
//                .andExpect(jsonPath("$[0].text", is("best rated")))
//                .andExpect(jsonPath("$[0].rating", is(7.25)))
//                .andExpect(jsonPath("$[1].uuid", is(3)))
//                .andExpect(jsonPath("$[1].movieUuid", is(3)))
//                .andExpect(jsonPath("$[1].text", is("best rated")))
//                .andExpect(jsonPath("$[1].rating", is(10.0)));
//    }

    @Test
    public void givenReview_whenGetAllReviews_thenReturnJsonReviews() throws Exception {
        Review review1Movie1 = new Review(1, 1, "review 1 movie 1", 7.25, new Date());
        Review review2Movie1 = new Review(2, 1, "review 2 movie 1", 2, new Date());

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review1Movie1);
        reviewList.add(review2Movie1);

        given(reviewRepository.findAll()).willReturn(reviewList);

        mockMvc.perform(get("/reviews/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].uuid", is(1)))
                .andExpect(jsonPath("$[0].movieUuid", is(1)))
                .andExpect(jsonPath("$[0].text", is("review 1 movie 1")))
                .andExpect(jsonPath("$[0].rating", is(7.25)))
                .andExpect(jsonPath("$[1].uuid", is(2)))
                .andExpect(jsonPath("$[1].movieUuid", is(1)))
                .andExpect(jsonPath("$[1].text", is("review 2 movie 1")))
                .andExpect(jsonPath("$[1].rating", is(2.0)));
    }

    @Test
    public void whenPostReview_thenReturnJsonReview() throws Exception {
        Review review1Movie1 = new Review(1, 1, "review 1 movie 1", 7.25, new Date());

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
        Review review1Movie1 = new Review(1, 1, "review 1 movie 1", 7.25, new Date());

        given(reviewRepository.findReviewByUuid(1)).willReturn(review1Movie1);

        Review updatedReview = new Review(1, 1, "review 1 movie 1 updated", 8.25, new Date());

        mockMvc.perform(put("/reviews")
                .content(mapper.writeValueAsString(updatedReview))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is(1)))
                .andExpect(jsonPath("$.movieUuid", is(1)))
                .andExpect(jsonPath("$.text", is("review 1 movie 1 updated")))
                .andExpect(jsonPath("$.rating", is(8.25)));
    }

    @Test
    public void givenReview_whenDeleteReview_thenStatusOk() throws Exception {
        Review reviewToBeDeleted = new Review(1, 1, "review to delete", 7.25, new Date());

        given(reviewRepository.findReviewByUuid(1)).willReturn(reviewToBeDeleted);

        mockMvc.perform(delete("/reviews/{uuid}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoReview_whenDeleteReview_thenStatusNotFound() throws Exception {
        given(reviewRepository.findReviewByUuid(1000)).willReturn(null);

        mockMvc.perform(delete("/reviews/{uuid}", 1000)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}