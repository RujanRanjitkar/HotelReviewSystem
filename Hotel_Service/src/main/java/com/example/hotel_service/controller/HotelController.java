package com.example.hotel_service.controller;

import com.example.hotel_service.dto.HotelRequestDto;
import com.example.hotel_service.dto.HotelResponseDto;
import com.example.hotel_service.model.Hotel;
import com.example.hotel_service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Hotel> addNewHotel(@RequestBody HotelRequestDto hotelRequestDto) {
        Hotel hotel = hotelService.addNewHotel(hotelRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDto> getHotelById(@PathVariable String hotelId) {
        HotelResponseDto hotelResponseDto = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelResponseDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping
    public ResponseEntity<List<HotelResponseDto>> getAllHotels() {
        List<HotelResponseDto> allHotels = hotelService.getAllHotels();
        return ResponseEntity.ok(allHotels);
    }
}
