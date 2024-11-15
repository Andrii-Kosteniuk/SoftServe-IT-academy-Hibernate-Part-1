package com.softserve.itacademy.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoTests {

    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidToDo() {
        ToDo todo = new ToDo();
        todo.setTitle("Project Meeting");

        Set<ConstraintViolation<ToDo>> violations = validator.validate(todo);
        assertTrue(violations.isEmpty(), "Expected no validation errors for a valid title");
    }

    @Test
    public void testEmptyTitle() {
        ToDo todo = new ToDo();
        todo.setTitle("");

        Set<ConstraintViolation<ToDo>> violations = validator.validate(todo);
        assertFalse(violations.isEmpty(), "Expected validation error for empty title");

        ConstraintViolation<ToDo> violation = violations.iterator().next();
        assertEquals("Title cannot be empty", violation.getMessage());
    }
}
