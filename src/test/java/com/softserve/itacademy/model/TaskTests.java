package com.softserve.itacademy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.repository.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootTest
public class TaskTests {

    private static final int MAX_TASK_NAME_LENGTH = 200;
    private static Validator validator;

    @Mock
    private static Task taskMock;

    @Mock
    private State stateMock;

    @Mock
    private ToDo todoMock;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    static void init() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory();) {
            validator = factory.getValidator();
        }

    }

    @Test
    void createValidTask() {
        Set<ConstraintViolation<Task>> violations = validator.validate(taskMock);
        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTaskNames")
    void constraintViolationInvalidTaskName(String taskName, String expectedMessage) {
        Task task = new Task();
        task.setName(taskName);
        task.setPriority(TaskPriority.LOW);
        task.setState(stateMock);
        task.setTodo(todoMock);

        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidTaskNames() {
        return Stream.of(Arguments.of("", "length must be between 3 and 200"),
                Arguments.of("n", "length must be between 3 and 200"),
                Arguments.of("n".repeat(MAX_TASK_NAME_LENGTH + 1), "length must be between 3 and 200"));
    }

    @ParameterizedTest
    @MethodSource("provideValidTaskNames")
    void initTaskWithValidNames(String taskName) {
        Task task = new Task();
        task.setName(taskName);
        task.setPriority(TaskPriority.LOW);
        task.setState(stateMock);
        task.setTodo(todoMock);

        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertEquals(0, violations.size());
    }

    private static Stream<Arguments> provideValidTaskNames() {
        return Stream.of(Arguments.of("length is greater than 3 and lesser than 200 characters"),
                Arguments.of("can contain special characters !@#$%^&*()-_=+[]{}|;:'\",.<>?/`~\\"),
                Arguments.of("!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~\\"), Arguments.of("ALLL UPPERCASE LETTERS"),
                Arguments.of("1234567890"), Arguments.of("1".repeat(MAX_TASK_NAME_LENGTH)));
    }

    @ParameterizedTest
    @MethodSource("provideNumberOfTimesSavingTaskToDB")
    void testSaveTaskToDB(int timesToSave) {
        final String VALID_NAME = "Valid name";
        List<Task> locallyCreatedTasks = new ArrayList<>();
        List<Task> savedTasks = new ArrayList<>();

        for (int i = 0; i < timesToSave; i++) {
            Task task = new Task();
            ToDo todo = new ToDo();
            State state = new State();
            User user = new User();

            user.setFirstName("First");
            user.setLastName("Last");
            user.setEmail("valid@gmail.com");
            user.setPassword("asdfghjklASDFGHJKL1!");
            user.setMyTodos(new ArrayList<>(List.of(todo)));
            user.setRole(UserRole.USER);

            state.setName(VALID_NAME + i);
            state.setTasks(new ArrayList<>(List.of(task)));

            todo.setTitle(VALID_NAME + i);
            todo.setTasks(new ArrayList<>(List.of(task)));
            todo.setOwner(user);

            task.setName(VALID_NAME + i);
            task.setPriority(TaskPriority.LOW);
            task.setState(state);
            task.setTodo(todo);

            userRepository.save(user);
            toDoRepository.save(todo);
            stateRepository.save(state);

            locallyCreatedTasks.add(task);
            savedTasks.add(taskRepository.save(task));
        }

        assertTrue(locallyCreatedTasks.equals(savedTasks));
    }

    private static Stream<Arguments> provideNumberOfTimesSavingTaskToDB() {
        return Stream.of(Arguments.of(1), Arguments.of(10), Arguments.of(100), Arguments.of(1000));
    }

}
