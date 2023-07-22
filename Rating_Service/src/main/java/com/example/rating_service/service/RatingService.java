package com.example.rating_service.service;

import com.example.rating_service.dto.RatingRequestDto;
import com.example.rating_service.dto.RatingResponseDto;
import com.example.rating_service.model.Rating;

import java.util.List;

public interface RatingService {

    Rating addNewRating(RatingRequestDto ratingRequestDto);

    List<RatingResponseDto> getAllRatings();

    List<RatingResponseDto> getRatingsByUserId(String userId);
    List<RatingResponseDto> getRatingsByHotelId(String userId);
}
