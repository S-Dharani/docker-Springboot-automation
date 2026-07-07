package com.sping_boot.student_details.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sping_boot.student_details.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    Optional<Student> findByEmail(String email);
}
