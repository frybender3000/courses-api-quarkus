package com.courses.service;

import com.courses.dto.*;
import com.courses.entity.CourseEntity;
import com.courses.entity.LessonEntity;
import com.courses.repository.CourseRepository;
import com.courses.repository.LessonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public CourseService(CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<CourseResponse> findAll(int page, int size) {
        return courseRepository.findAll()
                .page(page, size)
                .list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CourseResponse findById(Long id) {
        CourseEntity course = courseRepository.findById(id);

        if (course == null) {
            throw new NotFoundException("Course not found");
        }

        return toResponse(course);
    }

    @Transactional
    public CourseEntity create(CourseCreateRequest request) {
        CourseEntity course = new CourseEntity();
        course.name = request.name();

        courseRepository.persist(course);
        return course;
    }

    @Transactional
    public CourseResponse update(Long id, CourseUpdateRequest request) {
        CourseEntity course = courseRepository.findById(id);

        if (course == null) {
            throw new NotFoundException("Course not found");
        }

        course.name = request.name();
        return toResponse(course);
    }

    @Transactional
    public void delete(Long id) {
        CourseEntity course = courseRepository.findById(id);

        if (course == null) {
            throw new NotFoundException("Course not found");
        }

        courseRepository.delete(course);
    }

    @Transactional
    public LessonResponse createLesson(Long courseId, LessonCreateRequest request) {
        CourseEntity course = courseRepository.findById(courseId);

        if (course == null) {
            throw new NotFoundException("Course not found");
        }

        LessonEntity lesson = new LessonEntity();
        lesson.name = request.name();
        lesson.course = course;

        lessonRepository.persist(lesson);
        course.lessons.add(lesson);

        return new LessonResponse(lesson.id, lesson.name);
    }

    public List<LessonResponse> listLessons(Long courseId) {
        CourseEntity course = courseRepository.findById(courseId);

        if (course == null) {
            throw new NotFoundException("Course not found");
        }

        return course.lessons.stream()
                .map(l -> new LessonResponse(l.id, l.name))
                .toList();
    }

    public CourseResponse toResponse(CourseEntity course) {
        List<LessonResponse> lessons = course.lessons.stream()
                .map(l -> new LessonResponse(l.id, l.name))
                .toList();

        return new CourseResponse(course.id, course.name, lessons);
    }
}








