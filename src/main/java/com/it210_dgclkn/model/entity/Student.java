package com.it210_dgclkn.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "SV\\d{4}", message = "Mã SV phải dạng SV1234")
    private String studentCode;

    @Column(nullable = false)
    @Size(min = 5, max = 100)
    private String fullName;

    @Column(unique = true, nullable = false)
    @Email(message = "Email không hợp lệ")
    private String email;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double gpa;
}