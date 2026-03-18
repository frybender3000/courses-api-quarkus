package com.courses.repository;

import com.courses.entity.LessonEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LessonRepository implements  PanacheRepository<LessonEntity> {

}