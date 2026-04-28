package com.it210_dgclkn.service;

import com.it210_dgclkn.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface StudentService {

    List<Student> findAll(Sort sort);

    List<Student> search(String keyword, Sort sort);

    Student findById(Long id);

    void save(Student student);

    void deleteById(Long id);

    boolean existsByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    boolean existsByStudentCodeAndIdNot(String studentCode, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);
    Page<Student> findAll(Pageable pageable);

    Page<Student> search(String keyword, Pageable pageable);
}