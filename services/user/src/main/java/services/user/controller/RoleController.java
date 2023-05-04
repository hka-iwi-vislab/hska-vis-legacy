package services.user.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import services.user.model.Role;
import services.user.model.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping(path = "")
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    @PostMapping(path = "")
    public ResponseEntity<Role> createProduct(@RequestBody Role role) {

        if (roleRepository.existsByLevel(role.getLevel()) || roleRepository.existsByType(role.getType())) {
            return new ResponseEntity<Role>(HttpStatus.CONFLICT);
        } else {
            Role savedRole = roleRepository.save(role);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(savedRole.getId()).toUri();
            
            return ResponseEntity.created(location).body(savedRole);
        }
    }

    @GetMapping(value="/{:level}")
    public ResponseEntity<Role> getRoleByLevel(@PathVariable int level) {
        Optional<Role> role = roleRepository.findByLevel(level);

        if (role.isPresent()) {
            return ResponseEntity.ok().body(role.get());
        } else {
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }
    }
}
