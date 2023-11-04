package dev.arif.ratingservice.controller;

import dev.arif.ratingservice.dto.RatingDto;
import dev.arif.ratingservice.models.Ratings;
import dev.arif.ratingservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ratings")
public class RatingController {


    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<RatingDto>> getRatingByProductId(@PathVariable("productId") String productId){

        List<Ratings> ratings = ratingService.getRatingByProductId(productId);

        if(ratings.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<RatingDto> ratingDtos = ratings.stream()
                .map(RatingDto::from)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ratingDtos, HttpStatus.OK);
    }

    @GetMapping("/rating/{id}")
    public ResponseEntity<RatingDto> getRatingById(@PathVariable("id") Long id){
        Ratings ratings = ratingService.getRatingById(id);
        if(ratings == null)
        {
            return ResponseEntity.notFound().build();
        }
        RatingDto ratingDto = RatingDto.from(ratings);
        return new ResponseEntity<>(ratingDto, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<RatingDto> saveRating(@RequestBody RatingDto ratingDto){
        Ratings ratings = RatingDto.to(ratingDto);
        Ratings savedRatings = ratingService.saveRating(ratings);
        RatingDto savedRatingDto = RatingDto.from(savedRatings);
        return new ResponseEntity<>(savedRatingDto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRatingById(@PathVariable("id") Long id)
    {

        Ratings rating = ratingService.getRatingById(id);
        if(rating != null){
            ratingService.deleteRatingById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<RatingDto> updateRatingById(@PathVariable("id") Long id, @RequestBody RatingDto dto){

        Ratings ratings = ratingService.getRatingById(id);
        if(ratings == null)
        {
            return ResponseEntity.notFound().build();
        }
        Ratings updatedRatings = RatingDto.to(dto);
        Ratings savedRatings = ratingService.saveRating(updatedRatings);
        RatingDto savedRatingDto = RatingDto.from(savedRatings);
        return new ResponseEntity<>(savedRatingDto, HttpStatus.OK);

    }

    @GetMapping("/{productId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable("productId") String productId){
        double averageRating = ratingService.getAverageRating(productId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

}
