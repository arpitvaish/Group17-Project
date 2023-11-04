package dev.arif.ratingservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ratings {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private int rating;
    private String comment;

    public Ratings() {
    }

    public Ratings(Long id, String productId, int rating, String comment) {
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Long getId() {
        return id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
