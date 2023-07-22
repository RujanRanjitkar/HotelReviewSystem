package com.example.rating_service.controller;

import com.example.rating_service.dto.RatingRequestDto;
import com.example.rating_service.dto.RatingResponseDto;
import com.example.rating_service.model.Rating;
import com.example.rating_service.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Rating> addNewRating(@RequestBody RatingRequestDto ratingRequestDto) {
        Rating rating = ratingService.addNewRating(ratingRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<RatingResponseDto>> getRatingsByUserId(@PathVariable String userId) {
        List<RatingResponseDto> allRatingList = ratingService.getRatingsByUserId(userId);
        return ResponseEntity.ok(allRatingList);
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<RatingResponseDto>> getRatingsByHotelId(@PathVariable String hotelId) {
        List<RatingResponseDto> allRatingList = ratingService.getRatingsByHotelId(hotelId);
        return ResponseEntity.ok(allRatingList);
    }

    @GetMapping
    public ResponseEntity<List<RatingResponseDto>> getAllRatings() {
        List<RatingResponseDto> allRatingList = ratingService.getAllRatings();
        return ResponseEntity.ok(allRatingList);
    }

}
