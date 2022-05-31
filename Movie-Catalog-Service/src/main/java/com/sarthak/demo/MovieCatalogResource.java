package com.sarthak.demo;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.sarthak.demo.models.CatalogItem;
import com.sarthak.demo.models.DbSetting;
import com.sarthak.demo.models.Movie;
import com.sarthak.demo.models.UserRatingList;
import com.sarthak.demo.services.MovieInfoService;
import com.sarthak.demo.services.MovieRatingService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	public WebClient.Builder webClientaBuilder;

	@Autowired
	public MovieRatingService movieRatingService;

	@Autowired
	public MovieInfoService movieInfoService;

	@Autowired
	DbSetting dbSetting;

	@Value("${welcome_message}")
	private String customMessage;
	// you may also pass the default value in case , spring doesnt find welcome_message during compile time
	// @Value("${welcome_message} : write default value here")


	@GetMapping("/message")
	public String getMessage() {
		return this.customMessage;
	}

	@GetMapping("/configurationproperties")
	public String getConfigurationProperties() {
		return dbSetting.getHost()+dbSetting.getConnectionUrl()+dbSetting.getPort();
	}
	
	@RequestMapping("{userId}")
	public List<CatalogItem> getCatalogItems(@PathVariable String userId) {
		UserRatingList ratingsList = movieRatingService.getRatingList(userId);

		return ratingsList.getRatings().stream().map(rating -> {
			Movie movie = movieInfoService.getMovieInfo(userId, rating);
			return new CatalogItem(movie.getName(), movie.getDesc(), rating.getRating());
		}).collect(Collectors.toList());
		
	}

	
	// public List<CatalogItem> getFallBackForCataLog(@PathVariable String userId) {
	// 	return Arrays.asList(new CatalogItem("No Movie", "desc", 0));
	// }
}
// ? webClient way
// ? WebCleint is reactive way to handle api, RestTemplate,  the old way is restTemplate, see lines below for web client
// Movie movie = this.webClientaBuilder.build()
// .get()
// .uri("http://movie-info-service/movies/" + rating.getMovieId())
// .retrieve()
// .bodyToMono(Movie.class)
// .block();
// return new CatalogItem(movie.getName(), movie.getDesc(), rating.getRating());
// }).collect(Collectors.toList());