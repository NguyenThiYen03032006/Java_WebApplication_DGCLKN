package com.it210_dgclkn.controller;


import com.it210_dgclkn.model.entity.Student;
import com.it210_dgclkn.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final List<String> listMajor = List.of("CNTT", "Kinh tế", "Ngoại ngữ");

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "studentCode") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Sort sort = sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, 5, sort); 

        Page<Student> studentPage;

        if (keyword.isBlank()) {
            studentPage = studentService.findAll(pageable);
        } else {
            studentPage = studentService.search(keyword, pageable);
        }

        model.addAttribute("studentPage", studentPage);
        model.addAttribute("students", studentPage.getContent());

        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "list";
    }

    @GetMapping("/add")
    public String showFormAdd(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("danhSachNganh", listMajor);
        return "form";
    }

    @PostMapping("/add")
    public String create(
            @Valid @ModelAttribute Student student,
            BindingResult result,
            Model model) {

        if (student.getStudentCode() != null && !student.getStudentCode().isBlank()
                && studentService.existsByStudentCode(student.getStudentCode())) {
            result.rejectValue("studentCode", "err", "Mã sinh viên đã tồn tại");
        }

        if (student.getEmail() != null && !student.getEmail().isBlank()
                && studentService.existsByEmail(student.getEmail())) {
            result.rejectValue("email", "err", "Email đã tồn tại trong hệ thống");
        }

        // Kiểm tra ngành có thuộc danh sách cho phép không
        if (student.getMajor() != null && !listMajor.contains(student.getMajor())) {
            result.rejectValue("major", "err", "Vui lòng chọn ngành hợp lệ");
        }

        if (result.hasErrors()) {
            model.addAttribute("danhSachNganh", listMajor);
            return "form";
        }

        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showFormFix(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        model.addAttribute("danhSachNganh", listMajor);
        return "form";
    }

    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute Student student,
            BindingResult result,
            Model model) {

        if (student.getStudentCode() != null && !student.getStudentCode().isBlank()
                && studentService.existsByStudentCodeAndIdNot(student.getStudentCode(), id)) {
            result.rejectValue("studentCode", "err", "Mã sinh viên đã tồn tại");
        }

        if (student.getEmail() != null && !student.getEmail().isBlank()
                && studentService.existsByEmailAndIdNot(student.getEmail(), id)) {
            result.rejectValue("email", "err", "Email đã tồn tại trong hệ thống");
        }

        if (student.getMajor() != null && !listMajor.contains(student.getMajor())) {
            result.rejectValue("major", "err", "Vui lòng chọn ngành hợp lệ");
        }

        if (result.hasErrors()) {
            model.addAttribute("danhSachNganh", listMajor);
            return "form";
        }

        student.setId(id);
        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/students";
    }
}