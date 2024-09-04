package com.softserve.itacademy.model;

import jakarta.persistence.*;
import java.util.Objects;

public class Task {

    private long id;
    private String name;
    private TaskPriority priority;

    private ToDo todo;

    private State state;

}
