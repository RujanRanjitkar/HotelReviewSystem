package com.example.rating_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequestDto {
    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String feedback;
}
