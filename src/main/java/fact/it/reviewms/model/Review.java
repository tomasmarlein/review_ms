package fact.it.reviewms.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "revieuws")
public class Review {
    @Id
    private String id;
    private Integer uuid;
//    private Integer movieId;
    private Integer movieUuid;
    private String text;
    private double rating;
    private Date date;

    public Review() {
    }

    public Review(Integer uuid, Integer movieUuid, String text, double rating, Date date) {
//        this.id = id;
        this.uuid = uuid;
        this.movieUuid = movieUuid;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getMovieUuid() {
        return movieUuid;
    }

    public void setMovieUuid(Integer movieUuid) {
        this.movieUuid = movieUuid;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
