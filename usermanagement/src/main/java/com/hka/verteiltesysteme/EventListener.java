package com.hka.verteiltesysteme;

import com.hka.verteiltesysteme.database.Role;
import com.hka.verteiltesysteme.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventListener implements ApplicationListener<ContextStartedEvent> {

    private final RoleRepo roleRepo;

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        if(roleRepo.findAll().isEmpty()) {
            System.out.println("Empty");
            roleRepo.save(new Role(0,"admin", 0));
            roleRepo.save(new Role(1,"user", 1));
        } else {
            System.out.println("Not empty");
        }
    }
}
