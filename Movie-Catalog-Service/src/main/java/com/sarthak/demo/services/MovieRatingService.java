package com.sarthak.demo.services;

import java.util.ArrayList;
import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sarthak.demo.models.Rating;
import com.sarthak.demo.models.UserRatingList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieRatingService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getRatingFallBack")
    public UserRatingList getRatingList(String userId) {
        UserRatingList ratingsList = restTemplate
        .getForObject("http://movie-rating-service/ratings/users/" + userId.toString(), 
        UserRatingList.class);
        return ratingsList;
    }

    public UserRatingList getRatingFallBack(String userId) {
        List<Rating> list = new ArrayList<>();
        UserRatingList userRatingList = new UserRatingList();
        userRatingList.setRatings(list);
        userRatingList.setUserId((userId));
        return userRatingList;
    }
}
