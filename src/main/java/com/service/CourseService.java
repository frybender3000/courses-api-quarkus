package com.courses.service;

import com.courses.dto.CourseCreateRequest;
import com.courses.dto.CourseResponse;
import com.courses.dto.CourseUpdateRequest;
import com.courses.entity.CourseEntity;
import com.courses.repository.CourseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<CourseResponse> listAll() {
        return repository.listAll()
                .stream()
                .map(course -> new CourseResponse(course.id, course.name))
                .collect(Collectors.toList());
    }

    public CourseEntity findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public CourseEntity create(CourseCreateRequest request) {
        CourseEntity course = new CourseEntity();
        course.name = request.name();
        repository.persist(course);
        return course;
    }

    @Transactional
    public CourseEntity update(Long id, CourseUpdateRequest request) {
        CourseEntity course = repository.findById(id);

        if (course == null) {
            return null;
        }

        course.name = request.name();
        return course;
    }

    @Transactional
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}