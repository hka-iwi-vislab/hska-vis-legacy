package com.hka.verteiltesysteme;

import com.hka.verteiltesysteme.database.Role;
import com.hka.verteiltesysteme.database.User;
import com.hka.verteiltesysteme.repositories.RoleRepo;
import com.hka.verteiltesysteme.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventListener implements ApplicationListener<ContextStartedEvent> {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        if(!roleRepo.existsById(2)) roleRepo.save(new Role(2,"admin", 0));
        if(!roleRepo.existsById(1)) roleRepo.save(new Role(1,"user", 1));
        if(userRepo.findByUsername("admin").isEmpty()) {

            userRepo.save(new User(9999, "admin", "admin", "admin", "admin", roleRepo.findByLevel1(0)));
        }
    }
}
