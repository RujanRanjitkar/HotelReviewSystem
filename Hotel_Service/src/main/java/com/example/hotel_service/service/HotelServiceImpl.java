package com.example.hotel_service.service;

import com.example.hotel_service.dto.HotelRequestDto;
import com.example.hotel_service.dto.HotelResponseDto;
import com.example.hotel_service.exceptions.ResourceNotFoundException;
import com.example.hotel_service.model.Hotel;
import com.example.hotel_service.repo.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepo hotelRepo;

    @Override
    public Hotel addNewHotel(HotelRequestDto hotelRequestDto) {

        //generate unique hotelId
        String randomUserId = UUID.randomUUID().toString();

        Hotel hotel = new Hotel();

        hotel.setHotelId(randomUserId);
        hotel.setHotelName(hotelRequestDto.getHotelName());
        hotel.setLocation(hotelRequestDto.getLocation());
        hotel.setAbout(hotelRequestDto.getAbout());

        return hotelRepo.save(hotel);
    }

    @Override
    public List<HotelResponseDto> getAllHotels() {
        List<Hotel> userList = hotelRepo.findAll();

        List<HotelResponseDto> hotelResponseDtoList = new ArrayList<>();

        for (Hotel hotel : userList) {
            HotelResponseDto hotelResponseDto = new HotelResponseDto();

            hotelResponseDto.setHotelId(hotel.getHotelId());
            hotelResponseDto.setHotelName(hotel.getHotelName());
            hotelResponseDto.setLocation(hotel.getLocation());
            hotelResponseDto.setAbout(hotel.getAbout());

            hotelResponseDtoList.add(hotelResponseDto);
        }

        return hotelResponseDtoList;
    }

    @Override
    public HotelResponseDto getHotelById(String hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel with id " + hotelId + " does not exists"));

        HotelResponseDto hotelResponseDto = new HotelResponseDto();

        hotelResponseDto.setHotelId(hotel.getHotelId());
        hotelResponseDto.setHotelName(hotel.getHotelName());
        hotelResponseDto.setLocation(hotel.getLocation());
        hotelResponseDto.setAbout(hotel.getAbout());

        return hotelResponseDto;
    }
}
