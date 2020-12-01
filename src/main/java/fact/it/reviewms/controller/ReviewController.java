package fact.it.reviewms.controller;

import fact.it.reviewms.model.Review;
import fact.it.reviewms.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    public void fillTheDatabaseWithDummyData(){
        if(reviewRepository.count()==0){
            reviewRepository.save(new Review(1, "Supergoeie film",7.8));
            reviewRepository.save(new Review(2, "Amai dieje humor",9));
            reviewRepository.save(new Review(3, "Beste film van De Rock",10));
            reviewRepository.save(new Review(4, "Wa een rommel",2.1));
            reviewRepository.save(new Review(5, "Hmmm, kan beter",3.5));

        }

//        System.out.println("Reviews test: " + reviewRepository.findAll());
    }

    @GetMapping("/reviews/{movieId}")
    public List<Review> getReviewsByMovieId(@PathVariable Integer movieId){
        return reviewRepository.findReviewsByMovieId(movieId);
    }

}
