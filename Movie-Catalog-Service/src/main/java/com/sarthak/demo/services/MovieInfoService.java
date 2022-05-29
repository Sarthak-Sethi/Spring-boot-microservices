package com.sarthak.demo.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sarthak.demo.models.Movie;
import com.sarthak.demo.models.Rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MovieInfoService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getMovieInfoFallback")
    public Movie getMovieInfo(String userId,Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return movie;
    }

    public Movie getMovieInfoFallback(String userId,Rating rating) {
      return new Movie("No id", "No Movie","no desc");
    }
}
