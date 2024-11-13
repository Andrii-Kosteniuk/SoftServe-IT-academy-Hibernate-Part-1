package com.softserve.itacademy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "User name can not be empty!")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotEmpty(message = "User last name can not be empty!")
    @Size(min = 2, max = 50)
    private String lastName;

    @Email(message = "Invalid email format")
    @Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$",
            message = "Invalid email address format")
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>?/`~\\\\]).{8,}$",
            message = "Password must be at least 8 characters long, " +
                      "contain at least one uppercase letter, one lowercase letter, " +
                      "one number, and one special character !@#$%^&*()-_=+[]{}|;:'\",.<>?/`~\\")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToDo> myTodos;

    @ManyToMany(mappedBy = "collaborators")
    private List<ToDo> otherTodos;
}
