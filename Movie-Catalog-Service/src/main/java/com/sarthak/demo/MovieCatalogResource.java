package com.sarthak.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.sarthak.demo.models.CatalogItem;
import com.sarthak.demo.models.Movie;
import com.sarthak.demo.models.UserRatingList;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	public WebClient.Builder webClientaBuilder;

	@RequestMapping("{userId}")
	public List<CatalogItem> getCatalogItems(@PathVariable String userId) {
		UserRatingList ratingsList = restTemplate
				.getForObject("http://movie-rating-service/ratings/users/" + userId.toString(), 
				UserRatingList.class);

		return ratingsList.getRatings().stream().map(rating -> {
			// ? WebCleint is reactive way to handle api, RestTemplate,  the old way is restTemplate, see lines belo for web client
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(), movie.getDesc(), rating.getRating());
		}).collect(Collectors.toList());
		
	}
}
// ? webClient way
// Movie movie = this.webClientaBuilder.build()
// .get()
// .uri("http://movie-info-service/movies/" + rating.getMovieId())
// .retrieve()
// .bodyToMono(Movie.class)
// .block();
// return new CatalogItem(movie.getName(), movie.getDesc(), rating.getRating());
// }).collect(Collectors.toList());