package com.courses.service;

import com.courses.dto.LessonCreateRequest;
import com.courses.dto.LessonResponse;
import com.courses.entity.CourseEntity;
import com.courses.entity.LessonEntity;
import com.courses.repository.CourseRepository;
import com.courses.repository.LessonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class LessonService {

    @Inject
    LessonRepository lessonRepository;

    @Inject
    CourseRepository courseRepository;

    // CREATE LESSON
    @Transactional
    public LessonResponse create(Long courseId, LessonCreateRequest request) {
        CourseEntity course = findCourseOrThrow(courseId);

        LessonEntity lesson = new LessonEntity();
        lesson.name = request.name();
        lesson.course = course;

        lessonRepository.persist(lesson);

        return toResponse(lesson);
    }

    // LIST LESSONS BY COURSE
    public List<LessonResponse> listByCourse(Long courseId) {
        findCourseOrThrow(courseId);

        return lessonRepository.list("course.id", courseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // FIND COURSE (REUTILIZÁVEL)
    private CourseEntity findCourseOrThrow(Long courseId) {
        CourseEntity course = courseRepository.findById(courseId);

        if (course == null) {
            throw new NotFoundException("Course not found");
        }

        return course;
    }

    // ENTITY → DTO
    private LessonResponse toResponse(LessonEntity lesson) {
        return new LessonResponse(lesson.id, lesson.name);
    }
}