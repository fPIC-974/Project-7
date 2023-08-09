package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface IRatingService {
    List<Rating> getRatings();

    Rating getRatingById(Integer id);

    Rating addRating(Rating rating);

    Rating updateRating(Integer id, Rating rating);

    void deleteRating(Integer id);
}
