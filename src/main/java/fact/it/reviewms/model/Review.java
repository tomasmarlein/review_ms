package fact.it.reviewms.model;


import org.springframework.data.annotation.Id;

public class Review {
    @Id
    private String id;
    private Integer movieId;
    private String text;
    private double rating;

    public Review() {
    }

    public Review(Integer movieId, String text, double rating) {
        this.movieId = movieId;
        this.text = text;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
