package com.sarthak.demo;

import java.util.Arrays;
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
import com.sarthak.demo.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	public WebClient.Builder webClientaBuilder;

	@RequestMapping("{userId}")
	public List<CatalogItem> getCatalogItems(@PathVariable String userId) {
		List<Rating> ratingsList = Arrays.asList(new Rating("1234", 5), new Rating("5678", 4));

		return ratingsList.stream().map(rating -> {
			
			// Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" +
			// rating.getMovieId(), Movie.class);

			// ? WebCleint is reactive way to handle api, RestTemplate, the above line is the old way
			Movie movie = this.webClientaBuilder.build()
					.get()
					.uri("http://localhost:8082/movies/" + rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();
			return new CatalogItem(movie.getName(), movie.getDesc(), rating.getRating());
		}).collect(Collectors.toList());
		// return this.catalogs;

	}

}
