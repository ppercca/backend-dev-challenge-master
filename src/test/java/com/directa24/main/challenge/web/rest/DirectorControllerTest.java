package com.directa24.main.challenge.web.rest;

import com.directa24.main.challenge.exception.CustomException;
import com.directa24.main.challenge.exception.ErrorCode;
import com.directa24.main.challenge.service.DirectorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DirectorControllerTest {

    @InjectMocks
    private DirectorController directorController;

    @Mock
    private DirectorService directorService;

    @Test
    public void getDirectorsByMovieThreshold() {
        int THRESHOLD = 4;
        List<String> directors = List.of("Martin Scorsese", "Woody Allen");
        when(directorService.getDirectorsByMovieThreshold(THRESHOLD)).thenReturn(directors);
        ResponseEntity<List<String>> directorsResponseEntity =  directorController.getDirectorsByMovieThreshold(THRESHOLD);
        assertEquals(directorsResponseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(directorsResponseEntity.getBody(), directors);
    }

    @Test
    public void getDirectorsByMovieThresholdError() {
        int THRESHOLD = -1;
        List<String> directors = List.of("Martin Scorsese", "Woody Allen");
        lenient().when(directorService.getDirectorsByMovieThreshold(THRESHOLD)).thenReturn(directors);
        CustomException exception = assertThrows(CustomException.class, () -> {
            directorController.getDirectorsByMovieThreshold(THRESHOLD);
        });
        assertEquals(ErrorCode.THRESHOLD_ERROR, exception.getErrorCode());

    }
}
