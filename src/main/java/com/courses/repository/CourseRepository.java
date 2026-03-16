package com.courses.repository;

import com.courses.entity.CourseEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public  class CourseRepository  implements PanacheRepository<CourseEntity>{
}