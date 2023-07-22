package com.example.userservice.dto;

import com.example.userservice.model.Rating;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private String userName;
    private String email;
    private String about;
    private List<Rating> ratings = new ArrayList<>();
}
