package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {
    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    public void getValidListOfRatings() {
        List<Rating> ratings = new ArrayList<>();
        ratings.add(new Rating());
        ratings.add(new Rating());

        when(ratingRepository.findAll()).thenReturn(ratings);

        List<Rating> toCheck = ratingService.getRatings();

        assertEquals(2, toCheck.size());
    }

    @Test
    public void getNullListOfRatings() {
        when(ratingRepository.findAll()).thenReturn(new ArrayList<>());

        List<Rating> toCheck = ratingService.getRatings();

        assertTrue(toCheck.isEmpty());
    }

    @Test
    public void getValidRating() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(new Rating()));

        Rating toCheck = ratingService.getRatingById(1);

        assertNotNull(toCheck);
        assertDoesNotThrow(() -> {
        });
    }

    @Test
    public void cantGetRatingNotFound() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ratingService.getRatingById(1));

        assertTrue(runtimeException.getMessage().contains("Rating not found"));
    }

    @Test
    public void addValidRating() {
        Rating rating = new Rating("moody", "SandP", "Fitch", 3);
        rating.setId(1);

        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating toCheck = ratingService.addRating(rating);

        assertDoesNotThrow(() -> {});
        assertEquals("moody", toCheck.getMoodysRating());
        assertEquals("SandP", toCheck.getSandPRating());
        assertEquals("Fitch", toCheck.getFitchRating());
        assertEquals(3, toCheck.getOrderNumber());
    }

    @Test
    public void cantAddRatingNull() {

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ratingService.addRating(null));

        assertTrue(runtimeException.getMessage().contains("Invalid rating"));
    }

    @Test
    public void updateValidRating() {
        Rating toUpdate = new Rating("Moody", "SandP", "Fitch", 3);
        toUpdate.setId(1);

        Rating rating = new Rating("Updated", "Updated", "Updated", 1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(toUpdate));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating toCheck = ratingService.updateRating(1, rating);

        assertEquals("Updated", toCheck.getMoodysRating());
        assertEquals("Updated", toCheck.getSandPRating());
        assertEquals("Updated", toCheck.getFitchRating());
        assertEquals(1, toCheck.getOrderNumber());
    }

    @Test
    public void cantUpdateRatingNull() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ratingService.updateRating(1, null));

        assertTrue(runtimeException.getMessage().contains("Invalid rating"));
    }

    @Test
    public void cantUpdateRatingNotFound() {
        Rating rating = new Rating("Updated", "Updated", "Updated", 1);

        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ratingService.updateRating(1, rating));

        assertTrue(runtimeException.getMessage().contains("Rating not found"));
    }

    @Test
    public void deleteValidRatingById() {
        when(ratingRepository.existsById(anyInt())).thenReturn(true);

        assertDoesNotThrow(() -> ratingService.deleteRating(1));
    }

    @Test
    public void cantDeleteRatingByIdNotFound() {
        when(ratingRepository.existsById(anyInt())).thenReturn(false);

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ratingService.deleteRating(1));

        assertTrue(runtimeException.getMessage().contains("Rating not found"));
    }
}