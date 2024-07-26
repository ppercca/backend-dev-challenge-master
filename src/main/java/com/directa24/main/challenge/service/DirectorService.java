package com.directa24.main.challenge.service;

import java.util.List;

public interface DirectorService {

    /**
     * Get a list of directors who exceed the movie threshold
     * @param threshold minimum threshold of movies per director
     * @return list of directors
     */
    List<String> getDirectorsByMovieThreshold(int threshold);

}
