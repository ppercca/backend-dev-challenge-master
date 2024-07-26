package com.directa24.main.challenge.service;

import com.directa24.main.challenge.exception.CustomException;
import com.directa24.main.challenge.exception.ErrorCode;
import com.directa24.main.challenge.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    private static final String MOVIES_PAGE_RESPONSE = "src/test/resources/moviePageResponse";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    public void getMovies() throws IOException {
        movieService.setBaseUrl("https://directa24-movies.wiremockapi.cloud");

        byte[] moviesPageResponse = Files.readAllBytes((new File(MOVIES_PAGE_RESPONSE)).toPath());
        ResponseEntity<byte[]> moviesPageResponseEntity = new ResponseEntity<>(moviesPageResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(byte[].class)))
            .thenReturn(moviesPageResponseEntity);

        List<Movie> movies =  movieService.getMovies();
        assertNotEquals(0, movies.size());
    }

    @Test
    public void getMoviesHttpClientErrorException() throws IOException {
        movieService.setBaseUrl("https://directa24-movies.wiremockapi.cloud");

        byte[] moviesPageResponse = Files.readAllBytes((new File(MOVIES_PAGE_RESPONSE)).toPath());
        ResponseEntity<byte[]> moviesPageResponseEntity = new ResponseEntity<>(moviesPageResponse, HttpStatus.NOT_FOUND);

        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(byte[].class)))
                .thenReturn(moviesPageResponseEntity);
        CustomException exception = assertThrows(CustomException.class, () -> {
            movieService.getMovies();
        });
        assertEquals(ErrorCode.EXTERNAL_API_ERROR, exception.getErrorCode());
    }

    @Test
    public void getMoviesJsonProcessingException() throws IOException {
        movieService.setBaseUrl("https://directa24-movies.wiremockapi.cloud");

        byte[] moviesPageResponse = MOVIES_PAGE_RESPONSE.getBytes();
        ResponseEntity<byte[]> moviesPageResponseEntity = new ResponseEntity<>(moviesPageResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(byte[].class)))
                .thenReturn(moviesPageResponseEntity);
        CustomException exception = assertThrows(CustomException.class, () -> {
            movieService.getMovies();
        });
        assertEquals(ErrorCode.PARSING_API_ERROR, exception.getErrorCode());
    }

}
