package com.example.hotel_service.service;

import com.example.hotel_service.dto.HotelRequestDto;
import com.example.hotel_service.dto.HotelResponseDto;
import com.example.hotel_service.model.Hotel;

import java.util.List;

public interface HotelService {

    Hotel addNewHotel(HotelRequestDto hotelRequestDto);

    List<HotelResponseDto> getAllHotels();

    HotelResponseDto getHotelById(String hotelId);
}
