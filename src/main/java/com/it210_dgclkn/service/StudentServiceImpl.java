package com.it210_dgclkn.service;

import com.it210_dgclkn.model.entity.Student;
import com.it210_dgclkn.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll(Sort sort) {
        return studentRepository.findAll(sort);
    }

    @Override
    public List<Student> search(String keyword, Sort sort) {
        return studentRepository
                .findByFullNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCase(
                        keyword, keyword, sort);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên ID: " + id));
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public boolean existsByStudentCode(String studentCode) {
        return studentRepository.existsByStudentCode(studentCode);
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByStudentCodeAndIdNot(String studentCode, Long id) {
        return studentRepository.existsByStudentCodeAndIdNot(studentCode, id);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return studentRepository.existsByEmailAndIdNot(email, id);
    }
    @Override
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> search(String keyword, Pageable pageable) {
        return studentRepository
                .findByFullNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCase(
                        keyword, keyword, pageable);
    }

}