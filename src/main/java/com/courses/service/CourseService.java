package com.courses.service;

import com.courses.dto.CourseCreateRequest;
import com.courses.dto.CourseResponse;
import com.courses.dto.CourseUpdateRequest;
import com.courses.dto.LessonCreateRequest;
import com.courses.dto.LessonResponse;
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
        return courseRepository.findAllCourses(page, size)
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

    public CourseEntity findByIdOrThrow(Long id) {
        CourseEntity course = courseRepository.findById(id);

        if (course == null) {
            throw new NotFoundException("Course not found");
        }

        return course;
    }

    @Transactional
    public CourseResponse create(CourseCreateRequest request) {
        CourseEntity course = new CourseEntity();
        course.name = request.name();

        courseRepository.persist(course);

        return toResponse(course);
    }

    @Transactional
    public CourseResponse update(Long id, CourseUpdateRequest request) {
        CourseEntity course = findByIdOrThrow(id);

        course.name = request.name();

        return toResponse(course);
    }

    @Transactional
    public void delete(Long id) {
        CourseEntity course = findByIdOrThrow(id);
        courseRepository.delete(course);
    }

    @Transactional
    public LessonResponse createLesson(Long courseId, LessonCreateRequest request) {
        CourseEntity course = findByIdOrThrow(courseId);

        LessonEntity lesson = new LessonEntity();
        lesson.name = request.name();
        lesson.course = course;

        lessonRepository.persist(lesson);

        return new LessonResponse(lesson.id, lesson.name);
    }

    public List<LessonResponse> listLessons(Long courseId) {
        CourseEntity course = findByIdOrThrow(courseId);

        if (course.lessons == null) {
            return List.of();
        }

        return course.lessons.stream()
                .map(lesson -> new LessonResponse(lesson.id, lesson.name))
                .toList();
    }

    private CourseResponse toResponse(CourseEntity course) {
        List<LessonResponse> lessons = course.lessons == null
                ? List.of()
                : course.lessons.stream()
                .map(lesson -> new LessonResponse(lesson.id, lesson.name))
                .toList();

        return new CourseResponse(course.id, course.name, lessons);
    }
}