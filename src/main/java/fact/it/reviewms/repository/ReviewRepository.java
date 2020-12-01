package fact.it.reviewms.repository;

import fact.it.reviewms.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findReviewsByMovieId(Integer movieId);
}
