package com.directa24.main.challenge.web.rest;

import com.directa24.main.challenge.exception.CustomException;
import com.directa24.main.challenge.exception.ErrorCode;
import com.directa24.main.challenge.service.DirectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DirectorController {

    private final Logger log = LoggerFactory.getLogger(DirectorController.class);

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    /**
     * GET  /directors : get all the directors by movie threshold.
     *
     * @param threshold minimum threshold of movies per director.
     * @return the ResponseEntity with status 200 (OK) and the list of directors in body
     */
    @GetMapping("/directors")
    public ResponseEntity<List<String>> getDirectorsByMovieThreshold(@RequestParam int threshold) {
        log.debug("REST request to get Directors by movie threshold: {}", threshold);
        if (threshold < 1) {
            throw new CustomException(ErrorCode.THRESHOLD_ERROR);
        }
        List<String> directors = directorService.getDirectorsByMovieThreshold(threshold);
        return ResponseEntity.ok().body(directors);
    }
}
