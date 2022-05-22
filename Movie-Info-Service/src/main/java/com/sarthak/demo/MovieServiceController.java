package com.sarthak.demo;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sarthak.demo.models.Movie;

@RestController
@RequestMapping("/movies")
public class MovieServiceController {
	HashMap<String,Movie> map = new HashMap<>();
    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
    	map.put("1234", new Movie("1234", "3 idiots","best funnny movie "));
    	map.put("5678", new Movie("5678", " taare zameen par","best movie with kids and lesson "));
        return map.get(movieId) ;
    }
	
}
