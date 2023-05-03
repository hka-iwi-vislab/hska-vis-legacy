package services.user.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import services.user.model.LogInRequest;
import services.user.model.RegisterRequest;
import services.user.model.Role;
import services.user.model.RoleRepository;
import services.user.model.User;
import services.user.model.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping(path = "")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/login")
    public ResponseEntity logInUser(@RequestBody LogInRequest logInRequest) {

        if (userRepository.existsByUsername(logInRequest.getUsername())) {
            User user = userRepository.findByUsername(logInRequest.getUsername());
            String password = user.getPassword();

            if (password.equals(logInRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            } else {
                return ResponseEntity.badRequest().body("wrong password");
            }
        } else {
            return ResponseEntity.badRequest().body("user does not exist");
        }

    }

    @PostMapping(path = "/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Long roleId = registerRequest.getRoleId();

        if (roleId != null) {

            Optional<Role> roleOptional = roleRepository.findById(roleId);

            if (roleOptional.isPresent()) {

                Role role = roleOptional.get();
                User createdUser = new User(registerRequest.getUsername(),
                        registerRequest.getFirstname(), registerRequest.getLastname(),
                        registerRequest.getPassword(), role);

                User savedUser = userRepository.save(createdUser);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(savedUser.getId()).toUri();

                return ResponseEntity.created(location).body(savedUser);
            } else {
                return ResponseEntity.badRequest().body("The specified role does not exist.");
            }
        } else {
            return ResponseEntity.badRequest().body("roleId was null.");
        }

    }
}
