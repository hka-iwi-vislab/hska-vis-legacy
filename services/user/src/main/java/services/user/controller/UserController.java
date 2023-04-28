package services.user.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import services.user.model.LogInRequest;
import services.user.model.RegisterRequest;
import services.user.model.User;
import services.user.model.UserRepository;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired UserRepository userRepository;

    @GetMapping(path = "/login")
    public ResponseEntity logInUser(@RequestBody LogInRequest logInRequest){
        if(userRepository.existsByUsername(logInRequest.getUsername()).isPresent()){
            User user = userRepository.findByUsername(logInRequest.getUsername());
            String password = user.getPassword();

            if(password == logInRequest.getPassword()){
                return  ResponseEntity.status(HttpStatus.OK).body(user);
            }
            else{
                return ResponseEntity.badRequest().body("wrong password");
            }
        }
        else{
            return ResponseEntity.badRequest().body("user does not exist");
        }

    }

    @PostMapping(path = "/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest){
        if(userRepository.existsByUsername(registerRequest.getUsername()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        User newUser = new User(registerRequest.getUsername(), registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getPassword(), registerRequest.getRole());
        
        User savedUser = userRepository.save(newUser);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).body(savedUser);

    }
}
