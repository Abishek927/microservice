package com.rating.RatingService.controller;

import com.rating.RatingService.entities.Hotel;
import com.rating.RatingService.entities.Rating;
import com.rating.RatingService.services.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final Logger  logger= LoggerFactory.getLogger(RatingController.class);
    @Autowired
    private RatingService ratingService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));

    }
    @GetMapping
    public ResponseEntity<List<Rating>> getRatings(){
        return ResponseEntity.ok(ratingService.getRatings());
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUser(@PathVariable String userId){
        //http://localhost:8083/ratings/users/b1178ca6-980e-4358-bdda-c7c66b8c09d8
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
    }
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotel(@PathVariable String hotelId){
        List<Rating> ratings=ratingService.getRatingByHotelId(hotelId);

         return ResponseEntity.ok(ratings);

    }


}
