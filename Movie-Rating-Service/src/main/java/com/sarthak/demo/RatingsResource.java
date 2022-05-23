package com.sarthak.demo;

import java.util.Arrays;
import java.util.List;

import com.sarthak.demo.model.Rating;
import com.sarthak.demo.model.UserRatingList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingsResource {
    List<Rating> ratingsList = Arrays.asList(new Rating("1234", 5), new Rating("5678", 4));

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRatingList getMoviesForUser(@PathVariable("userId") String userId) {
    UserRatingList userRatingList = new UserRatingList();
    userRatingList.setUserId((userId));
    userRatingList.setRatings(ratingsList);
    return userRatingList;
    }
}
