package com.cine.cine.service;


import com.cine.cine.dto.TmdbResponse;
import com.cine.cine.entity.Movie;
import com.cine.cine.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TmdbService {

    private final RestTemplate restTemplate;
    private final MovieRepository movieRepository;
    private static final Logger logger = LoggerFactory.getLogger(TmdbService.class);

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final String urlTemplate = "https://api.themoviedb.org/3/movie/now_playing?api_key=%s&language=es-ES&include_adult=false&page=1";

    public TmdbService(RestTemplate restTemplate, MovieRepository movieRepository) {
        this.restTemplate = restTemplate;
        this.movieRepository = movieRepository;
    }

    public void fetchAndSaveMovies() {
        String url = String.format(urlTemplate, apiKey);
        logger.info("Fetching movies from TMDB API: {}", url);
        try {
            TmdbResponse response = restTemplate.getForObject(url, TmdbResponse.class);
            if (response != null && response.getResults() != null) {
                List<Movie> movies = response.getResults().stream()
                        .filter(result -> result.getPosterPath() != null)
                        .limit(20)
                        .map(result -> new Movie(result.getId(), result.getTitle(), result.getPosterPath()))
                        .collect(Collectors.toList());
                movieRepository.saveAll(movies);
                logger.info("Movies fetched and saved successfully!");
            } else {
                logger.warn("No movies found in the TMDB response.");
            }
        } catch (HttpClientErrorException e) {
            logger.error("Error fetching movies from TMDB API: {}", e.getMessage());
        }
    }
}
