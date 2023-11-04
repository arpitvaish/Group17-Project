package dev.arif.ratingservice.service;

import dev.arif.ratingservice.models.Ratings;
import dev.arif.ratingservice.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    public  List<Ratings>  getRatingByProductId(String productId){
        return ratingRepository.findByProductId(productId);
    }
    public  Ratings saveRating(Ratings ratings){
        return ratingRepository.save(ratings);
    }

    public  Ratings getRatingById(Long id){
        return ratingRepository.findById(id).orElse(null);
    }
    public  void deleteRatingById(Long id){
        ratingRepository.deleteById(id);
    }
    public  Ratings updateRatingById(Long id, Ratings ratings){
        Ratings existingRating = ratingRepository.findById(id).orElse(null);
        if(existingRating != null){
            existingRating.setComment(ratings.getComment());
            existingRating.setProductId(ratings.getProductId());
            existingRating.setRating(ratings.getRating());
            return ratingRepository.save(existingRating);
        }
        return null;
    }

    public double getAverageRating(String productId) {
        List<Ratings> ratings = ratingRepository.findByProductId(productId);
        double sum = 0;
        for (Ratings rating : ratings) {
            sum += rating.getRating();
        }
        return sum / ratings.size();
    }


}
