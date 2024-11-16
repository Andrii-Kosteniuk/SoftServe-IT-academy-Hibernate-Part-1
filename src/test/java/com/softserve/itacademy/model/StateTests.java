package com.softserve.itacademy.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StateTests {
    private static State validState;

    @BeforeAll
    static void init() {
        validState = new State();
        validState.setName("NOT DONE");
    }

    @Test
    void validStateTest() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<State>> violations = validator.validate(validState);
            assertEquals(0, violations.size());
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidStates")
    void testInvalidState(String input, String errorValue) {
        State invalidState = new State();
        invalidState.setName(input);
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<State>> violations = validator.validate(invalidState);
            assertEquals(1, violations.size());
            assertEquals(errorValue, violations.iterator().next().getInvalidValue());
        }
    }

    static Stream<Arguments> provideInvalidStates() {
        return Stream.of(
                Arguments.of("Неправильна назва", "Неправильна назва"),
                Arguments.of("TooLongStateNameTooLongStateName", "TooLongStateNameTooLongStateName"),
                Arguments.of("    ", "    "),
                Arguments.of("almost<alid?", "almost<alid?")
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidStates")
    void testValidStates(String input) {
        State invalidState = new State();
        invalidState.setName(input);
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<State>> violations = validator.validate(invalidState);
            assertEquals(0, violations.size());
        }
    }

    static Stream<Arguments> provideValidStates() {
        return Stream.of(
                Arguments.of("Valid Name"),
                Arguments.of("valid12345"),
                Arguments.of("1932123"),
                Arguments.of("valid_name-for-state")
        );
    }
}
