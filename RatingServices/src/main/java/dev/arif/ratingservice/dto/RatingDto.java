package dev.arif.ratingservice.dto;

import dev.arif.ratingservice.models.Ratings;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RatingDto {
    private String productId;
    private int rating;
    private String comment;

    public static RatingDto from(Ratings ratings) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setProductId(ratings.getProductId());
        ratingDto.setRating(ratings.getRating());
        ratingDto.setComment(ratings.getComment());
        return ratingDto;
    }

    public static Ratings to(RatingDto ratingDto) {
        Ratings ratings = new Ratings();
        ratings.setProductId(ratingDto.getProductId());
        ratings.setRating(ratingDto.getRating());
        ratings.setComment(ratingDto.getComment());
        return ratings;
    }
}
