package com.courses.repository;

import com.courses.entity.CourseEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CourseRepository implements PanacheRepositoryBase<CourseEntity, Long> {

    // Custom methods for the repository can go here, like:
    public CourseEntity findById(Long id) {
        return find("id", id).firstResult();
    }

    public List<CourseEntity> findAllCourses(int page, int size) {
        return findAll()
                .page(Page.of(page, size))
                .list();
    }
}