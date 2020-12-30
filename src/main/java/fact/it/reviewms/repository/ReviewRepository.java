package fact.it.reviewms.repository;

import fact.it.reviewms.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findReviewsByMovieUuid(String movieUuid);
    List<Review> findReviewsByRatingGreaterThan(Double rating);
    Review findReviewByMovieUuid(String movieUuid);
    Review findReviewByUuid(String uuid);
}
