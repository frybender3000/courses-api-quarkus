package com.courses.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class LessonEntity extends PanacheEntityBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public  String name;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    public CourseEntity course;
}