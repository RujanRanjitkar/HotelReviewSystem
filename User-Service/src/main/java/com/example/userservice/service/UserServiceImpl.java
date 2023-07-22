package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.exceptions.ResourceNotFoundException;
import com.example.userservice.external.services.HotelService;
import com.example.userservice.model.Hotel;
import com.example.userservice.model.Rating;
import com.example.userservice.model.User;
import com.example.userservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Override
    public User addNewUser(UserRequestDto userRequestDto) {

        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();

        User user = new User();

        user.setUserId(randomUserId);
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setAbout(userRequestDto.getAbout());

        return userRepo.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> userList = userRepo.findAll();

        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        for (User user : userList) {
            UserResponseDto userResponseDto = new UserResponseDto();

            userResponseDto.setUserName(user.getUserName());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setAbout(user.getAbout());

            //fetch ratings of above user using Rating Service
            //http://localhost:8083/ratings/users/100239c1-6ca2-466d-8d5b-0d599a50fcd7

            Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);

            List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

            List<Rating> ratingList = ratings.stream().map(rating -> {
                //api call to Hotel Service to get the hotel
                //http://localhost:8082/hotels/739c8000-2f44-4b3b-8f8d-732bd4c3029e
                ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
                Hotel hotel = forEntity.getBody();
                System.out.println("response status code: {} " + forEntity.getStatusCode());

                //set the hotel to rating
                rating.setHotel(hotel);

                //return the rating
                return rating;
            }).collect(Collectors.toList());
            userResponseDto.setRatings(ratingList);

            userResponseDtoList.add(userResponseDto);
        }

        return userResponseDtoList;
    }

    @Override
    public UserResponseDto getUserById(String userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " does not exists"));

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setUserName(user.getUserName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setAbout(user.getAbout());

        //fetch ratings of above user using Rating Service
        //http://localhost:8083/ratings/users/100239c1-6ca2-466d-8d5b-0d599a50fcd7

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to Hotel Service to get the hotel
            //http://localhost:8082/hotels/739c8000-2f44-4b3b-8f8d-732bd4c3029e

//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();

            Hotel hotel = hotelService.getHotel(rating.getHotelId());

//            System.out.println("response status code: {} " + forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);

            //return the rating
            return rating;
        }).collect(Collectors.toList());
        userResponseDto.setRatings(ratingList);

        return userResponseDto;
    }
}
