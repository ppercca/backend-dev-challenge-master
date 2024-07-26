package com.directa24.main.challenge.service;

import com.directa24.main.challenge.model.Movie;

import java.util.List;

public interface MovieService {

    /**
     * Get all the movies from all the pages without duplication
     * @return list of movies
     */
    List<Movie> getMovies();

}
