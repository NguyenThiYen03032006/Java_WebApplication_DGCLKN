package com.it210_dgclkn.repository;

import com.it210_dgclkn.model.entity.Student;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFullNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCase(
            String fullName, String studentCode, Sort sort);

    boolean existsByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    boolean existsByStudentCodeAndIdNot(String studentCode, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);
    Page<Student> findByFullNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCase(
            String fullName, String studentCode, Pageable pageable);
}