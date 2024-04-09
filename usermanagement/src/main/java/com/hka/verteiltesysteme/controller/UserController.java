package com.hka.verteiltesysteme.controller;

import com.hka.verteiltesysteme.database.Role;
import com.hka.verteiltesysteme.database.User;
import com.hka.verteiltesysteme.dto.UserDto;
import com.hka.verteiltesysteme.repositories.RoleRepo;
import com.hka.verteiltesysteme.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @PostMapping("user/register")
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

    @GetMapping("/user/find/{id}")
    public ResponseEntity<User> findUser(@PathVariable int id){
        Optional<User> user = userRepo.findById(id);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id){
       userRepo.deleteById(id);

       return ResponseEntity.ok(null);
    }
}
