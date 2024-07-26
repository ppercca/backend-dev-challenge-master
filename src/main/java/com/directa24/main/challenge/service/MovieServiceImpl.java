package com.directa24.main.challenge.service;

import com.directa24.main.challenge.exception.CustomException;
import com.directa24.main.challenge.exception.ErrorCode;
import com.directa24.main.challenge.model.Movie;
import com.directa24.main.challenge.model.MoviePageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieService.class);

    @Setter
    @Value("${base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public MovieServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get all the movies from all the pages without duplication
     * @return list of movies
     */
    @Override
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        int page = 1;
        int totalPages = Integer.MAX_VALUE;

        while(page <= totalPages) {
            log.info("Getting movies from page: {}", page);
            MoviePageResponse moviePageResponse = getMoviePageResponse(page);
            if (moviePageResponse == null) {
                return movies;
            }
            movies.addAll(moviePageResponse.getData());
            totalPages = moviePageResponse.getTotalPages();
            page++;
        }

        // Removing duplicate movies
        Set<Movie> uniqueMovies = new HashSet<>(movies);

        return new ArrayList<>(uniqueMovies);
    }

    /**
     * Get movies page from a Rest Client
     * Parsing response to movie page response
     * @param page of movies
     * @return movie page
     */
    private MoviePageResponse getMoviePageResponse(int page) {
        MoviePageResponse moviePageResponse;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = baseUrl + "/api/movies/search";
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("page", page)
                .build()
                .toUri();

        log.info("Request: {} : {}", HttpMethod.GET, url);

        try {
            ResponseEntity<byte[]> response = this.restTemplate.exchange(uri, HttpMethod.GET, entity, byte[].class);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
            }
            String jsonString = new String(response.getBody(), StandardCharsets.UTF_8);
            log.info("Response: {} : {}", response.getStatusCode(), jsonString);
            ObjectMapper objectMapper = new ObjectMapper();
            moviePageResponse = objectMapper.readValue(jsonString, MoviePageResponse.class);
        } catch(HttpClientErrorException e) {
            log.error("Error Response: {} : {}", e.getStatusCode(), e.getMessage());
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }  catch (JsonProcessingException e) {
            log.error("Error parsing response");
            throw new CustomException(ErrorCode.PARSING_API_ERROR);
        }
        return moviePageResponse;
    }

}
