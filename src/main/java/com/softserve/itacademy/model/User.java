package com.softserve.itacademy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Pattern(regexp = "^[A-Z][a-z]+(-[A-Z][a-z]+)?$",
    message = "First name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-z]+(-[A-Z][a-z]+)?$",
            message = "Last name must start with a capital letter followed by lowercase letters, optionally separated by a hyphen")
    private String lastName;

    @Pattern(regexp = "^(?!\\.)([\\w\\-_.]+[^.])@([\\w-]+)\\.(\\w{2,})(\\.\\w{2,})?$",
            message = "Must be a valid e-mail address")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>?/`~\\\\]).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one letter, one number, and can include special characters")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToDo> myTodos;

    @ManyToMany(mappedBy = "collaborators")
    private List<ToDo> otherTodos;
}
