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
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @GetMapping("/roles/all")
    public List<Role> getAllRoles(){
        if(roleRepo.findAll().isEmpty()) {
            System.out.println("Empty");
            roleRepo.save(new Role(2,"admin", 0));
            roleRepo.save(new Role(1,"user", 1));
        } else {
            System.out.println("Not empty");
        }

        return roleRepo.findAll();
    }

    @PostMapping("user/register")
    public ResponseEntity<String> registerUser(@RequestBody @Validated UserDto user) {
        if(roleRepo.findAll().isEmpty()) {
            System.out.println("Empty");
            roleRepo.save(new Role(2,"admin", 0));
            roleRepo.save(new Role(1,"user", 1));
        } else {
            System.out.println("Not empty");
        }

        Role role = roleRepo.findByLevel(1);
        var newUser = userRepo.save(User.builder()
                .role(role)
                .firstname(user.firstname())
                .lastname(user.lastname())
                .password(user.password1())
                .username(user.username())
                .build());

        return ResponseEntity.created(URI.create("/user/" + newUser.getId())).build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUser(@PathVariable int id) {
        Optional<User> user = userRepo.findById(id);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/user/findByUsername")
    public ResponseEntity<User> findByUsername(@RequestBody String username) {
        Optional<User> user = userRepo.findByUsername(username);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/role/{level}")
    public ResponseEntity<Role> getRoleByLevel(@PathVariable int level) {
        if(roleRepo.findAll().isEmpty()) {
            System.out.println("Empty");
            roleRepo.save(new Role(2,"admin", 0));
            roleRepo.save(new Role(1,"user", 1));
        } else {
            System.out.println("Not empty");
        }

        Role role = roleRepo.findByLevel(level);
        return ResponseEntity.ok(role);
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userRepo.deleteById(id);

        return ResponseEntity.ok(null);
    }
}
