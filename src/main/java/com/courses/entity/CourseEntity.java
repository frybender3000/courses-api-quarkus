package com.courses.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

 @Entity
 @Table(name ="courses")
 public class CourseEntity extends PanacheEntityBase{


       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       public Long id;

       @Column(nullable = false)
       public String name;

       @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true )
       public List<LessonEntity> lessons = new ArrayList<>();






 }