package com.example.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "micro_user")
public class User {
    @Id
    private String userId;

    @Column(length = 15)
    private String userName;
    private String email;
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();
}
