package dev.arif.ratingservice.repository;

import dev.arif.ratingservice.models.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Ratings, Long> {
    List<Ratings> findByProductId(String productId);
}
