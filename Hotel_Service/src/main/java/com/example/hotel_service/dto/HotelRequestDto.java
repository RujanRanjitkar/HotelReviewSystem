package com.example.hotel_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelRequestDto {
    private String hotelId;
    private String hotelName;
    private String location;
    private String about;
}
