package com.sping_boot.student_details.service;

import java.util.List;

import com.sping_boot.student_details.entity.Student;

public interface StudentService {

    List<Student> findAll();

    Student findById(Long id);

    Student create(Student student);

    Student update(Long id, Student student);

    void delete(Long id);
}
