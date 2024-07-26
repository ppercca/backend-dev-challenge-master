package com.directa24.main.challenge.service;

import com.directa24.main.challenge.exception.CustomException;
import com.directa24.main.challenge.exception.ErrorCode;
import com.directa24.main.challenge.model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DirectorServiceTest {

    private static final String MOVIES_RESPONSE = "src/test/resources/moviesResponse.json";

    @InjectMocks
    private DirectorServiceImpl directorService;

    @Mock
    private MovieService movieService;

    @Test
    public void getDirectorsByMovieThreshold() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Movie> movies = mapper.readValue(new File(MOVIES_RESPONSE), new TypeReference<>() {
        });
        when(movieService.getMovies()).thenReturn(movies);
        List<String> directors =  directorService.getDirectorsByMovieThreshold(4);
        assertEquals(2, directors.size());
        assertEquals("Woody Allen", directors.get(1));
        Assertions.assertThat(directors).isSortedAccordingTo(String::compareTo);
    }

    @Test
    public void getDirectorsByMovieThresholdMoviesNotFound() {
        when(movieService.getMovies()).thenReturn(null);
        CustomException exception = assertThrows(CustomException.class, () -> {
            directorService.getDirectorsByMovieThreshold(4);
        });
        assertEquals(ErrorCode.MOVIES_NOT_FOUND, exception.getErrorCode());
    }

}
