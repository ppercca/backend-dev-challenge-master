package com.directa24.main.challenge.service;

import com.directa24.main.challenge.exception.CustomException;
import com.directa24.main.challenge.exception.ErrorCode;
import com.directa24.main.challenge.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DirectorServiceImpl implements DirectorService{

    private final Logger log = LoggerFactory.getLogger(DirectorService.class);

    private final MovieService movieService;

    public DirectorServiceImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Get a list of directors who exceed the movie threshold
     * @param threshold minimum threshold of movies per director
     * @return list of directors
     */
    @Override
    public List<String> getDirectorsByMovieThreshold(int threshold) {
        log.info("Getting directors by movie threshold: {}", threshold);
        Set<String> directors = new HashSet<>();
        Map<String, Integer> directorsMap = new HashMap<>();

        List<Movie> movies = movieService.getMovies();
        if (movies == null) {
            throw new CustomException(ErrorCode.MOVIES_NOT_FOUND);
        }
        movies.forEach(movie -> {
            if (directorsMap.containsKey(movie.getDirector())) {
                int moviesCount = directorsMap.get(movie.getDirector()) + 1;
                directorsMap.put(movie.getDirector(),  moviesCount);
                if (moviesCount > threshold) {
                    directors.add(movie.getDirector());
                }
            } else {
                directorsMap.put(movie.getDirector(), 1);
            }
        });
        List<String> sortedDirectors = new ArrayList<>(directors);

        // Sort directors alphabetically
        Collections.sort(sortedDirectors);
        return sortedDirectors;
    }
}
