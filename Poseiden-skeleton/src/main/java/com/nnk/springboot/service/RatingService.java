package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {
    private static final Logger logger = LogManager.getLogger(IRatingService.class);
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> getRatings() {
        logger.info("Method called : getRatings()");

        List<Rating> ratingList = ratingRepository.findAll();

        if (ratingList.isEmpty()) {
            logger.warn("No ratings found");
        }

        return ratingList;
    }

    @Override
    public Rating getRatingById(Integer id) {
        return ratingRepository.findById(id).orElseThrow(() -> new RuntimeException("Rating not found"));
    }

    @Override
    public Rating addRating(Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("Invalid rating");
        }

        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(Integer id, Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("Invalid rating");
        }

        Rating toUpdate = ratingRepository.findById(id).orElseThrow(() -> new RuntimeException("Rating not found"));

        toUpdate.setFitchRating(rating.getFitchRating());
        toUpdate.setMoodysRating(rating.getMoodysRating());
        toUpdate.setSandPRating(rating.getSandPRating());
        toUpdate.setOrderNumber(rating.getOrderNumber());

        return ratingRepository.save(toUpdate);
    }

    @Override
    public void deleteRating(Integer id) {
        if (!ratingRepository.existsById(id)) {
            throw new RuntimeException("Rating not found");
        }

        ratingRepository.deleteById(id);
    }
}
