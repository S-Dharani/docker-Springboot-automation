package com.sping_boot.student_details.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sping_boot.student_details.entity.Student;
import com.sping_boot.student_details.exception.DuplicateEmailException;
import com.sping_boot.student_details.exception.StudentNotFoundException;
import com.sping_boot.student_details.repository.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student create(Student student) {
        ensureEmailAvailable(student.getEmail(), null);
        return studentRepository.save(student);
    }

    @Override
    public Student update(Long id, Student student) {
        Student existing = findById(id);
        ensureEmailAvailable(student.getEmail(), id);

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setCourse(student.getCourse());
        existing.setAge(student.getAge());
        existing.setPhone(student.getPhone());

        return studentRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Student existing = findById(id);
        studentRepository.delete(existing);
    }

    private void ensureEmailAvailable(String email, Long currentStudentId) {
        studentRepository.findByEmail(email).ifPresent(student -> {
            if (currentStudentId == null || !student.getId().equals(currentStudentId)) {
                throw new DuplicateEmailException(email);
            }
        });
    }
}
