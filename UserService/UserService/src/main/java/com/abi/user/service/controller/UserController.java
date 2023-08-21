package com.abi.user.service.controller;

import com.abi.user.service.entities.Hotel;
import com.abi.user.service.entities.Ratings;
import com.abi.user.service.entities.User;
import com.abi.user.service.proxy.HotelService;
import com.abi.user.service.services.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger logger= LoggerFactory.getLogger(UserController.class);
    private final String ratingUrl="http://RATING-SERVICE/ratings/users/";
    private final String hotelUrl="http://HOTEL-SERVICE/hotels/";

    @Autowired
    private UserService userService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RestTemplate restTemplate;
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1=userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){

        User user=userService.getUser(userId);
        Ratings[] ratingsArray=restTemplate.getForObject(ratingUrl+user.getUserId(), Ratings[].class);//ArrayList cannot be mapped directly to rating array-list
        List<Ratings> userRatings=Arrays.stream(ratingsArray).collect(Collectors.toList());
        for (Ratings eachRating:userRatings
             ) {
            //ResponseEntity<Hotel> forObject=restTemplate.getForEntity(hotelUrl+eachRating.getHotelId(),Hotel.class);
            Hotel hotel=hotelService.getHotel(eachRating.getHotelId());
            eachRating.setHotel(hotel);

        }
        logger.info("Rating for the user {}",userRatings);
        user.setRatings(userRatings);

        return ResponseEntity.ok(user);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users=userService.getAllUser();
        return ResponseEntity.ok(users);
    }


}
