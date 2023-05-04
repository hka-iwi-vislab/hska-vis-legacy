package services.user.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<User> logInUser(@RequestBody LogInRequest logInRequest) {
        Optional<User> user = userRepository.findByUsername(logInRequest.getUsername());

        if (user.isPresent()) {
            String password = user.get().getPassword();

            if (password.equals(logInRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body(user.get());
            } else {
                return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(path = "/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Long roleId = registerRequest.getRoleId();

        if (roleId != null) {

            Optional<Role> roleOptional = roleRepository.findById(roleId);

            if (roleOptional.isPresent()) {

                Role role = roleOptional.get();
                User createdUser = new User(registerRequest.getUsername(),
                        registerRequest.getFirstName(), registerRequest.getLastName(),
                        registerRequest.getPassword(), role);

                User savedUser = userRepository.save(createdUser);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(savedUser.getId()).toUri();

                return ResponseEntity.created(location).body(savedUser);
            } else {
                return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/{:username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            return new ResponseEntity<User>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        if (id != null) {
            if (userRepository.findById(id).isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(String.format("Product with id %d deleted successfully.", id));
            } else {
                return ResponseEntity.badRequest().body(String.format("Product with id %d does not exist.", id));
            }
        } else {
            return ResponseEntity.badRequest().body("id was null.");
        }
    }
}
