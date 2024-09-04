package com.softserve.itacademy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Objects;

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private List<ToDo> myTodos;
    private List<ToDo> otherTodos;
}
