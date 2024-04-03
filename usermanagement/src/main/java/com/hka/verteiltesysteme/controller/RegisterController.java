package com.hka.verteiltesysteme.controller;

import com.hka.verteiltesysteme.database.Role;
import com.hka.verteiltesysteme.database.User;
import com.hka.verteiltesysteme.dto.UserDto;
import com.hka.verteiltesysteme.repositories.RoleRepo;
import com.hka.verteiltesysteme.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Validated UserDto user) {

        Role role = roleRepo.findByLevel(1);

        userRepo.save(User.builder()
                .role(role)
                .firstname(user.firstname())
                .lastname(user.lastname())
                .password(user.password1())
                .username(user.username())
                .build());

        return ResponseEntity.created(URI.create("/hallo")).build();
    }
}
