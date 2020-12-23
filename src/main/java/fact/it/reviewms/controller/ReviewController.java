package fact.it.reviewms.controller;

import fact.it.reviewms.model.Review;
import fact.it.reviewms.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    public void fillTheDatabaseWithDummyData(){
        if(reviewRepository.count()==0){
            reviewRepository.save(new Review(1,1, "Supergoeie film",7.8, new Date()));
            reviewRepository.save(new Review(2,2, "Amai dieje humor",9, new Date()));
            reviewRepository.save(new Review(3,3, "Beste film van De Rock",10, new Date()));
            reviewRepository.save(new Review(4,4, "Wa een rommel",2.1, new Date()));
            reviewRepository.save(new Review(5,5, "Hmmm, kan beter",3.5, new Date()));
        }

//        System.out.println("Reviews test: " + reviewRepository.findAll());
    }

    @GetMapping("/reviews/movie/{movieUuid}")
    public List<Review> getReviewsByMovieUuid(@PathVariable Integer movieUuid){
        return reviewRepository.findReviewsByMovieUuid(movieUuid);
    }

    @GetMapping("/reviews/bestrated")
    public List<Review> getReviewsByBestRated(){
        return reviewRepository.findReviewsByRatingGreaterThan((double) 6);
//        return reviewRepository.giveListOfBestRatedMovies();
    }

    @GetMapping("/reviews/all")
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    @PostMapping("/reviews")
    public Review addReview(@RequestBody Review review){
        reviewRepository.save(review);
        return review;
    }

    @PutMapping("/reviews")
    public Review updateReview(@RequestBody Review newReview){
        Review oldReview = reviewRepository.findReviewByMovieUuid(newReview.getMovieUuid());

        oldReview.setText(newReview.getText());
        oldReview.setRating(newReview.getRating());

        reviewRepository.save(oldReview);

        return oldReview;
    }

    @DeleteMapping("/reviews/movie/{movieUuid}")
    public ResponseEntity deleteReview(@PathVariable Integer movieUuid){
        Review review = reviewRepository.findReviewByMovieUuid(movieUuid);
        if(review!=null){
            reviewRepository.delete(review);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
