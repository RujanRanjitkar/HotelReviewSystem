package com.example.userservice.controller;

import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addNewUser(@RequestBody UserRequestDto userRequestDto) {
        User user = userService.addNewUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    int retryCount = 1;

    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String userId) {
        System.out.println("Retry count: {} " + retryCount);
        retryCount++;
        UserResponseDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    //creating fall back method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        System.out.println("Fallback is executed because service is down : " + ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .userName("dummy")
                .about("This user is created dummy because some service is down")
                .userId("11111")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> allUser = userService.getAllUsers();
        return ResponseEntity.ok(allUser);
    }

}
