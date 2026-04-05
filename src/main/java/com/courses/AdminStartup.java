package com.courses;

import com.courses.entity.UserEntity;
import com.courses.repository.UserRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class AdminStartup{

    @Inject
    UserRepository userRepository;


    @Transactional
    void onStart(@Observes StartupEvent event) {
        boolean exists  = userRepository.findByEmail("admin@admin.com").isPresent();

        if(!exists) {
            UserEntity admin = new UserEntity();
            admin.name = "name";
            admin.email = "admin@admin";
            admin.password = "admin";
            admin.role = "ADMIN";

        userRepository.persist(admin);

        }
    }

    @PostConstruct
    public void init() {
        System.out.println("AdminStartup initialized");

    }
    @PreDestroy
    public void destroy() {
        System.out.println("AdminStartup destrpyed");
    }
}
